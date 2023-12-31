import controllers.TheatreAPI
import models.Booking
import models.Event
import mu.KotlinLogging
import persistence.*
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import kotlin.system.exitProcess

// private val theatreAPI = TheatreAPI(XMLSerializer(File("events.xml")))
// private val theatreAPI = TheatreAPI(JSONSerializer(File("events.json")))
private val theatreAPI = TheatreAPI(YAMLSerializer(File("events.yaml")))

private val logger = KotlinLogging.logger {}

/**
 * Main function to run the Theatre System application.
 */
fun main(args: Array<String>) {
    logger.info("Welcome to Waterford's Theatre System")
    runMenu()
}

/**
 * Function to continuously run the main menu of the Theatre System application.
 */
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

/**
 * Function to display the main menu options and receive user input.
 */
fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |           Waterford's Theatre System              |
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

// Event CRUD

/**
 * Function to add a new event to the system.
 */
fun addEvent() {
    val eventTitle = readNextLine("Enter a Title for the Event: ")
    val eventCategory = readNextLine("Enter a Category for the Event: ")
    val eventDescription = readNextLine("Enter Description of the Event: ")
    val ageRating = readNextInt("Enter Age Rating for Event: ")
    val ticketPrice = readNextInt("Enter Price for a ticket for the Event: ")
    val eventDuration = readNextInt("Enter Duration of the Event: ")

    val isAdded = theatreAPI.add(Event(eventTitle = eventTitle, eventCategory = eventCategory, eventDescription = eventDescription, ageRating = ageRating, ticketPrice = ticketPrice, eventDuration = eventDuration))

    if (isAdded) {
        println("Added Successfully")
    } else {
        logger.info("Add Failed")
    }
}

/**
 * Function to list all events in the system.
 */
fun listEvent() = println(theatreAPI.listAllEvents())

/**
 * Function to update details of an existing event.
 */
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
                logger.info("Update Failed")
            }
        } else {
            logger.info("There are no notes for this index number")
        }
    }
}

/**
 * Function to delete an existing event.
 */
fun deleteEvent() {
    listEvent()
    if (theatreAPI.numberOfEvents() > 0) {
        val id = readNextInt("Enter the id of the note to delete: ")
        val noteToDelete = theatreAPI.deleteEvent(id)
        if (noteToDelete) {
            println("Delete Successful!")
        } else {
            logger.info("Delete NOT Successful")
        }
    }
}

// Booking CRUD

/**
 * Function to add a booking to an existing event.
 */
private fun addBookingToEvent() {
    val event: Event? = askUserToChooseEvent()
    if (event != null) {
        val bookingDate = readNextLine("Enter Booking Date: ")
        val bookingTime = readNextLine("Enter Booking Time: ")
        val customerName = readNextLine("Enter Customer Name: ")
        val customerPhone = readNextInt("Enter Customer Phone Number: ")
        val paymentMethod = readNextLine("Enter a Payment Method: ")
        val booking = Booking(bookingDate = bookingDate, bookingTime = bookingTime, customerName = customerName, customerPhone = customerPhone, paymentMethod = paymentMethod)
        if (event.addBooking(booking)) {
            println("Add Successful!")
        } else {
            logger.info("Add NOT Successful")
        }
    }
}

/**
 * Function to update details of an existing booking in an event.
 */
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
            if (event.update(
                    booking.bookingId,
                    Booking(
                            bookingDate = newBookingDate,
                            bookingTime = newBookingTime,
                            customerName = newCustomerName,
                            customerPhone = newCustomerPhone,
                            paymentMethod = newPaymentMethod
                        )
                )
            ) {
                println("Booking contents updated")
            } else {
                logger.info("Booking contents NOT updated")
            }
        } else {
            logger.info("Invalid Booking Id")
        }
    }
}

/**
 * Function to delete an existing booking from an event.
 */
fun deleteBooking() {
    val event: Event? = askUserToChooseEvent()
    if (event != null) {
        val booking: Booking? = askUserToChooseBooking(event)
        if (booking != null) {
            val isDeleted = event.delete(booking.bookingId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                logger.info("Delete NOT Successful")
            }
        }
    }
}

/**
 * Function to mark the payment status of a booking.
 */
