package com.hipoom.scaffold.security.java

import java.io.InputStream
import java.lang.Exception
import java.security.KeyPair
import java.security.KeyStore
import java.security.PrivateKey

/**
 * 读取 KeyStore 的工具。
 *
 * @author ZhengHaiPeng
 * @since 2021/12/6 11:59 下午
 */
class KeyStoreReader {

    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    /**
     * 读取一个 Android 的密钥库。
     *
     * @param inputStream   密钥库文件的 inputStream。
     * @param password      密钥库的密码
     * @param alias         需要读取的 alias 别名
     * @param aliasPassword alias 别名对应的密码
     */
    fun read(inputStream: InputStream, password: String, alias: String, aliasPassword: String): KeyPair? {
        try {
            val keyStore = KeyStore.getInstance("PKCS12")
            keyStore.load(inputStream, password.toCharArray())

            // 私钥
            val privateKey = keyStore.getKey(alias, aliasPassword.toCharArray())

            // 公钥
            val certification = keyStore.getCertificate(alias)
            val publicKey = certification.publicKey

            return KeyPair(publicKey, privateKey as PrivateKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}