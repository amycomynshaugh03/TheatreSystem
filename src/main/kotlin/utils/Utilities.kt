package utils

import models.Event
import models.Booking

object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(eventToFormat: List<Event>): String =
        eventToFormat
            .joinToString(separator = "\n") { event ->  "$event" }

    @JvmStatic
    fun formatSearchString(eventsToFormat: List<Event>): String =
        eventsToFormat
            .joinToString(separator = "\n") { event ->
                eventsToFormat.indexOf(event).toString() + ": " + event.toString()
            }

    @JvmStatic
    fun formatSetString(bookingToFormat: Set<Booking>): String =
        bookingToFormat
            .joinToString(separator = "\n") { booking ->  "\t$booking" }

}