package controllers
import models.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.JSONSerializer
import persistence.XMLSerializer
import persistence.YAMLSerializer
import java.io.File
import java.time.LocalDate
import kotlin.test.assertEquals

class TheatreAPITest {

    private var event1: Event? = null
    private var event2: Event? = null
    private var event3: Event? = null
    private var event4: Event? = null
    private var populatedEvents: TheatreAPI? = TheatreAPI(YAMLSerializer(File("events.yaml")))
    private var emptyEvents: TheatreAPI? = TheatreAPI(YAMLSerializer(File("events.yaml")))

@BeforeEach
fun setup() {
    event1 = Event(0,"Variations", "Comedy", "Breakfast & family drama", 13, 10, 60)
    event2 = Event(1, "Shrek the Musical", "Comedy", "shrek in musical form", 18, 50, 120)
    event3 = Event(2, "Maltida", "Enlighting", "Orphange Girl being adopted by a rich family", 10, 25, 120)
    event4 = Event(3, "Talent Show", "Comedy", "People show their talents to the world", 10,30,60)

    populatedEvents!!.add(event1!!)
    populatedEvents!!.add(event2!!)
    populatedEvents!!.add(event3!!)
    populatedEvents!!.add(event4!!)
}
    @AfterEach
    fun tearDown() {
        event1 = null
        event2 = null
        event3 = null
        event4 = null
        populatedEvents= null
        emptyEvents = null
    }

