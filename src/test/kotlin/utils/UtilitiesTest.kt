package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.Utilities.validRange

/**
 * This test class is used to test the functionality of the [Utilities] object.
 */
class UtilitiesTest {

    /**
     * Tests the [validRange] function with various positive test cases.
     */
    @Test
    fun validRangeWorksWithPositiveTestData() {
        // Asserts that validRange returns true with valid positive test data.
        Assertions.assertTrue(validRange(1, 1, 1))
        Assertions.assertTrue(validRange(1, 1, 2))
        Assertions.assertTrue(validRange(1, 0, 1))
        Assertions.assertTrue(validRange(1, 0, 2))
        Assertions.assertTrue(validRange(-1, -2, -1)) // Valid with negative values.
    }

    /**
     * Tests the [validRange] function with various negative test cases.
     */
    @Test
    fun validRangeWorksWithNegativeTestData() {
        // Asserts that validRange returns false with invalid test data.
        Assertions.assertFalse(validRange(1, 0, 0))
        Assertions.assertFalse(validRange(1, 1, 0))
        Assertions.assertFalse(validRange(1, 2, 1))
        Assertions.assertFalse(validRange(-1, -1, -2)) // Invalid with negative values.
    }
}