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