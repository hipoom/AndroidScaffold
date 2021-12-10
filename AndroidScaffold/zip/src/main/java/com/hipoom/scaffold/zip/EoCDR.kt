package com.hipoom.scaffold.zip

/**
 * @author ZhengHaiPeng
 * @since 2021/12/7 12:13 上午
 *
 */
class EoCDR(
    /**
     * EoCDR 开始位置，相对于文件的开始位置 的偏移量。
     */
    val offset: Long,

    /**
     * 中央目录开始位置，相对于文件的开始位置 的偏移量。
     */
    val cdrOffset: Long,

    /**
     * 中央目录的大小
     */
    val cdrSize: Long
)