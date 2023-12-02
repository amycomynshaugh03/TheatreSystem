package utils

import utils.ScannerInput.readNextInt
import java.util.*

/**
 * A utility object for validating user input related to categories and priorities.
 */
object ValidateInput {

    /**
     * Prompts the user to enter a valid category and validates the input.
     *
     * @param prompt The message displayed to prompt the user for input.
     * @return A valid category entered by the user.
     */
    @JvmStatic
    fun readValidCategory(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (CategoryUtility.isValidCategory(input))
                return input
            else {
                print("Invalid category $input. Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    /**
     * Prompts the user to enter a valid priority and validates the input.
     *
     * @param prompt The message displayed to prompt the user for input.
     * @return A valid priority (an integer between 1 and 5) entered by the user.
     */
    @JvmStatic
    fun readValidPriority(prompt: String?): Int {
        var input = readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1, 5))
                return input
            else {
                print("Invalid priority $input. Please try again: ")
                input = readNextInt(prompt)
            }
        } while (true)
    }
}