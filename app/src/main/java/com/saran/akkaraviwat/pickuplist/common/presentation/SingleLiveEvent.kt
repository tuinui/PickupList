package com.saran.akkaraviwat.pickuplist.common.presentation

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class SingleLiveEvent<out T>(private val content: T) {

    var isConsumed = false
        private set // Allow external read but not write

    /**
     * Consumes the content if it's not been consumed yet.
     * @return The unconsumed content or `null` if it was consumed already.
     */
    fun consume(): T? {
        return if (isConsumed) {
            null
        } else {
            isConsumed = true
            content
        }
    }

    /**
     * @return The content whether it's been handled or not.
     */
    fun peek(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SingleLiveEvent<*>

        if (content != other.content) return false
        if (isConsumed != other.isConsumed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + isConsumed.hashCode()
        return result
    }
}
