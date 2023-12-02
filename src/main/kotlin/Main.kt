import controllers.TheatreAPI
import models.Event
import models.Booking
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import kotlin.system.exitProcess

private val theatreAPI = TheatreAPI()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addEvent()
            2 -> listEvent()
            3 -> updateEvent()
            4 -> deleteEvent()
            5 -> archiveEvent()
            6 -> addBookingToEvent()
            7 -> updateBookingContentsInEvent()
            8 -> deleteBooking()
            9 -> markPaymentStatus()
            10 -> searchEvents()
            15 -> searchBooking()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |      Welcome to Waterford's Theatre System        |
         > -----------------------------------------------------  
         > |                -EVENT MENU-                       |
         > |   1 -> Add an Event                               |
         > |   2 -> List Events                                |
         > |   3 -> Update an Event                            |
         > |   4 -> Delete an Event                            |
         > |   5 -> Archive an Event                           |
         > |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| 
         > |               -BOOKING MENU-                      | 
         > |   6 -> Add Booking to an Event                    |
         > |   7 -> Update Booking contents on an Event        |
         > |   8 -> Delete Booking from an Event               |
         > |   9 -> Mark Booking as Paid or Unpaid             | 
         > |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| 
         > |          -REPORT MENU FOR EVENTS-                 | 
         > |   10 -> Search for all Events                     |
         > |   11 -> Search by Event Category                  |
         > |   12 -> Search by Duration                        |
         > |   13 -> Search by Ticket Price                    |
         > |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| 
         > |          -REPORT MENU FOR BOOKINGS-               |                                
         > |   14 -> Search for all Bookings                   |
         > |   15 -> Search by Payment Status                  |
         > |   16 -> Search Booking by Date                    |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//Event CRUD
fun addEvent() {
    val eventTitle = readNextLine("Enter a Title for the Event: ")
    val eventCategory = readNextLine("Enter a Category for the Event: ")
    val eventDescription = readNextLine("Enter Description of the Event: ")
    val ageRating = readNextInt("Enter Age Rating for Event: ")
    val ticketPrice = readNextInt("Enter Price for a ticket for the Event: ")
    val eventDuration = readNextInt("Enter Duration of the Event: ")

    val isAdded = theatreAPI.add(Event( eventTitle = eventTitle, eventCategory = eventCategory, eventDescription = eventDescription, ageRating = ageRating, ticketPrice = ticketPrice, eventDuration = eventDuration))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listEvent() = println(theatreAPI.listAllEvents())

fun updateEvent() {
    listEvent()
    if (theatreAPI.numberOfEvents() > 0) {
        val id = readNextInt("Enter the id of the note to update: ")
        if (theatreAPI.findEvent(id) != null) {
            val eventTitle = readNextLine("Enter a Title for the Event: ")
            val eventCategory = readNextLine("Enter a Category for the Event: ")
            val eventDescription = readNextLine("Enter Description of the Event: ")
            val ageRating = readNextInt("Enter Age Rating for Event: ")
            val ticketPrice = readNextInt("Enter Price for a ticket for the Event: ")
            val eventDuration = readNextInt("Enter Duration of the Event: ")

            if (theatreAPI.update(id, Event(0, eventTitle, eventCategory, eventDescription, ageRating, ticketPrice, eventDuration))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteEvent() {
    listEvent()
    if (theatreAPI.numberOfEvents() > 0) {
        val id = readNextInt("Enter the id of the note to delete: ")
        val noteToDelete = theatreAPI.delete(id)
        if (noteToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

//Booking CRUD
private fun addBookingToEvent() {
    val event: Event? = askUserToChooseActiveEvent()
    if (event != null ) {
        if (event.addBooking(Booking(bookingContents = readNextLine("\t Booking Contents: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updateBookingContentsInEvent() {
    val event: Event? = askUserToChooseActiveEvent()
    if (event != null) {
        val booking: Booking? = askUserToChooseBooking(Event)
        if (booking != null) {
            val newContents = readNextLine("Enter new contents: ")
            if (event.update(booking.bookingId, Booking(bookingContents = newContents))) {
                println("Booking contents updated")
            } else {
                println("Booking contents NOT updated")
            }
        } else {
            println("Invalid Booking Id")
        }
    }
}

fun deleteBooking(){
    val event: Event? = askUserToChooseActiveEvent()
    if (event != null) {
        val event: Event? = askUserToChooseBooking(Event)
        if (booking != null) {
            val isDeleted = event.delete(booking.bookingId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}
