@file:Suppress("SpellCheckingInspection")
package com.hipoom.scaffold.zip

/**
 * @author ZhengHaiPeng
 * @since 2021年12月11日00:20:56
 */
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.zip.ZipFile
import com.hipoom.scaffold.core.java.ktx.readBytes
import com.hipoom.scaffold.core.java.ktx.subArray

/**
 * 获取一个 zip 文件的 oCDR 起始位置的偏移量。
 * 获取异常时，返回 -1。
 */
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
    val bytes = readBytes(offset)
    // 读取 EoCDR 开始位置的魔数
    val magicNumber = bytes.subArray(0, 4)
    // 验证魔数是否正确
    val isMagicNum = magicNumber.isEoCDRMagicNumber()
    if (!isMagicNum) {
        return null
    }
    // 读取 cdr 的偏移量
    val cdrSizeBytes = readBytes(offset + 12, 4)


    return null
}


fun ByteArray.toLittleEndianLong(): Long {
    if (size != 4 && size != 8) {
        throw IllegalArgumentException("只支持4字节或者8字节的 ByteArray 转 Long")
    }

    return -1
}

fun main() {
//    val file = File("/Users/zhp/Documents/tencent-map.apk")
//    val offset = getEoCDROffset(file)
//    println("offset = $offset");
//    val bytes = readBytes(file, offset)
//
//    val magicNumber = bytes.sub(0, 4)
//    val isMagicNum = magicNumber.isEoCDRMagicNumber()
//    println("isMagicNum = $isMagicNum")
//
//    file.readEoCDR()
}