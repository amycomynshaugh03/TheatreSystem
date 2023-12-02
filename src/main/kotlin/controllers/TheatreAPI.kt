package controllers

import models.Booking
import models.Event
import utils.Utilities.formatListString
import java.util.ArrayList
class TheatreAPI() {
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























}