package utils

/**
 * A utility object for managing note categories and checking the validity of a category.
 */
object CategoryUtility {
    /**
     * Set of predefined categories. You can add more categories here.
     */
    @JvmStatic
    val categories = setOf("Home", "College")

    /**
     * Checks if the provided category is a valid category by comparing it to the predefined categories.
     *
     * @param categoryToCheck The category to be validated.
     * @return `true` if the category is valid, `false` otherwise.
     */
    @JvmStatic
    fun isValidCategory(categoryToCheck: String?): Boolean {
        for (category in categories) {
            if (category.equals(categoryToCheck, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}
