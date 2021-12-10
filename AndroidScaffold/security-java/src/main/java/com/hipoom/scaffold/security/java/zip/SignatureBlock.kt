package com.hipoom.scaffold.security.java.zip

import com.hipoom.scaffold.core.java.ktx.*
import java.util.*

/**
 * 签名块。
 *
 * @author ZhengHaiPeng
 * @since 2021/12/11 2:08 上午
 */
class SignatureBlock(

    /**
     * 必须是 16 个字符的字符串，例如：
     * "APK Sig Block 42"、"Hipoom Zip Sign!"
     */
    val magic: String,

    /**
     * ID-Value 键值对
     */
    pairs: List<IDAndValue>

) {

    companion object

    val idValues = LinkedList(pairs)

    /**
     * 将签名块构建为字节数组。
     */
    fun build(): ByteArray {
        // 计算总长度
        var size = 0

        // 遍历每一个键值对，累计大小
        idValues.forEach {
            size += 8 + 4 + it.value.size
        }

        // 签名块尾部的「大小」、「魔数」
        size += 8 + 16

        val bytes = ByteArray(size + 8)

        // 块首的大小，8字节
        size.toLong().toLittleEndianBytes().copyInto(bytes)

        var bytesOffset = 8

        // 遍历并写入每一个数据块
        idValues.forEach {
            // 当前键值对块的大小（不含自身的8字节）
            val currentPairSize = (4 + it.value.size).toLong()
            currentPairSize.toLittleEndianBytes().copyInto(bytes, bytesOffset)
            bytesOffset += 8
            // ID
            it.id.toLittleEndianBytes().copyInto(bytes, bytesOffset)
            bytesOffset += 4
            // Value
            it.value.copyInto(bytes, bytesOffset)
            bytesOffset += it.value.size
        }

        // 块尾的大小，8字节
        size.toLong().toLittleEndianBytes().copyInto(bytes, bytesOffset)
        bytesOffset += 8

        // 块尾的魔数
        magic.toAsciiBytes().copyInto(bytes, bytesOffset)
        return bytes
    }

    /**
     * 添加一个 ID-Value 键值对
     */
    fun put(id: Int, value: ByteArray) {
        idValues.add(IDAndValue(id, value))
    }
}

/**
 * 签名块中用到的 ID-Value 键值对。
 */
class IDAndValue(
    val id: Int,
    val value: ByteArray
)

/**
 * 读取字节数组，生成一个签名块。
 */
fun SignatureBlock.Companion.read(bytes: ByteArray): SignatureBlock? {
    if (bytes.size < 16) {
        return null
    }

    // 末尾16字节的魔数
    val magic = bytes.subArray(bytes.size - 16, 16).toCharSequence()

    // 签名块大小，验证前后两个大小是否相等，且等于当前签名块的大小
    val sizeHead = bytes.subArray(0, 8).littleEndianLong()
    val sizeTail = bytes.subArray(bytes.size - 16 - 8, 8).littleEndianLong()
    if (sizeHead != sizeTail/* || sizeHead != bytes.size.toLong()*/) {
        return null
    }

    var offset = 8

    // 挨个解析 ID-Value 键值对
    val idValues = LinkedList<IDAndValue>()
    while(true) {
        if (offset >= bytes.size - 16 - 8) {
            break
        }

        // 8字节的总长度（不包含当前8字节）
        val pairSize = bytes.subArray(offset, 8).littleEndianLong()
        offset += 8

        // 4字节的ID
        val id = bytes.subArray(offset, 4).littleEndianInt()
        offset += 4

        // 剩余字节的 value
        val value = bytes.subArray(offset, (pairSize - 4).toInt())
        offset += (pairSize - 4).toInt()

        idValues.add(IDAndValue(
            id = id,
            value = value
        ))
    }

    return SignatureBlock(
        magic = magic,
        pairs = idValues
    )
}


fun main() {
    val bytes = SignatureBlock("Hipoom Zip Sign!", listOf(
        IDAndValue(931114, "This is Hipoom".toAsciiBytes())
    )).build()

//    val file = File("/Users/zhp/Downloads/toutiao.apk")
//    val eocdr = file.readEoCDR() ?: return
//    println("eocdr的偏移量:${eocdr.offset}")
//    println("中央目录的大小:${eocdr.cdrSize}")
//    println("中央目录的偏移量:${eocdr.cdrOffset}")

//    val signBlockSize = file.readBytes((eocdr.cdrOffset - 16 - 8).toLong(), 8).littleEndianLong()
//    val signBlockBytes = file.readBytes(eocdr.cdrOffset - signBlockSize - 8, (signBlockSize + 8).toInt())

    val block = SignatureBlock.read(bytes)
    block?.idValues?.forEach {
        // V2 签名
        if (it.id == 0x7109871a) {
            return@forEach
        }

        println("ID    : ${it.id}")
        println("Value : ${it.value.toCharSequence()}")
        println()
    }

//    File("/Users/zhp/Workspace/SignBlock.hex").writeBytes(signBlockBytes)
}