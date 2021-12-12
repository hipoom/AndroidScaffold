package com.hipoom.scaffold.security.java.zip

import com.hipoom.scaffold.core.java.ktx.insertAsNewFile
import com.hipoom.scaffold.core.java.ktx.littleEndianLong
import com.hipoom.scaffold.core.java.ktx.overwrite
import com.hipoom.scaffold.core.java.ktx.toLittleEndianBytes
import com.hipoom.scaffold.security.java.KeyStoreReader
import com.hipoom.scaffold.security.java.SHA256Utils
import com.hipoom.scaffold.zip.readEoCDR
import com.hipoom.scaffold.core.java.ktx.*
import com.hipoom.scaffold.zip.EoCDR
import com.hipoom.scaffold.zip.OffsetAndSize
import java.io.File
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

/**
 * 对一个 zip 文件，按照 V2 签名的方式，对其签名。
 * @author ZhengHaiPeng
 * @since 2021/12/11 1:57 上午
 */
class ZipSignatureUtils {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    /**
     * 签名块末尾的魔数
     */
    private val magic = "Hipoom Zip Sign!"

    /**
     * 签名块中，明文摘要的 ID
     */
    private val digestID = 0x4287FF

    /**
     * 签名块中，用私钥对摘要加密后的密文的 ID
     */
    private val encryptID = 0xCE7B66



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    /**
     * 对文件进行签名。
     *
     * @param zip        需要签名的压缩文件
     * @param privateKey 私钥
     */
    fun signature(zip: File, privateKey: PrivateKey): File? {
        // 计算原始文件的 SHA256 摘要
        val utils = SHA256Utils()
        val digest = utils.digest(zip)

        // 对摘要进行加密
        val encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey)
        val encryptDigest = encryptCipher.doFinal(digest)

        // 构建签名块
        val signBlock = SignatureBlock(
            magic = magic,
            pairs = listOf(
                IDAndValue(
                    id = digestID,
                    value = digest
                ),
                IDAndValue(
                    id = encryptID,
                    value = encryptDigest
                )
            )
        )
        val signBlockBytes = signBlock.build()

        // 插入到 zip 文件中
        return insertSignBlockAndUpdateEoCDR(zip, signBlockBytes)
    }

    /**
     * 读取签名块信息。
     * 兼容与 V2 签名格式相同的所有签名方式（ magic 长度必须为 16 字节。）
     */
    fun readSignBlocks(file: File, eoCDR: EoCDR? = null): SignatureBlock? {
        val eocdr = eoCDR ?: file.readEoCDR() ?: return null
        // 签名块起始位置的偏移量，包含 Head Size Block
        val (sbOffset, sbTotalSize) = readSignBlockOffsetAndSize(file, eocdr) ?: return null
        // 签名块的数据
        val signBlockBytes = file.readBytes(sbOffset, sbTotalSize.toInt())
        // 解析并返回
        return SignatureBlock.parse(signBlockBytes)
    }

    /**
     * 校验文件的签名。
     */
    fun verify(zip: File, publicKey: PublicKey): Boolean {
        val eocdr = zip.readEoCDR() ?: return false

        // 签名块起始位置的偏移量，包含 Head Size Block
        val (sbOffset, sbTotalSize) = readSignBlockOffsetAndSize(zip, eocdr) ?: return false

        // 签名块的数据
        val signBlockBytes = zip.readBytes(sbOffset, sbTotalSize.toInt())

        // 得到签名块的信息
        val signBlock = SignatureBlock.parse(signBlockBytes) ?: return false

        // 得到签名块的内容
        val digest = signBlock.idValues.find { it.id == digestID }?.value ?: return false
        val encrypt = signBlock.idValues.find { it.id == encryptID }?.value ?: return false

        // 解密 encrypt
        val decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        decryptCipher.init(Cipher.DECRYPT_MODE, publicKey)
        val decrypt = decryptCipher.doFinal(encrypt)

        // 校验证书是否正确
        val isCertificationValid = digest contentEquals decrypt
        if (!isCertificationValid) {
            return false
        }

        // 去掉签名块
        val tempFile = File(zip.absolutePath + ".deleteSignBlock")
        zip.deleteSegment(
            start = sbOffset.toInt(),
            length = sbTotalSize.toInt(),
            dest = tempFile
        )

        // 重写 cdr 的偏移量
        val cdrOffset = eocdr.cdrOffset - sbTotalSize
        val eocdrOffset = eocdr.offset - sbTotalSize
        tempFile.overwrite(eocdrOffset + 16, cdrOffset.toInt().toLittleEndianBytes())

        // 计算摘要值
        val sha256 = SHA256Utils().digest(tempFile)
        return sha256 contentEquals digest
    }



    /* ======================================================= */
    /* Private Methods                                         */
    /* ======================================================= */

    /**
     * 读取签名块起始位置的偏移量和大小，包含 Head Size Block。
     */
    private fun readSignBlockOffsetAndSize(file: File, eoCDR: EoCDR? = null): OffsetAndSize? {
        val eocdr = eoCDR ?: file.readEoCDR() ?: return null
        // 签名块大小的偏移量
        val magicSize = 16
        // 记录「签名块大小(不含 Head Size)」的数据块的大小
        val sizeSize = 8
        // 记录「签名块大小(不含 Head Size)」的数据块的偏移量
        val sbSizeOffset = (eocdr.cdrOffset - magicSize - sizeSize).toLong()
        // 读取「签名块大小(不含 Head Size)」
        val sbSize = file.readBytes(sbSizeOffset, sizeSize).littleEndianLong()
        // 签名块大小，包含 Head Size
        val sbTotalSize = (sbSize + sizeSize)
        // 签名块起始位置的偏移量，包含 Head Size Block
        return OffsetAndSize(
            offset = eocdr.cdrOffset - sbTotalSize,
            size = sbTotalSize
        )
    }

    /**
     * 往一个 zip 文件中插入一个签名块，并且更新它的 EoCDR 中记录的偏移信息。
     */
    private fun insertSignBlockAndUpdateEoCDR(zip: File, signBlockBytes: ByteArray): File? {
        File(zip.absolutePath + "signature.hex").writeBytes(signBlockBytes)

        println("签名块大小: ${signBlockBytes.size}")
        val eocdr = zip.readEoCDR() ?: return null
        println("原始 zip 文件的 eocdr 偏移量：${eocdr.offset}")
        println("原始 zip 文件的 cdr 偏移量：${eocdr.cdrOffset}")

        val signedFile = File(zip.absolutePath + ".signed")
        zip.insertAsNewFile(eocdr.cdrOffset, signBlockBytes, signedFile)
        val cdrOffset = eocdr.cdrOffset + signBlockBytes.size
        println("新 zip 文件的 cdr 偏移量：${cdrOffset}")

        val cdrOffsetBytes = cdrOffset.toLittleEndianBytes()
        signedFile.overwrite(eocdr.offset + signBlockBytes.size + 16, cdrOffsetBytes)
        return signedFile
    }

}