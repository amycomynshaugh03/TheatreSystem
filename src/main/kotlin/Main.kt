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



