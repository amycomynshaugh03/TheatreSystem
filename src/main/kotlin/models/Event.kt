package models

import utils.Utilities

/**
 * Data class representing an event at the theater.
 *
 * @property eventId The unique identifier for the event.
 * @property eventTitle The title of the event.
 * @property eventCategory The category or genre of the event.
 * @property eventDescription A description of the event.
 * @property ageRating The age rating for the event.
 * @property ticketPrice The price for a ticket for the event.
 * @property eventDuration The duration of the event in minutes.
 * @property booking A set of bookings associated with the event.
 */
data class Event(
    var eventId: Int = 0,
    var eventTitle: String = "Unavailable",
    var eventCategory: String = "Unavailable",
    var eventDescription: String = "Unavailable",
    var ageRating: Int = 0,
    var ticketPrice: Int = 0,
    var eventDuration: Int = 0,
    var booking: MutableSet<Booking> = mutableSetOf()
) {
    private var lastBookingId = 0

    /**
     * Generates a unique booking ID for a new booking.
     *
     * @return A unique booking ID.
     */
    private fun getBookingId() = lastBookingId++

    /**
     * Adds a new booking to the event.
     *
     * @param bookings The booking to be added.
     * @return `true` if the booking is added successfully, `false` otherwise.
     */
    fun addBooking(bookings: Booking): Boolean {
        bookings.bookingId = getBookingId()
        return booking.add(bookings)
    }

    /**
     * Retrieves the number of bookings associated with the event.
     *
     * @return The number of bookings.
     */
    fun numberOfBookings() = booking.size

    /**
     * Finds and returns a booking with the specified ID.
     *
     * @param id The ID of the booking to be found.
     * @return The found booking or `null` if not found.
     */
    fun findOne(id: Int): Booking? {
        return booking.find { booking -> booking.bookingId == id }
    }

    /**
     * Updates the details of a booking associated with the event.
     *
     * @param id The ID of the booking to be updated.
     * @param newBooking The updated booking details.
     * @return `true` if the booking is updated successfully, `false` otherwise.
     */
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

    /**
     * Deletes a booking associated with the event.
     *
     * @param id The ID of the booking to be deleted.
     * @return `true` if the booking is deleted successfully, `false` otherwise.
     */
    fun delete(id: Int): Boolean {
        return booking.removeIf { booking -> booking.bookingId == id }
    }

    /**
     * Checks if all bookings associated with the event have complete payment status.
     *
     * @return `true` if all bookings are paid, `false` otherwise.
     */
    fun checkPaymentStatus(): Boolean {
        if (booking.isNotEmpty()) {
            for (bookings in booking) {
                if (!bookings.isPaymentComplete) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Formats and returns a string representation of the bookings associated with the event.
     *
     * @return A formatted string representing the bookings.
     */
    fun listBooking() =
        if (booking.isEmpty()) {
            "\t No Bookings Added"
        } else {
            Utilities.formatSetString(booking)
        }

    /**
     * Returns a string representation of the event.
     *
     * @return A formatted string containing event details and associated bookings.
     */
    override fun toString(): String {
        return "$eventId: $eventTitle, Category($eventCategory), Description ($eventDuration), Age Rating ($ageRating), Ticket Price ($ticketPrice), Duration ($eventDuration),\n ${listBooking()}"
    }
}
