package models

import utils.Utilities

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

    fun checkPaymentStatus(): Boolean {
        if (booking.isNotEmpty()) {
            for (booking in booking) {
                if (!booking.isPaymentComplete){
                    return false
                }
            }
        }
        return true
    }

    fun listBooking() =
        if(booking.isEmpty()) "\t No Bookings Added"
        else Utilities.formatSetString(booking)

    override fun toString(): String {
        val archived = if (isEventArchived) 'Y' else 'N'
        return "$eventId: $eventTitle, Category($eventCategory), Description ($eventDuration), Age Rating ($ageRating), Ticket Price ($ticketPrice), Duration ($eventDuration), Archived($archived) \n ${listBooking()}"
    }
}