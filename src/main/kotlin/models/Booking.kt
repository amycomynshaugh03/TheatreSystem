package models

data class Booking(var bookingId: Int = 0,
                   var bookingPerformance: String,
                   var bookingDate: String,
                   var bookingTime: String,
                   var customerName: String,
                   var customerPhone: Int = 0,
                   var paymentMethod: String,
                   var paymentStatus: String,
                   var bookingContents: String,
                   var isPaymentComplete: Boolean = false
) {
    override fun toString(): String {
        if (isPaymentComplete)
            return "$bookingId: $bookingContents (Paid)"
        else
            return "$bookingId: $bookingContents (Unpaid)"
    }


}