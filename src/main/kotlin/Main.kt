import controllers.TheatreAPI
import models.Event
import models.Booking
import persistence.JSONSerializer
import persistence.XMLSerializer
import persistence.YAMLSerializer
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import kotlin.system.exitProcess

private val theatreAPI = TheatreAPI()

//private val noteAPI = TheatreAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = TheatreAPI(JSONSerializer(File("notes.json")))
//private val noteAPI = TheatreAPI(YAMLSerializer(File("notes.yaml")))
fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addEvent()
            2 -> listEvent()
            3 -> updateEvent()
            4 -> deleteEvent()
            5 -> addBookingToEvent()
            6 -> updateBookingContentsInEvent()
            7 -> deleteBooking()
            8 -> markPaymentStatus()
            9 -> searchEvents()
            10 -> searchBooking()
            20 -> save()
            21 -> load()
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
         > |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| 
         > |               -BOOKING MENU-                      | 
         > |   5 -> Add Booking to an Event                    |
         > |   6 -> Update Booking contents on an Event        |
         > |   7 -> Delete Booking from an Event               |
         > |   8 -> Mark Booking as Paid or Unpaid             | 
         > |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| 
         > |          -REPORT MENU FOR EVENTS-                 | 
         > |   9 -> Search for all Events                      |
         > |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| 
         > |          -REPORT MENU FOR BOOKINGS-               |                                
         > |   10 -> Search for all Bookings                   |
         > |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         > |    20 -> Save Event                               |
         > |    21 -> Load Event                               |
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
    val event: Event? = askUserToChooseEvent()
    if (event != null ) {
        val bookingDate = readNextLine("Enter Booking Date: ")
        val bookingTime = readNextLine("Enter Booking Time: ")
        val customerName = readNextLine("Enter Customer Name: ")
        val customerPhone = readNextInt("Enter Customer Phone Number: ")
        val paymentMethod = readNextLine("Enter a Payment Method: ")
        val booking = Booking( bookingDate = bookingDate,bookingTime = bookingTime, customerName = customerName, customerPhone = customerPhone, paymentMethod = paymentMethod)
        if (event.addBooking(booking))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updateBookingContentsInEvent() {
    val event: Event? = askUserToChooseEvent()
    if (event != null) {
        val booking: Booking? = askUserToChooseBooking(event)
        if (booking != null) {
            val newBookingDate = readNextLine("Enter a new Booking Date: ")
            val newBookingTime = readNextLine("Enter a new Booking Time: ")
            val newCustomerName = readNextLine("Enter a new Customer Name: ")
            val newCustomerPhone = readNextInt("Enter new Customer Phone Number: ")
            val newPaymentMethod = readNextLine("Enter a new Payment Method: ")
            if (event.update(booking.bookingId, Booking(bookingDate = newBookingDate,bookingTime = newBookingTime,
                    customerName = newCustomerName, customerPhone = newCustomerPhone, paymentMethod = newPaymentMethod ))) {
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
    val event: Event? = askUserToChooseEvent()
    if (event != null) {
        val booking: Booking? = askUserToChooseBooking(event)
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

fun markPaymentStatus() {
    val event: Event? = askUserToChooseEvent()
    if (event != null) {
        val booking: Booking? = askUserToChooseBooking(event)
        if (booking != null) {
            var changeStatus = 'X'
            if (booking.isPaymentComplete) {
                changeStatus = readNextChar("The Booking is currently complete...do you want to mark it as paid?")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                    booking.isPaymentComplete = false
            }
            else {
                changeStatus = readNextChar("The Booking is currently unpaid...do you want to mark it as unpaid?")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                    booking.isPaymentComplete = true
            }
        }
    }
}

//Helper Functions
private fun askUserToChooseEvent(): Event? {
    listEvent()
    if (theatreAPI.numberOfEvents() > 0) {
        val event = theatreAPI.findEvent(readNextInt("\nEnter the id of the note: "))
        if (event != null) {
            return event
        } else {
                println("Event id is not valid")
            }
        }
    return null
}

private fun askUserToChooseBooking(event: Event): Booking? {
    if (event.numberOfBookings() > 0) {
        print(event.listBooking())
        return event.findOne(readNextInt("\nEnter the id of the Booking: "))
    }
    else{
        println ("No Booking for chosen note")
        return null
    }
}

//Event - Search Functions
fun searchEvents() {
    if(theatreAPI.numberOfEvents() > 0) {
        val option = readNextInt(
            """
                  > ----------------------------------------
                  > |   1 -> Search ALL Events             |
                  > |   2 -> Search Events by Category     |
                  > |   3 -> Search Events by Duration     |
                  > |   4 -> Search Events by Ticket Price |
                  > ----------------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> searchAllEvents()
            2 -> searchEventsByCategory()
            3 -> searchEventsByDuration()
            4 -> searchEventsByTicketPrice()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No Events Stored")
    }
}

fun searchAllEvents() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = theatreAPI.searchAllEvents(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

fun searchEventsByCategory(){
    val searchTitle = readNextLine("Enter the category to search by: ")
    val searchResults = theatreAPI.searchEventsByCategory(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

fun searchEventsByDuration()
{
    val searchTitle = readNextInt("Enter the duration to search by: ")
    val searchResults = theatreAPI.searchEventsByDuration(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

fun searchEventsByTicketPrice() {
    val searchTitle = readNextInt("Enter the ticket price to search by: ")
    val searchResults = theatreAPI.searchEventsByTicketPrice(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

//Booking Search Function
fun searchBooking() {
    if(theatreAPI.numberOfEvents() > 0) {
        val option = readNextInt(
            """
                  > -----------------------------------------------
                  > |   1 -> Search Bookings by Customer Name     |
                  > |   2 -> Search Bookings by Date              |
                  > -----------------------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> searchBookingsByCustomerName()
            2 -> searchBookingsByDate()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No Bookings Stored")
    }
}

fun searchBookingsByCustomerName() {
    val searchBookings = readNextLine("Enter customer name to search by: ")
    val searchResults = theatreAPI.searchBookingByCustomerName(searchBookings)
    if (searchResults.isEmpty()) {
        println("No bookings found")
    } else {
        println(searchResults)
    }
}

fun searchBookingsByDate() {
    val searchBookings = readNextLine("Enter the date to search by: ")
    val searchResults = theatreAPI.searchBookingByDate(searchBookings)
    if (searchResults.isEmpty()) {
        println("No bookings found")
    } else {
        println(searchResults)
    }
}

fun exitApp() {
    println("Exiting...bye :) ")
    exitProcess(0)
}

fun save() {
    try {
        theatreAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Loads notes from a file.
 */
fun load() {
    try {
        theatreAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}