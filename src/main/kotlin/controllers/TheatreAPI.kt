package controllers

import models.Booking
import models.Event
import persistence.Serializer
import utils.Utilities.formatListString
import utils.Utilities.formatSearchString
import java.util.ArrayList
class TheatreAPI(serializerType : Serializer) {
    private var serializer: Serializer = serializerType

    private var events = ArrayList<Event>()
    private var lastId = 0
    private fun getId() = lastId++

    //CRUD FOR EVENT
    fun add(event: Event): Boolean {
        event.eventId = getId()
        return events.add(event)
    }
    fun delete(id: Int) = events.removeIf { note -> note.eventId == id }

    fun update(id: Int, event: Event?): Boolean {
        val foundEvent = findEvent(id)

        // if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundEvent != null) && (event != null)) {
            foundEvent.eventTitle = event.eventTitle
            foundEvent.eventCategory = event.eventCategory
            foundEvent.eventDescription = event.eventDescription
            foundEvent.ageRating = event.ageRating
            foundEvent.ticketPrice = event.ticketPrice
            foundEvent.eventDuration = event.eventDuration
            return true
        }
        return false
    }
    fun listAllEvents() =
        if (events.isEmpty()) "No notes stored"
        else formatListString(events)


    //counting methods for event
    fun numberOfEvents() = events.size

    //searching methods
    fun findEvent(eventId: Int) = events.find{ events -> events.eventId == eventId}


//Search Events Functions
fun searchAllEvents(searchString: String) =
    formatSearchString(
        events.filter { event -> event.eventTitle.contains(searchString, ignoreCase = true)}
    )

    fun searchEventsByCategory(searchString: String) =
        formatSearchString(
            events.filter { event -> event.eventCategory.contains(searchString, ignoreCase = true)}
        )

    fun searchEventsByDuration(searchValue: Int): String =
        formatSearchString(
            events.filter { event -> event.eventDuration == searchValue}
        )

    fun searchEventsByTicketPrice(searchValue: Int): String =
        formatSearchString(
            events.filter { event -> event.ticketPrice== searchValue}
        )

    fun searchBookingByCustomerName(searchString: String): String {
        return if (numberOfEvents() == 0) "No events stored"
        else {
            var listOfEvent = ""
            for (event in events) {
                for (booking in event.booking) {
                    if (booking.customerName.contains(searchString, ignoreCase = true)) {
                        listOfEvent += "${event.eventId}: ${event.eventTitle} \n\t${booking}\n"
                    }
                }
            }
            if (listOfEvent == "") "No bookings found for: $searchString"
            else listOfEvent
        }
    }

    fun searchBookingByDate(searchString: String): String {
        return if (numberOfEvents() == 0) "No events stored"
        else {
            var listOfEvent = ""
            for (event in events) {
                for (booking in event.booking) {
                    if (booking.bookingDate.contains(searchString, ignoreCase = true)) {
                        listOfEvent += "${event.eventId}: ${event.eventTitle} \n\t${booking}\n"
                    }
                }
            }
            if (listOfEvent == "") "No bookings found for: $searchString"
            else listOfEvent
        }
    }

    @Throws(Exception::class)
    fun load() {
        events = serializer.read() as ArrayList<Event>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(events)
    }


















}