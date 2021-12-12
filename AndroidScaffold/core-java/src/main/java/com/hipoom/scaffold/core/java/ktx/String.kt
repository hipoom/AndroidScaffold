package com.hipoom.scaffold.core.java.ktx

import java.lang.StringBuilder

/**
 * 在字符串的末尾添加空格「 」直到长度等于 size。
 * 如果字符串的长度本来就大于等于 size，返回自身。
 */
fun String.appendBlankStillSize(size: Int): String {
    if (this.length >= size) {
        return this
    }
    val sb = StringBuilder(this)
    while (sb.length < size) {
        sb.append(" ")
    }
    return sb.toString()
}

/**
 * 将字符串转为对应的 ASCII 字节数组。
 * 用途：
 * 将「APK Sig Block 42」转为 [65, 80, 75, 32, 83, 105, 103, 32, 66, 108, 111, 99, 107, 32, 52, 50]
 */
fun String.toAsciiBytes(): ByteArray {
    val bytes = ByteArray(length)
    forEachIndexed { index, char ->
        bytes[index] = char.code.toByte()
    }
    return bytes
}

/**
 * 将字符串数组拼接为一个字符串，中间用 [delimiter] 分隔。
 */
fun List<String>.join(delimiter: String): String {
    return joinToString(delimiter)
}

/**
 * 将字符串数组拼接为一个字符串，中间用 [delimiter] 分隔。
 */
fun Array<String>.join(delimiter: String): String {
    return joinToString(delimiter)
}

fun main() {
    val bytes = "APK Sig Block 42".toAsciiBytes()
    println(bytes)
}