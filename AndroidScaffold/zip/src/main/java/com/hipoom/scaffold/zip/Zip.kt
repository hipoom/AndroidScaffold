@file:Suppress("SpellCheckingInspection")
package com.hipoom.scaffold.zip

/**
 * @author ZhengHaiPeng
 * @since 2021年12月11日00:20:56
 */
import com.hipoom.scaffold.core.java.ktx.littleEndianInt
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.zip.ZipFile
import com.hipoom.scaffold.core.java.ktx.readBytes
import com.hipoom.scaffold.core.java.ktx.subArray
import com.hipoom.scaffold.core.java.ktx.toCharSequence

/**
 * 获取一个 zip 文件的 oCDR 起始位置的偏移量。
 * 获取异常时，返回 -1。
 */
fun File.getEoCDROffset(): Long {
    try {
        val zip = ZipFile(this)
        val commentBytes = zip.comment?.toByteArray()
        val commentLength = commentBytes?.size ?: 0
        val eocdrLength = commentLength + 22
        return length() - eocdrLength
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return -1
}

/**
 * 读取文件的中央目录。
 */
fun File.readEoCDR(): EoCDR? {
    // 获取 EoCDR 的偏移量
    val offset = this.getEoCDROffset()
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
    val cdrSizeBytes = bytes.subArray(12, 4)
    val cdrSize = cdrSizeBytes.littleEndianInt()

    // 读取 cdr 的大小
    val cdrOffsetBytes = bytes.subArray(16, 4)
    val cdrOffset = cdrOffsetBytes.littleEndianInt()

    return EoCDR(
        offset = offset,
        cdrOffset = cdrOffset,
        cdrSize = cdrSize
    )
}

/**
 * 判断当前文件是否有V2签名
 */
fun File.hasAndroidV2Signature(eocdr: EoCDR?): Boolean {
    val temp = eocdr ?: readEoCDR() ?: return false
    val magicNumber = readBytes((temp.cdrOffset - 16).toLong(), 16).toCharSequence()
    return (magicNumber == "APK Sig Block 42")
}

fun ByteArray.isEoCDRMagicNumber(): Boolean {
    if (size != 4) {
        return false
    }
    return this contentEquals byteArrayOf(80, 75, 5, 6)
}

fun main() {
//    run {
//        val file = File("/Users/zhp/Workspace/Github/AndroidCameraScaffold.zip")
//        val eocdr = file.readEoCDR() ?: return
//        println("eocdr的偏移量:${eocdr.offset}")
//        println("中央目录的大小:${eocdr.cdrSize}")
//        println("中央目录的偏移量:${eocdr.cdrOffset}")
//    }

    // 读取抖音的 apk sign block
    run {
        val file = File("/Users/zhp/Downloads/toutiao.apk")
        val eocdr = file.readEoCDR() ?: return
        println("eocdr的偏移量:${eocdr.offset}")
        println("中央目录的大小:${eocdr.cdrSize}")
        println("中央目录的偏移量:${eocdr.cdrOffset}")
    }
}