package com.hipoom.scaffold.core.java.ktx

import java.io.FileOutputStream
import java.io.RandomAccessFile

/**
 * 将自己拷贝到 [output] 中。
 */
fun RandomAccessFile.copyInto(output: FileOutputStream) {
    val buffer = ByteArray(1024)
    var length = read(buffer)
    while (length >= 0) {
        output.write(buffer, 0, length)
        length = read(buffer)
    }
}