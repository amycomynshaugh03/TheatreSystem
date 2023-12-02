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
            9 -> markItemStatus()
            10 -> searchEvents()
            15 -> searchBooking()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}


