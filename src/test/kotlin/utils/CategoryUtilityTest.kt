import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.CategoryUtility.categories
import utils.CategoryUtility.isValidCategory

/**
 * This test class is used to test the functionality of the [CategoryUtility] object.
 */
internal class CategoryUtilityTest {

    /**
     * Tests that the [categories] set contains the expected categories.
     */
    @Test
    fun categoriesReturnsFullCategoriesSet() {
        // Asserts that the categories set contains 2 elements.
        Assertions.assertEquals(2, categories.size)

        // Asserts that the categories set contains "Home" and "College".
        Assertions.assertTrue(categories.contains("Home"))
        Assertions.assertTrue(categories.contains("College"))

        // Asserts that the categories set does not contain an empty string.
        Assertions.assertFalse(categories.contains(""))
    }

    /**
     * Tests the behavior of [isValidCategory] when a valid category is provided.
     */
    @Test
    fun isValidCategoryTrueWhenCategoryExists() {
        // Asserts that the function returns true for valid categories in different cases.
        Assertions.assertTrue(isValidCategory("Home"))
        Assertions.assertTrue(isValidCategory("home"))
        Assertions.assertTrue(isValidCategory("COLLEGE"))
    }

    /**
     * Tests the behavior of [isValidCategory] when an invalid category is provided.
     */
    @Test
    fun isValidCategoryFalseWhenCategoryDoesNotExist() {
        // Asserts that the function returns false for invalid categories.
        Assertions.assertFalse(isValidCategory("Hom"))
        Assertions.assertFalse(isValidCategory("colllege"))
        Assertions.assertFalse(isValidCategory(""))
    }
}