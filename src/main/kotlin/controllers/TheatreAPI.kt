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
        return event.add(event)
    }

    fun delete(id: Int) = events.removeIf { note -> note.eventId == id }


























}