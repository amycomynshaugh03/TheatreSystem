package models

data class Booking(var bookingId: Int = 0,
                   var bookingDate: String = "Unavailable",
                   var bookingTime: String = "Unavailable",
                   var customerName: String = "Unavailable",
                   var customerPhone: Int = 0,
                   var paymentMethod: String = "Unavailable",
                   var isPaymentComplete: Boolean = false
) {
    override fun toString(): String {
        if (isPaymentComplete)
            return "$bookingId: $customerName (Paid)"
        else
            return "$bookingId: $customerName (Unpaid)"
    }


}