fun markPaymentStatus() {
    val event: Event? = askUserToChooseEvent()
    if (event != null) {
        val booking: Booking? = askUserToChooseBooking(event)
        if (booking != null) {
            var changeStatus = 'X'
            if (booking.isPaymentComplete) {
                changeStatus = readNextChar("The Booking is currently complete...do you want to mark it as paid?")
                if ((changeStatus == 'Y') || (changeStatus == 'y')) {
                    booking.isPaymentComplete = false
                }
            } else {
                changeStatus = readNextChar("The Booking is currently unpaid...do you want to mark it as paid?")
                if ((changeStatus == 'Y') || (changeStatus == 'y')) {
                    booking.isPaymentComplete = true
                }
            }
        }
    }
}

// Helper Functions

/**
 * Function to prompt the user to choose an event from the list.
 */
private fun askUserToChooseEvent(): Event? {
    listEvent()
    if (theatreAPI.numberOfEvents() > 0) {
        val event = theatreAPI.findEvent(readNextInt("\nEnter the id of the note: "))
        if (event != null) {
            return event
        } else {
            logger.info("Event id is not valid")
        }
    }
    return null
}

/**
 * Function to prompt the user to choose a booking from an event.
 */
private fun askUserToChooseBooking(event: Event): Booking? {
    if (event.numberOfBookings() > 0) {
        print(event.listBooking())
        return event.findOne(readNextInt("\nEnter the id of the Booking: "))
    } else {
        println("No Booking for the chosen note")
        return null
    }
}

// Event - Search Functions

/**
 * Function to initiate a search for events based on user criteria.
 */
fun searchEvents() {
    if (theatreAPI.numberOfEvents() > 0) {
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
        logger.info("Option Invalid - No Events Stored")
    }
}

/**
 * Function to search for all events based on a provided description.
 */
fun searchAllEvents() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = theatreAPI.searchAllEvents(searchTitle)
    if (searchResults.isEmpty()) {
        logger.info("No notes found")
    } else {
        println(searchResults)
    }
}

/**
 * Function to search for events based on a provided category.
 */
fun searchEventsByCategory() {
    val searchTitle = readNextLine("Enter the category to search by: ")
    val searchResults = theatreAPI.searchEventsByCategory(searchTitle)
    if (searchResults.isEmpty()) {
        logger.info("No notes found")
    } else {
        println(searchResults)
    }
}

/**
 * Function to search for events based on a provided duration.
 */
fun searchEventsByDuration() {
    val searchTitle = readNextInt("Enter the duration to search by: ")
    val searchResults = theatreAPI.searchEventsByDuration(searchTitle)
    if (searchResults.isEmpty()) {
        logger.info("No notes found")
    } else {
        println(searchResults)
    }
}

/**
 * Function to search for events based on a provided ticket price.
 */
fun searchEventsByTicketPrice() {
    val searchTitle = readNextInt("Enter the ticket price to search by: ")
    val searchResults = theatreAPI.searchEventsByTicketPrice(searchTitle)
    if (searchResults.isEmpty()) {
        logger.info("No notes found")
    } else {
        println(searchResults)
    }
}

// Booking Search Function

/**
 * Function to initiate a search for bookings based on user criteria.
 */
fun searchBooking() {
    if (theatreAPI.numberOfEvents() > 0) {
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
        logger.info("Option Invalid - No Bookings Stored")
    }
}

/**
 * Function to search for bookings based on a provided customer name.
 */
fun searchBookingsByCustomerName() {
    val searchBookings = readNextLine("Enter customer name to search by: ")
    val searchResults = theatreAPI.searchBookingByCustomerName(searchBookings)
    if (searchResults.isEmpty()) {
        logger.info("No bookings found")
    } else {
        println(searchResults)
    }
}

/**
 * Function to search for bookings based on a provided date.
 */
fun searchBookingsByDate() {
    val searchBookings = readNextLine("Enter the date to search by: ")
    val searchResults = theatreAPI.searchBookingByDate(searchBookings)
    if (searchResults.isEmpty()) {
        logger.info("No bookings found")
    } else {
        println(searchResults)
    }
}

/**
 * Function to exit the application.
 */
fun exitApp() {
    println("Exiting...bye :) ")
    exitProcess(0)
}

/**
 * Function to save the state of the Theatre System to a file.
 */
fun save() {
    try {
        theatreAPI.store()
        println("Storing successful")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Function to load the state of the Theatre System from a file.
 */
fun load() {
    try {
        theatreAPI.load()
        println("Load successful")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
