@file:Suppress("SpellCheckingInspection")
package com.hipoom.scaffold.zip

/**
 * @author ZhengHaiPeng
 * @since 2021/12/7 12:13 上午
 */

import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.zip.ZipFile

fun getEoCDROffset(file: File): Long {
    try {
        val zip = ZipFile(file)
        val commentBytes = zip.comment?.toByteArray()
        val commentLength = commentBytes?.size ?: 0
        val eocdrLength = commentLength + 22
        return file.length() - eocdrLength
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return -1
}

fun readBytes(file: File, offset: Long, length: Int = 0): ByteArray {
    var size = length
    if (size <= 0) {
        size = (file.length() - offset).toInt()
    }
    val buffer = ByteArray(size)
    val fis = FileInputStream(file)
    fis.skip(offset)
    fis.read(buffer)
    return buffer
}

fun ByteArray.sub(offset: Int, length: Int = 0): ByteArray {
    var size = length
    if (size <= 0) {
        size = (this.size - offset)
    }
    val buffer = ByteArray(size)
    System.arraycopy(this, offset, buffer, 0, size)
    return buffer
}

fun ByteArray.isEoCDRMagicNumber(): Boolean {
    if (size != 4) {
        return false
    }

    return this contentEquals byteArrayOf(80, 75, 5, 6)
}

fun File.readEoCDR(): EoCDR? {
    // 获取 EoCDR 的偏移量
    val offset = getEoCDROffset(this)
    // 读取整个 EoCDR
    val bytes = readBytes(this, offset)
    // 读取 EoCDR 开始位置的魔数
    val magicNumber = bytes.sub(0, 4)
    // 验证魔数是否正确
    val isMagicNum = magicNumber.isEoCDRMagicNumber()
    if (!isMagicNum) {
        return null
    }
    // 读取 cdr 的偏移量
    val cdrSizeBytes = readBytes(this, offset + 12, 4)


    return null
}


fun ByteArray.toLittleEndianLong(): Long {
    if (size != 4 && size != 8) {
        throw IllegalArgumentException("只支持4字节或者8字节的 ByteArray 转 Long")
    }


}

fun main() {
    val file = File("/Users/zhp/Documents/tencent-map.apk")
    val offset = getEoCDROffset(file)
    println("offset = $offset");
    val bytes = readBytes(file, offset)

    val magicNumber = bytes.sub(0, 4)
    val isMagicNum = magicNumber.isEoCDRMagicNumber()
    println("isMagicNum = $isMagicNum")

    file.readEoCDR()
}