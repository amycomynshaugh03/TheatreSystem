package utils

import models.Event
import models.Booking

/**
 * A utility object containing formatting functions for events and bookings.
 */
object Utilities {

    /**
     * Formats a list of events into a string.
     *
     * @param eventToFormat The list of events to be formatted.
     * @return A formatted string representing the list of events.
     */
    @JvmStatic
    fun formatListString(eventToFormat: List<Event>): String =
        eventToFormat.joinToString(separator = "\n") { event -> "$event" }

    /**
     * Formats a list of events for search results into a string.
     *
     * @param eventsToFormat The list of events to be formatted.
     * @return A formatted string representing the list of events with index numbers.
     */
    @JvmStatic
    fun formatSearchString(eventsToFormat: List<Event>): String =
        eventsToFormat.joinToString(separator = "\n") { event ->
            "${eventsToFormat.indexOf(event)}: $event"
        }

    /**
     * Formats a set of bookings into a string.
     *
     * @param bookingToFormat The set of bookings to be formatted.
     * @return A formatted string representing the set of bookings.
     */
    @JvmStatic
    fun formatSetString(bookingToFormat: Set<Booking>): String =
        bookingToFormat.joinToString(separator = "\n") { booking -> "\t$booking" }
}