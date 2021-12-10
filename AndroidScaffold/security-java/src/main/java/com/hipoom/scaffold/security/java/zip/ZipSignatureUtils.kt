package com.hipoom.scaffold.security.java.zip

import com.hipoom.scaffold.core.java.ktx.insertAsNewFile
import com.hipoom.scaffold.core.java.ktx.littleEndianLong
import com.hipoom.scaffold.core.java.ktx.overwrite
import com.hipoom.scaffold.core.java.ktx.toLittleEndianBytes
import com.hipoom.scaffold.security.java.KeyStoreReader
import com.hipoom.scaffold.security.java.SHA256Utils
import com.hipoom.scaffold.zip.readEoCDR
import com.hipoom.scaffold.core.java.ktx.*
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
    fun signature(zip: File, privateKey: PrivateKey) {
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
        insertSignBlockAndUpdateEoCDR(zip, signBlockBytes)
    }

    fun readSignBlocks(file: File): SignatureBlock? {
        val eocdr = file.readEoCDR() ?: return null
        val signBlockSize = file.readBytes((eocdr.cdrOffset - 16 - 8).toLong(), 8).littleEndianLong()
        val signBlockBytes = file.readBytes(eocdr.cdrOffset - signBlockSize - 8, (signBlockSize + 8).toInt())
        return SignatureBlock.read(signBlockBytes)
    }




    /* ======================================================= */
    /* Private Methods                                         */
    /* ======================================================= */

    /**
     * 往一个 zip 文件中插入一个签名块，并且更新它的 EoCDR 中记录的偏移信息。
     */
    private fun insertSignBlockAndUpdateEoCDR(zip: File, signBlockBytes: ByteArray): Boolean {
        val eocdr = zip.readEoCDR() ?: return false
        val signingFile = File(zip.absolutePath + ".signed")
        zip.insertAsNewFile(eocdr.cdrOffset, signBlockBytes, signingFile)
        val cdrOffset = eocdr.cdrOffset + signBlockBytes.size
        val cdrOffsetBytes = cdrOffset.toLittleEndianBytes()
        signingFile.overwrite(eocdr.offset + signBlockBytes.size + 16, cdrOffsetBytes)
        return true
    }

    /**
     * 校验文件的签名。
     */
    fun verify(zip: File, publicKey: PublicKey) {

    }

}

fun main() {

    val keyStore = "/Users/zhp/Workspace/Hipoom.ks"
    val keys = KeyStoreReader().read(File(keyStore).inputStream(), "12345678", "haipeng", "12345678") ?: return

    val zip = File("/Users/zhp/Workspace/SignBlock.hex.zip")
    ZipSignatureUtils().signature(zip, keys.private)

    ZipSignatureUtils().readSignBlocks(File("/Users/zhp/Workspace/SignBlock.hex.zip.signed"))?.idValues?.forEach {
        println("ID    : ${it.id}")
//        println("Value : ${it.value.toCharSequence()}")
        println()
    }
}