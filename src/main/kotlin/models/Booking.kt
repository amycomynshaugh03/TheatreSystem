package models

/**
 * Data class representing a booking for an event.
 *
 * @property bookingId The unique identifier for the booking.
 * @property bookingDate The date of the booking.
 * @property bookingTime The time of the booking.
 * @property customerName The name of the customer making the booking.
 * @property customerPhone The phone number of the customer making the booking.
 * @property paymentMethod The payment method used for the booking.
 * @property isPaymentComplete A flag indicating whether the payment for the booking is complete.
 */
data class Booking(
    var bookingId: Int = 0,
    var bookingDate: String = "Unavailable",
    var bookingTime: String = "Unavailable",
    var customerName: String = "Unavailable",
    var customerPhone: Int = 0,
    var paymentMethod: String = "Unavailable",
    var isPaymentComplete: Boolean = false
) {
    /**
     * Returns a string representation of the booking, indicating whether the payment is complete.
     *
     * @return A formatted string representing the booking status and customer name.
     */
    override fun toString(): String {
        return if (isPaymentComplete)
            "$bookingId: $customerName (Paid)"
        else
            "$bookingId: $customerName (Unpaid)"
    }
}