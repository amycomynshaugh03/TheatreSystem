package utils

/**
 * A utility object providing common validation functions for working with ranges and lists.
 */
object Utilities {
    /**
     * Checks if a given number is within a specified range.
     *
     * @param numberToCheck The number to be validated.
     * @param min The minimum value of the range (inclusive).
     * @param max The maximum value of the range (inclusive).
     * @return `true` if the number is within the specified range, `false` otherwise.
     */
    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    /**
     * Checks if the provided index is valid for accessing an element in a list.
     *
     * @param index The index to be validated.
     * @param list The list to check the index against.
     * @return `true` if the index is a valid index for the list, `false` otherwise.
     */
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}