//package me.haipeng.scaffold.zip
//
//import java.lang.Exception
//import java.lang.StringBuilder
//
//fun Byte.toHexString(): String {
//    val i = if (this < 0) 256 + this else this.toInt()
//    val v1 = (i / 16).toByte().toString(16)
//    val v2 = (i % 16).toByte().toString(16)
//    return "$v1$v2"
//}
//
//
//fun ByteArray.toHexString(): String {
//    val sb = StringBuilder()
//
//    forEach {
//        sb.append(it.toHexString())
//    }
//
//    return sb.toString()
//}
//
//
//fun ByteArray.toHexString4Char(): String {
//    val sb = StringBuilder()
//
//    forEach {
//        sb.append(it.toHexString())
//    }
//
//    return sb.toString().appenBlankStillSize(4)
//}
//
//fun ByteArray.toHexString8Char(): String {
//    val sb = StringBuilder()
//
//    forEach {
//        sb.append(it.toHexString())
//    }
//
//    return sb.toString().appenBlankStillSize(8)
//}
//
//fun ByteArray.toHexString16Char(): String {
//    val sb = StringBuilder()
//
//    forEach {
//        sb.append(it.toHexString())
//    }
//
//    return sb.toString().appenBlankStillSize(16)
//}
//
//fun ByteArray.toCharSequence(): String {
//    val sb = StringBuilder()
//
//    forEach {
//        sb.append(it.toChar())
//    }
//
//    return sb.toString()
//}
//
///**
// * 小端
// */
//fun ByteArray.littleEndianInt(): Int {
//    require(this.size <= 8) { "暂不支持大于 32 位的 byte 数组转 Int 值。" }
//
//    val sb = StringBuilder()
//    for (i in size - 1 downTo 0) {
//        sb.append(this[i].toHexString())
//    }
//
//    try {
//        return sb.toString().toInt(16)
//    } catch (e: Exception) {
//        log { "===>" }
//        forEach {
//            log { "${it.toChar()}/$it/${it.toHexString()}" }
//        }
//        log { "<===" }
//        throw e
//    }
//}
//
///**
// * 大端
// */
//fun ByteArray.bigEndianInt(): Int {
//    require(this.size <= 8) { "暂不支持大于 32 位的 byte 数组转 Int 值。" }
//
//    val sb = StringBuilder()
//    for (i in this) {
//        sb.append(i.toHexString())
//    }
//
//    try {
//        return sb.toString().toInt(16)
//    } catch (e: Exception) {
//        log { "===>" }
//        forEach {
//            log { "${it.toChar()}/$it/${it.toHexString()}" }
//        }
//        log { "<===" }
//        throw e
//    }
//}
//
//
//
///**
// * 小端
// */
//fun ByteArray.littleEndianLong(): Long {
//    require(this.size <= 8) { "暂不支持大于 32 位的 byte 数组转 Long 值。" }
//
//    val sb = StringBuilder()
//    for (i in size - 1 downTo 0) {
//        sb.append(this[i].toHexString())
//    }
//
//    try {
//        return sb.toString().toLong(16)
//    } catch (e: Exception) {
//        log { "===>" }
//        forEach {
//            log { "${it.toChar()}/$it/${it.toHexString()}" }
//        }
//        log { "<===" }
//        throw e
//    }
//}
//
///**
// * 大端
// */
//fun ByteArray.bigEndianLong(): Long {
//    require(this.size <= 8) { "暂不支持大于 32 位的 byte 数组转 Int 值。" }
//
//    val sb = StringBuilder()
//    for (i in this) {
//        sb.append(i.toHexString())
//    }
//
//    try {
//        return sb.toString().toLong(16)
//    } catch (e: Exception) {
//        log { "===>" }
//        forEach {
//            log { "${it.toChar()}/$it/${it.toHexString()}" }
//        }
//        log { "<===" }
//        throw e
//    }
//}
//
//fun ByteArray.toMyString(): String {
//    return "原始16进制：${toHexString()}, 转为int值：${littleEndianInt()}"
//}
//
//
///**
// * 获取一段子数组。
// *
// * @param start  从这里开始
// * @param length 子数组的长度
// */
//fun ByteArray.subArray(start: Int, length: Int): ByteArray {
//    val res = ByteArray(length)
//
//    System.arraycopy(
//        /* src     =*/ this,
//        /* srcPos  =*/ start,
//        /* dest    =*/ res,
//        /* destPos =*/ 0,
//        /* length  =*/ length
//    )
//
//    return res
//}
//
///**
// * 获取一段子数组。
// *
// * @param start  从这里开始，知道原始数组的末尾
// */
//fun ByteArray.subArrayFrom(start: Int): ByteArray {
//
//    val realStart =
//        if (start >= 0) {
//            start
//        } else {
//            size + start
//        }
//
//    val length = size - realStart
//    val res = ByteArray(length)
//    System.arraycopy(
//        /* src     =*/ this,
//        /* srcPos  =*/ realStart,
//        /* dest    =*/ res,
//        /* destPos =*/ 0,
//        /* length  =*/ length
//    )
//
//    return res
//
//}
//
//
///**
// * 从一个位置开始，读取四个字节，并转为小端的int值
// */
//fun ByteArray.readLittleEndianIntFrom(start: Int): Int {
//    return subArray(start, 4).littleEndianInt()
//}
//
//
///**
// * 将数组转为16进制字符串
// */
//fun ByteArray.byte2HexFormatted(): String {
//    val str = StringBuilder(size * 2)
//    for (i in indices) {
//        var h = Integer.toHexString(get(i).toInt())
//        val l = h.length
//        if (l == 1) h = "0$h"
//        if (l > 2) h = h.substring(l - 2, l)
//        str.append(h.toUpperCase())
//        if (i < size - 1) str.append(':')
//    }
//    return str.toString()
//}