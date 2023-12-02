package models

data class Event( var eventId: Int = 0,
                  var eventTitle: String,
                  var eventCategory: String,
                  var eventDescription: String,
                  var ageRating: Int = 0,
                  var ticketPrice: Int = 0,
                  var eventDuration: Int = 0,
                  var booking : MutableSet<Booking> = mutableSetOf()
) {
    private var lastBookingId = 0
    private fun getBookingId() = lastBookingId++

    fun addBooking( booking: Booking) : Boolean {
        booking.bookingId = getBookingId()
        return booking.add(booking)
    }
    fun numberOfBookings() = booking.size
    fun findOne(id: Int): Booking?{
        return booking.find{ booking -> booking.bookingId == id}
    }

    fun update(id: Int, newBooking: Booking): Boolean {
        val foundBooking = findOne(id)
        if (foundBooking != null) {
            foundBooking.bookingContents = newBooking.bookingContents
            foundBooking.isPaymentComplete = newBooking.isPaymentComplete
            return true
        }
        return false
    }

    fun delete(id: Int): Boolean {
        return booking.removeIf { booking -> booking.bookingId == id}
    }
}