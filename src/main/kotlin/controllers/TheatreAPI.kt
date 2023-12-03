package controllers

import models.Event
import persistence.Serializer
import utils.Utilities.formatListString
import utils.Utilities.formatSearchString
import java.util.ArrayList

/**
 * The TheatreAPI class manages the operations related to events in the Theatre System.
 *
 * @property serializer The serializer used to read and write events data.
 * @property events The list of events stored in the system.
 * @property lastId The last used ID for events.
 */
class TheatreAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var events = ArrayList<Event>()
    private var lastId = 0

    /**
     * Gets the next available ID for events.
     *
     * @return The next available ID.
     */
    private fun getId() = lastId++

    // CRUD FOR EVENT

    /**
     * Adds a new event to the system.
     *
     * @param event The event to be added.
     * @return True if the event is added successfully, false otherwise.
     */
    fun add(event: Event): Boolean {
        event.eventId = getId()
        return events.add(event)
    }

    /**
     * Deletes an event from the system based on its ID.
     *
     * @param id The ID of the event to be deleted.
     * @return True if the event is deleted successfully, false otherwise.
     */
    fun deleteEvent(id: Int) = events.removeIf { note -> note.eventId == id }

    /**
     * Updates the details of an existing event in the system.
     *
     * @param id The ID of the event to be updated.
     * @param event The updated event details.
     * @return True if the event is updated successfully, false otherwise.
     */
    fun update(id: Int, event: Event?): Boolean {
        val foundEvent = findEvent(id)

        // If the event exists, use the event details passed as parameters to update the found event in the ArrayList.
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

    /**
     * Lists all events in the system.
     *
     * @return A formatted string containing the list of events.
     */
    fun listAllEvents() =
        if (events.isEmpty()) "No notes stored"
        else formatListString(events)

    // Counting methods for events

    /**
     * Gets the number of events in the system.
     *
     * @return The number of events.
     */
    fun numberOfEvents() = events.size

    // Searching methods

    /**
     * Finds an event in the system based on its ID.
     *
     * @param eventId The ID of the event to be found.
     * @return The found event or null if not found.
     */
    fun findEvent(eventId: Int) = events.find { events -> events.eventId == eventId }

    // Search Events Functions

    /**
     * Searches for all events containing the specified string in their titles.
     *
     * @param searchString The string to search for in event titles.
     * @return A formatted string containing the search results.
     */
    fun searchAllEvents(searchString: String) =
        formatSearchString(
            events.filter { event -> event.eventTitle.contains(searchString, ignoreCase = true) }
        )

    /**
     * Searches for events based on the specified category.
     *
     * @param searchString The category to search for in event categories.
     * @return A formatted string containing the search results.
     */
    fun searchEventsByCategory(searchString: String) =
        formatSearchString(
            events.filter { event -> event.eventCategory.contains(searchString, ignoreCase = true) }
        )

    /**
     * Searches for events with the specified duration.
     *
     * @param searchValue The duration to search for in event durations.
     * @return A formatted string containing the search results.
     */
    fun searchEventsByDuration(searchValue: Int): String =
        formatSearchString(
            events.filter { event -> event.eventDuration == searchValue }
        )

    /**
     * Searches for events with the specified ticket price.
     *
     * @param searchValue The ticket price to search for in event ticket prices.
     * @return A formatted string containing the search results.
     */
    fun searchEventsByTicketPrice(searchValue: Int): String =
        formatSearchString(
            events.filter { event -> event.ticketPrice == searchValue }
        )

    /**
     * Searches for bookings with the specified customer name.
     *
     * @param searchString The customer name to search for in booking details.
     * @return A formatted string containing the search results.
     */
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

    /**
     * Searches for bookings with the specified date.
     *
     * @param searchString The date to search for in booking details.
     * @return A formatted string containing the search results.
     */
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

    /**
     * Loads events from a file using the specified serializer.
     *
     * @throws Exception if an error occurs during the loading process.
     */
    @Throws(Exception::class)
    fun load() {
        events = serializer.read() as ArrayList<Event>
    }

    /**
     * Stores events to a file using the specified serializer.
     *
     * @throws Exception if an error occurs during the storing process.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(events)
    }
}