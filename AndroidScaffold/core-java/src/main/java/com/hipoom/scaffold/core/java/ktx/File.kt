package com.hipoom.scaffold.core.java.ktx

import java.io.*

/**
 * 覆盖掉文件中从 [offset] 开始，长度为 [bytes] 长度的一段数据。
 */
fun File.overwrite(offset: Long, bytes: ByteArray) {
    val raf = RandomAccessFile(this, "rw")
    raf.seek(offset)
    raf.write(bytes)
    raf.close()
}

/**
 * 从 [offset] 位置开始，拷贝当前文件的剩余内容到 [output] 中。
 */
fun File.copyTo(offset: Long, output: OutputStream) {
    val raf = RandomAccessFile(this, "r")
    raf.seek(offset)

    val buffer = ByteArray(1024)
    var length = raf.read()
    while (length >= 0) {
        output.write(buffer, 0, length)
        length = raf.read()
    }
}

/**
 * 从 [offset] 位置开始，插入一个 [bytes] 数组的内容到文件。
 * 例如， offset = 0, 表示插在文件开头。
 */
fun File.insertAsNewFile(offset: Int, bytes: ByteArray, dest: File) {
    // 拷贝前 offset-1 个字节
    val output = copyHeadBytes(offset, dest)

    // 写入 bytes
    output.write(bytes)

    // 写入剩下的
    val raf = RandomAccessFile(this, "r")
    raf.seek(offset.toLong())
    raf.copyInto(output)

    // 关闭
    output.close()
    raf.close()
}

/**
 * 拷贝文件的前 [length] 个字节到新文件 [dest] 中。
 * 对于文件 [0, 1, 2, 3, 4, 5, 6, 7]，copyFrontBytes(3, x) 得到：
 * [0, 1, 2]
 */
fun File.copyHeadBytes(length: Int, dest: File): FileOutputStream {
    // 如果新文件已经存在，删掉它
    if (dest.exists()) {
        dest.delete()
    }

    // 确保父目录存在
    ensureParentDirectory()

    // 创建新文件
    dest.createNewFile()

    // 输出流
    val output = dest.outputStream()
    if (length <= 0) {
        return output
    }

    // 先读取前 offset 个字节写入新文件
    inputStream().use { input ->
        var sum = 0
        val buffer = ByteArray(1024)
        var readed = input.read(buffer)
        while (readed >= 0) {
            if (sum + readed > length) {
                val size = (length -  sum)
                output.write(buffer, 0, size)
                sum += size
                break
            }

            output.write(buffer, 0, readed)
            sum += readed
            readed = input.read(buffer)
        }
    }

    return output
}

/**
 * 删除文件从 [start] 位置开始，长度为 [length] 的数据，并保存到新文件 [dest]
 * 对于文件 [0, 1, 2, 3, 4, 5, 6, 7]， deleteSegment(3, 2, x) 后，还剩余：
 * [0, 1, 2, 5, 6, 7]。
 */
fun File.deleteSegment(start: Int, length: Int, dest: File) {
    // 拷贝前 offset 个字节
    val output = copyHeadBytes(start, dest)

    // 再复制后面的内容
    val raf = RandomAccessFile(this, "r")
    raf.seek((start + length).toLong())
    raf.copyInto(output)

    // 关闭
    output.close()
    raf.close()
}

/**
 * 确保父文件夹已经创建。
 */
fun File.ensureParentDirectory() {
    val p = parentFile
    if (p != null && !p.exists()) {
        p.mkdirs()
    }
}

/**
 * 读取文件从 [offset] 开始，长度为 [length] 的一段字节数组。
 */
fun File.readBytes(offset: Long, length: Int = 0): ByteArray {
    var size = length
    if (size <= 0) {
        size = (this.length() - offset).toInt()
    }
    val buffer = ByteArray(size)
    val fis = FileInputStream(this)
    fis.skip(offset)
    fis.read(buffer)
    return buffer
}