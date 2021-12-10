package com.hipoom.scaffold.zip

/**
 * 用于记录这份数据在文件中，相对某个开始位置的偏移量，和从该偏移量往后多少字节的长度。
 *
 * @author ZhengHaiPeng
 * @since 2021/12/7 12:14 上午
 */
data class OffsetAndSize(
    /**
     *开始位置相对于某一个位置的偏移量。
     */
    val offset: Long,

    /**
     * 这份数据的长度。
     */
    val size: Long
)