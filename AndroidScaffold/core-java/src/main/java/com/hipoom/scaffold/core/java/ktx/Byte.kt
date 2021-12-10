package com.hipoom.scaffold.core.java.ktx

/**
 * 将一个字节转为 16 进制的字符串。
 */
fun Byte.toUnsignedHexString(): String {
    return toUByte().toString(16).uppercase()
}

fun main() {
    for (i in Byte.MIN_VALUE until Byte.MAX_VALUE) {
        println("$i: " + i.toByte().toUnsignedHexString());
    }
}