package com.hipoom.scaffold.core.java.ktx

fun Int.toLittleEndianBytes(): ByteArray {
    return byteArrayOf(
        (this.and(0xFF)).toByte(),
        (this.shr(8).and(0xFF)).toByte(),
        (this.shr(16).and(0xFF)).toByte(),
        (this.shr(24).and(0xFF)).toByte()
    )
}