    @Nested
    inner class AddEvents {
        @Test
        fun `adding a Event to a populated list adds to ArrayList`() {
            val newEvent = Event(0, "Sisters Act", "Comedy", "A woman gone into hiding with the sisters and joins the choir", 15, 40, 120)
            assertEquals(4, populatedEvents!!.numberOfEvents())
            assertTrue(populatedEvents!!.add(newEvent))
            assertEquals(5, populatedEvents!!.numberOfEvents())
            assertEquals(newEvent, populatedEvents!!.findEvent(populatedEvents!!.numberOfEvents() - 1))
        }

        @Test
        fun `adding a Event to an empty list adds to ArrayList`() {
            val newEvent = Event(0, "Sisters Act", "Comedy", "A woman gone into hiding with the sisters and joins the choir", 15, 40, 120)
            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.add(newEvent))
            assertEquals(1, emptyEvents!!.numberOfEvents())
            assertEquals(newEvent, emptyEvents!!.findEvent(emptyEvents!!.numberOfEvents() - 1))
        }
    }

    @Nested
    inner class ListNotes {

        @Test
        fun `listAllEvents returns No Events Stored message when ArrayList is empty`() {
            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.listAllEvents().lowercase().contains("no notes"))
        }

        @Test
        fun `listAllEvents returns Events when ArrayList has notes stored`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            val eventsString = populatedEvents!!.listAllEvents().lowercase()
            assertTrue(eventsString.contains("Variations", ignoreCase = true))
            assertTrue(eventsString.contains("Shrek the Musical", ignoreCase = true))
            assertTrue(eventsString.contains("Maltida", ignoreCase = true))
            assertTrue(eventsString.contains("Talent Show", ignoreCase = true))
        }
    }

    @Nested
    inner class DeleteEvents {
        @Test
        fun `deleting a Event that does not exist, returns null`() {
            assertFalse(emptyEvents!!.deleteEvent(0))
            assertFalse(populatedEvents!!.deleteEvent(-1))
            assertFalse(populatedEvents!!.deleteEvent(5))
        }

        @Test
        fun `deleting a event that exists delete and returns deleted object`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            assertTrue(populatedEvents!!.deleteEvent(3))
            assertEquals(3, populatedEvents!!.numberOfEvents())
            assertTrue(populatedEvents!!.deleteEvent(0))
            assertEquals(2, populatedEvents!!.numberOfEvents())
        }
    }

    @Nested
    inner class UpdateEvents {
        @Test
        fun `updating a event that does not exist returns false`() {
            assertFalse(
                populatedEvents!!.update(
                    4,
                    Event(0, "Talent Show", "Comedy", "People show their talents to the world", 10, 30, 60)
                )
            )
            assertFalse(
                populatedEvents!!.update(
                    -1,
                    Event(0, "Talent Show", "Comedy", "People show their talents to the world", 10, 30, 60)
                )
            )
            assertFalse(emptyEvents!!.update(0, Event(0, "Talent Show", "Comedy", "People show their talents to the world", 10, 30, 60)))
        }

        @Test
        fun `updating a event that exists returns true and updates`() {
           assertEquals(event4, populatedEvents!!.findEvent(3))
            assertEquals("Talent Show", populatedEvents!!.findEvent(3)!!.eventTitle)
            assertEquals("Comedy", populatedEvents!!.findEvent(3)!!.eventCategory)
            assertEquals("People show their talents to the world", populatedEvents!!.findEvent(3)!!.eventDescription)
            assertEquals(10, populatedEvents!!.findEvent(3)!!.ageRating)
            assertEquals(30, populatedEvents!!.findEvent(3)!!.ticketPrice)
            assertEquals(60, populatedEvents!!.findEvent(3)!!.eventDuration)

            assertTrue(populatedEvents!!.update(3, Event(3, "Updating Event","Hilarious", "Showcasing different talents to four judges, who will win?", 12, 35, 90)))
            assertEquals("Updating Event", populatedEvents!!.findEvent(3)!!.eventTitle)
            assertEquals("Hilarious", populatedEvents!!.findEvent(3)!!.eventCategory)
            assertEquals("Showcasing different talents to four judges, who will win?", populatedEvents!!.findEvent(3)!!.eventDescription)
            assertEquals(12, populatedEvents!!.findEvent(3)!!.ageRating)
            assertEquals(35, populatedEvents!!.findEvent(3)!!.ticketPrice)
            assertEquals(90, populatedEvents!!.findEvent(3)!!.eventDuration)
        }
    }
    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading an empty collection in YAML doesn't crash app`() {
            val storingEvents = TheatreAPI(YAMLSerializer(File("events.yaml")))
            storingEvents.store()

            val loadedEvents = TheatreAPI(YAMLSerializer(File("events.yaml")))
            loadedEvents.load()

            assertEquals(0, storingEvents.numberOfEvents())
            assertEquals(0, loadedEvents.numberOfEvents())
            assertEquals(storingEvents.numberOfEvents(), loadedEvents.numberOfEvents())
        }

        @Test
        fun `saving and loading a loaded collection in YAML doesn't lose data`() {
            val storingEvents = TheatreAPI(YAMLSerializer(File("events.yaml")))
            storingEvents.add(event1!!)
            storingEvents.add(event2!!)
            storingEvents.add(event3!!)
            storingEvents.store()

            val loadedEvents = TheatreAPI(YAMLSerializer(File("events.yaml")))
            loadedEvents.load()

            assertEquals(3, storingEvents.numberOfEvents())
            assertEquals(3, loadedEvents.numberOfEvents())
            assertEquals(storingEvents.numberOfEvents(), loadedEvents.numberOfEvents())
            assertEquals(storingEvents.findEvent(0), loadedEvents.findEvent(0))
            assertEquals(storingEvents.findEvent(1), loadedEvents.findEvent(1))
            assertEquals(storingEvents.findEvent(2), loadedEvents.findEvent(2))
        }
    }

    @Nested
    inner class CountingMethods {
        @Test
        fun numberOfEventsCalculatedCorrectly() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            assertEquals(0, emptyEvents!!.numberOfEvents())
        }

    }

    @Nested
    inner class SearchMethods {
        @Test
        fun `search event by title returns no event when no event with that title exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            val searchResults = populatedEvents!!.searchAllEvents("No results excepted")
            assertTrue(searchResults.isEmpty())

            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.searchAllEvents(" ").isEmpty())
        }

        @Test
        fun `search events by title returns events when events with that title exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())

            var searchResults = populatedEvents!!.searchAllEvents("Talent Show")
            assertTrue(searchResults.contains("Talent Show"))
            assertFalse(searchResults.contains("Variations"))

            searchResults = populatedEvents!!.searchAllEvents("Talent")
            assertTrue(searchResults.contains("Talent Show"))
            assertFalse(searchResults.contains(" Variations"))

            searchResults = populatedEvents!!.searchAllEvents("sHoW")
            assertTrue(searchResults.contains("Talent Show"))
            assertFalse(searchResults.contains("Variations"))
        }

    }





    }
