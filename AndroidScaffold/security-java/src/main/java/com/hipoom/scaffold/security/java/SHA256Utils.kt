package com.hipoom.scaffold.security.java

import java.io.File
import java.io.InputStream
import java.security.MessageDigest

/**
 * SHA-256 相关的工具类。
 *
 * @author ZhengHaiPeng
 * @since 2021年12月10日23:53:10
 */
class SHA256Utils {

    /**
     * 对字节数组计算其 SHA-256 摘要。
     */
    fun digest(bytes: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(bytes)
    }

    /**
     * 对一个输入流计算其摘要。
     */
    fun digest(input: InputStream): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")

        val buffer = ByteArray(1024)
        var length = input.read(buffer)
        while (length >= 0) {
            digest.update(buffer, 0, length)
            length = input.read(buffer)
        }

        return digest.digest()
    }

    /**
     * 计算一个文件的摘要。
     */
    fun digest(file: File): ByteArray {
        var res: ByteArray
        file.inputStream().use {
            res = digest(it)
        }
        return res
    }

    /**
     * 对字节数组计算其 SHA-256 摘要，并将字节转为转为字符串返回。
     * 返回的字符串格式为：32个16进制数以「:」分隔，例如：
     * 1e:e1:92:dd:3e:......:62:cd:82:de:e9:96:b1
     */
    fun digestToString(bytes: ByteArray): String {
        val digest = digest(bytes)
        return bytesToString(digest)
    }

    /**
     * 将字节数组转为16进制文本。
     * 返回的字符串格式为：32个16进制数以「:」分隔，例如：
     * 1e:e1:92:dd:3e:......:62:cd:82:de:e9:96:b1
     */
    fun bytesToString(bytes: ByteArray): String {
        val sb = StringBuilder()
        bytes.forEach { byte ->
            val temp = byte.toUByte().toString(16)
            sb.append(temp).append(":")
        }
        if (sb.endsWith(":")) {
            sb.deleteCharAt(sb.length - 1)
        }
        return sb.toString()
    }

    /**
     * 将一个字符串反解析为字节数组。
     * @param des 一定是由 [digestToString] 或者 [bytesToString] 返回的才可以解析。
     */
    fun decodeSHA256StringToByteArray(des: String): ByteArray {
        val splits = des.split(":")
        val bytes = ByteArray(splits.size)
        splits.forEachIndexed { index, s ->
            bytes[index] = Integer.parseInt(s, 16).toUByte().toByte()
        }
        return bytes
    }

}

fun main() {
    val file = File("/Users/zhp/Downloads/效果.png")
    val utils = SHA256Utils()
    val sha256 = utils.digest(file)
    val string = utils.bytesToString(sha256)
    println(string)
}