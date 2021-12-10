package com.hipoom.scaffold.core.java.ktx

fun Long.toLittleEndianBytes(): ByteArray {
    return byteArrayOf(
        (this.and(0xFF)).toByte(),
        (this.shr(8).and(0xFF)).toByte(),
        (this.shr(16).and(0xFF)).toByte(),
        (this.shr(24).and(0xFF)).toByte(),
        (this.shr(32).and(0xFF)).toByte(),
        (this.shr(40).and(0xFF)).toByte(),
        (this.shr(48).and(0xFF)).toByte(),
        (this.shr(56).and(0xFF)).toByte()
    )
}