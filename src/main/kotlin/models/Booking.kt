package models

data class Booking(var bookingId: Int = 0,
                   var bookingDate: String,
                   var bookingTime: String,
                   var customerName: String,
                   var customerPhone: Int = 0,
                   var paymentMethod: String,
                   var isPaymentComplete: Boolean = false
) {
    override fun toString(): String {
        if (isPaymentComplete)
            return "$bookingId: $customerName (Paid)"
        else
            return "$bookingId: $customerName (Unpaid)"
    }


}