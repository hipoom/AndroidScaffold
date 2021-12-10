package com.hipoom.scaffold.security.java.zip

import com.hipoom.scaffold.security.java.SHA256Utils
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

    /**
     * 对文件进行签名。
     *
     * @param zip        需要签名的压缩文件
     * @param message    签名时的备注内容
     * @param privateKey 私钥
     */
    fun signature(zip: File, message: String, privateKey: PrivateKey) {
        // 计算原始文件的 SHA256 摘要
        val utils = SHA256Utils()
        val sha256 = utils.digest(zip)

        // 对摘要进行加密
        val encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey)
        val encryptDigest = encryptCipher.doFinal(sha256)

        // 构建签名块

    }

    /**
     * 校验文件的签名。
     */
    fun verify(zip: File, publicKey: PublicKey) {

    }

}