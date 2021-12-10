package com.hipoom.scaffold.core.java.ktx

import java.lang.Exception
import java.lang.StringBuilder
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun main() {
    val bytes = byteArrayOf(72, 73, 74, 75)

    println(bytes.toUnsignedHexString())
    println(bytes.toCharSequence())
    println(byteArrayOf(80, 75, 5, 6).littleEndianInt())
//    println(byteArrayOf(127).littleEndianInt())
//
//    println(byteArrayOf(1, -1).toUnsignedHexString())
//    println(byteArrayOf(1, 127).littleEndianInt())
}

/**
 * 将字节数组的每一个元素都当作一个无符号字节，并转为16进制字符串，字符中间以「:」分割。
 * 例如 输入 [72, 73, 74, 75]，  输出： "48:49:4A:4B".
 */
fun ByteArray.toUnsignedHexString(): String {
    val sb = StringBuilder()
    forEach {
        sb.append(it.toUnsignedHexString()).append(":")
    }
    if (sb.isNotEmpty()) {
        sb.deleteCharAt(sb.length - 1)
    }
    return sb.toString()
}

/**
 * 将每个字节都转为对应的 ASCII 字符。
 * 例如 输入 [72, 73, 74]，  输出： "HIJ"。
 * 因为 ascii 的 72 对应字符 'H'。
 */
fun ByteArray.toCharSequence(): String {
    val sb = StringBuilder()
    forEach {
        sb.append(it.toInt().toChar())
    }
    return sb.toString()
}

/**
 * 小端
 */
fun ByteArray.littleEndianInt(): Int {
    if (this.isEmpty()) {
        return 0
    }

    val temp = when(size) {
        1 -> byteArrayOf(this[0], 0, 0, 0)
        2 -> byteArrayOf(this[0], this[1], 0, 0)
        3 -> byteArrayOf(this[0], this[1], this[2], 0)
        else -> this
    }
    return ByteBuffer.wrap(temp)
        .order(ByteOrder.LITTLE_ENDIAN)
        .int
}

/**
 * 大端
 */
fun ByteArray.bigEndianInt(): Int {
    require(this.size <= 8) { "暂不支持大于 32 位的 byte 数组转 Int 值。" }

    val sb = StringBuilder()
    for (i in this) {
        sb.append(i.toUnsignedHexString())
    }

    try {
        return sb.toString().toInt(16)
    } catch (e: Exception) {
        println("===>")
        forEach {
            println("${it.toInt().toChar()}/$it/${it.toUnsignedHexString()}")
        }
        println("<===")
        throw e
    }
}

/**
 * 小端
 */
fun ByteArray.littleEndianLong(): Long {
    require(this.size <= 8) { "暂不支持大于 32 位的 byte 数组转 Long 值。" }

    val sb = StringBuilder()
    for (i in size - 1 downTo 0) {
        sb.append(this[i].toUnsignedHexString())
    }

    try {
        return sb.toString().toLong(16)
    } catch (e: Exception) {
        println("===>")
        forEach {
            println("${it.toInt().toChar()}/$it/${it.toUnsignedHexString()}")
        }
        println("<===")
        throw e
    }
}

/**
 * 大端
 */
fun ByteArray.bigEndianLong(): Long {
    require(this.size <= 8) { "暂不支持大于 32 位的 byte 数组转 Int 值。" }

    val sb = StringBuilder()
    for (i in this) {
        sb.append(i.toUnsignedHexString())
    }

    try {
        return sb.toString().toLong(16)
    } catch (e: Exception) {
        println("===>")
        forEach {
            println("${it.toInt().toChar()}/$it/${it.toUnsignedHexString()}")
        }
        println("<===")
        throw e
    }
}

fun ByteArray.toMyString(): String {
    return "原始16进制：${toUnsignedHexString()}, 转为int值：${littleEndianInt()}"
}

/**
 * 获取一段子数组。
 *
 * @param start  从这里开始
 * @param length 子数组的长度
 */
fun ByteArray.subArray(start: Int, length: Int): ByteArray {
    val res = ByteArray(length)

    System.arraycopy(
        /* src     =*/ this,
        /* srcPos  =*/ start,
        /* dest    =*/ res,
        /* destPos =*/ 0,
        /* length  =*/ length
    )

    return res
}

/**
 * 获取一段子数组。
 *
 * @param start  从这里开始，知道原始数组的末尾
 */
fun ByteArray.subArrayFrom(start: Int): ByteArray {

    val realStart =
        if (start >= 0) {
            start
        } else {
            size + start
        }

    val length = size - realStart
    val res = ByteArray(length)
    System.arraycopy(
        /* src     =*/ this,
        /* srcPos  =*/ realStart,
        /* dest    =*/ res,
        /* destPos =*/ 0,
        /* length  =*/ length
    )

    return res

}

/**
 * 从一个位置开始，读取四个字节，并转为小端的int值
 */
fun ByteArray.readLittleEndianIntFrom(start: Int): Int {
    return subArray(start, 4).littleEndianInt()
}

/**
 * 将数组转为16进制字符串
 */
fun ByteArray.byte2HexFormatted(): String {
    val str = StringBuilder(size * 2)
    for (i in indices) {
        var h = Integer.toHexString(get(i).toInt())
        val l = h.length
        if (l == 1) h = "0$h"
        if (l > 2) h = h.substring(l - 2, l)
        str.append(h.toUpperCase())
        if (i < size - 1) str.append(':')
    }
    return str.toString()
}
