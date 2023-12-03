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

    fun addBooking( bookings: Booking) : Boolean {
        bookings.bookingId = getBookingId()
        return booking.add(bookings)
    }
    fun numberOfBookings() = booking.size
    fun findOne(id: Int): Booking?{
        return booking.find{ booking -> booking.bookingId == id}
    }

    fun update(id: Int, newBooking: Booking): Boolean {
        val foundBooking = findOne(id)
        if (foundBooking != null) {
            foundBooking.bookingDate = newBooking.bookingDate
            foundBooking.bookingTime = newBooking.bookingTime
            foundBooking.customerName = newBooking.customerName
            foundBooking.customerPhone = newBooking.customerPhone
            foundBooking.paymentMethod = newBooking.paymentMethod
            return true
        }
        return false
    }

    fun delete(id: Int): Boolean {
        return booking.removeIf { booking -> booking.bookingId == id}
    }

    fun checkPaymentStatus(): Boolean {
        if (booking.isNotEmpty()) {
            for (bookings in booking) {
                if (!bookings.isPaymentComplete){
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
        return "$eventId: $eventTitle, Category($eventCategory), Description ($eventDuration), Age Rating ($ageRating), Ticket Price ($ticketPrice), Duration ($eventDuration),\n ${listBooking()}"
    }
}

