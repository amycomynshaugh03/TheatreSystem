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
}