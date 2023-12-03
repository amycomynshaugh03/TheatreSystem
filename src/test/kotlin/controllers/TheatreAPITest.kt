package controllers

import models.Event
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.YAMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Unit tests for the TheatreAPI class.
 */
class TheatreAPITest {

    private var event1: Event? = null
    private var event2: Event? = null
    private var event3: Event? = null
    private var event4: Event? = null
    private var populatedEvents: TheatreAPI? = TheatreAPI(YAMLSerializer(File("events.yaml")))
    private var emptyEvents: TheatreAPI? = TheatreAPI(YAMLSerializer(File("events.yaml")))

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    fun setup() {
        event1 = Event(0, "Variations", "Comedy", "Breakfast & family drama", 13, 10, 60)
        event2 = Event(1, "Shrek the Musical", "Comedy", "shrek in musical form", 18, 50, 120)
        event3 = Event(2, "Matilda", "Enlightening", "Orphanage Girl being adopted by a rich family", 10, 25, 120)
        event4 = Event(3, "Talent Show", "Comedy", "People show their talents to the world", 10, 30, 60)

        populatedEvents!!.add(event1!!)
        populatedEvents!!.add(event2!!)
        populatedEvents!!.add(event3!!)
        populatedEvents!!.add(event4!!)
    }

    /**
     * Tear down the test environment after each test case.
     */
    @AfterEach
    fun tearDown() {
        event1 = null
        event2 = null
        event3 = null
        event4 = null
        populatedEvents = null
        emptyEvents = null
    }

    /**
     * Nested tests for adding events to the TheatreAPI.
     */
    @Nested
    inner class AddEvents {
        /**
         * Test adding an event to a populated list.
         */
        @Test
        fun `adding a Event to a populated list adds to ArrayList`() {
            val newEvent = Event(0, "Sisters Act", "Comedy", "A woman gone into hiding with the sisters and joins the choir", 15, 40, 120)
            assertEquals(4, populatedEvents!!.numberOfEvents())
            assertTrue(populatedEvents!!.add(newEvent))
            assertEquals(5, populatedEvents!!.numberOfEvents())
            assertEquals(newEvent, populatedEvents!!.findEvent(populatedEvents!!.numberOfEvents() - 1))
        }

        /**
         * Test adding an event to an empty list.
         */
        @Test
        fun `adding a Event to an empty list adds to ArrayList`() {
            val newEvent = Event(0, "Sisters Act", "Comedy", "A woman gone into hiding with the sisters and joins the choir", 15, 40, 120)
            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.add(newEvent))
            assertEquals(1, emptyEvents!!.numberOfEvents())
            assertEquals(newEvent, emptyEvents!!.findEvent(emptyEvents!!.numberOfEvents() - 1))
        }
    }

    /**
     * Nested tests for listing events in the TheatreAPI.
     */
    @Nested
    inner class ListEvents {

        /**
         * Test listing all events when ArrayList is empty.
         */
        @Test
        fun `listAllEvents returns No Events Stored message when ArrayList is empty`() {
            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.listAllEvents().lowercase().contains("no notes"))
        }

        /**
         * Test listing all events when ArrayList has notes stored.
         */
        @Test
        fun `listAllEvents returns Events when ArrayList has notes stored`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            val eventsString = populatedEvents!!.listAllEvents().lowercase()
            assertTrue(eventsString.contains("Variations", ignoreCase = true))
            assertTrue(eventsString.contains("Shrek the Musical", ignoreCase = true))
            assertTrue(eventsString.contains("Matilda", ignoreCase = true))
            assertTrue(eventsString.contains("Talent Show", ignoreCase = true))
        }
    }

    /**
     * Nested tests for deleting events from the TheatreAPI.
     */
    @Nested
    inner class DeleteEvents {
        /**
         * Test deleting an event that does not exist.
         */
        @Test
        fun `deleting a Event that does not exist, returns null`() {
            assertFalse(emptyEvents!!.deleteEvent(0))
            assertFalse(populatedEvents!!.deleteEvent(-1))
            assertFalse(populatedEvents!!.deleteEvent(5))
        }

        /**
         * Test deleting an event that exists.
         */
        @Test
        fun `deleting a event that exists delete and returns deleted object`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            assertTrue(populatedEvents!!.deleteEvent(3))
            assertEquals(3, populatedEvents!!.numberOfEvents())
            assertTrue(populatedEvents!!.deleteEvent(0))
            assertEquals(2, populatedEvents!!.numberOfEvents())
        }
    }

    /**
     * Nested tests for updating events in the TheatreAPI.
     */
    @Nested
    inner class UpdateEvents {
        /**
         * Test updating an event that does not exist.
         */
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

        /**
         * Test updating an event that exists.
         */
        @Test
        fun `updating a event that exists returns true and updates`() {
            assertEquals(event4, populatedEvents!!.findEvent(3))
            assertEquals("Talent Show", populatedEvents!!.findEvent(3)!!.eventTitle)
            assertEquals("Comedy", populatedEvents!!.findEvent(3)!!.eventCategory)
            assertEquals("People show their talents to the world", populatedEvents!!.findEvent(3)!!.eventDescription)
            assertEquals(10, populatedEvents!!.findEvent(3)!!.ageRating)
            assertEquals(30, populatedEvents!!.findEvent(3)!!.ticketPrice)
            assertEquals(60, populatedEvents!!.findEvent(3)!!.eventDuration)

            assertTrue(populatedEvents!!.update(3, Event(3, "Updating Event", "Hilarious", "Showcasing different talents to four judges, who will win?", 12, 35, 90)))
            assertEquals("Updating Event", populatedEvents!!.findEvent(3)!!.eventTitle)
            assertEquals("Hilarious", populatedEvents!!.findEvent(3)!!.eventCategory)
            assertEquals("Showcasing different talents to four judges, who will win?", populatedEvents!!.findEvent(3)!!.eventDescription)
            assertEquals(12, populatedEvents!!.findEvent(3)!!.ageRating)
            assertEquals(35, populatedEvents!!.findEvent(3)!!.ticketPrice)
            assertEquals(90, populatedEvents!!.findEvent(3)!!.eventDuration)
        }
    }

    /**
     * Nested tests for persistence operations in the TheatreAPI.
     */
    @Nested
    inner class PersistenceTests {
        /**
         * Test saving and loading an empty collection in YAML without crashing the app.
         */
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

        /**
         * Test saving and loading a loaded collection in YAML without losing data.
         */
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

    /**
     * Nested tests for counting methods in the TheatreAPI.
     */
    @Nested
    inner class CountingMethods {
        /**
         * Test that the number of events is calculated correctly.
         */
        @Test
        fun numberOfEventsCalculatedCorrectly() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            assertEquals(0, emptyEvents!!.numberOfEvents())
        }
    }

    /**
     * Nested tests for searching methods in the TheatreAPI.
     */
    @Nested
    inner class SearchMethods {
        /**
         * Test searching for an event by title when no event with that title exists.
         */
        @Test
        fun `search event by title returns no event when no event with that title exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            val searchResults = populatedEvents!!.searchAllEvents("No results expected")
            assertTrue(searchResults.isEmpty())

            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.searchAllEvents(" ").isEmpty())
        }

        /**
         * Test searching for events by title when events with that title exist.
         */
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

        /**
         * Test searching for an event by category when no event with that category exists.
         */
        @Test
        fun `search event by category returns no event when no event with that category exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            val searchResults = populatedEvents!!.searchEventsByCategory("No results expected")
            assertTrue(searchResults.isEmpty())

            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.searchEventsByCategory(" ").isEmpty())
        }

        /**
         * Test searching for events by category when events with that category exist.
         */
        @Test
        fun `search events by category returns events when events with that category exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())

            var searchResults = populatedEvents!!.searchEventsByCategory("Comedy")
            assertTrue(searchResults.contains("Shrek the Musical"))
            assertTrue(searchResults.contains("Variations"))
            assertTrue(searchResults.contains("Talent Show"))
            assertFalse(searchResults.contains("Matilda"))

            searchResults = populatedEvents!!.searchEventsByCategory("cOmEdY")
            assertTrue(searchResults.contains("Shrek the Musical"))
            assertTrue(searchResults.contains("Variations"))
            assertTrue(searchResults.contains("Talent Show"))
            assertFalse(searchResults.contains("Matilda"))
        }

        /**
         * Test searching for an event by duration when no event with that duration exists.
         */
        @Test
        fun `search event by duration returns no event when no event with that duration exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            val searchResults = populatedEvents!!.searchEventsByDuration(0)
            assertTrue(searchResults.isEmpty())

            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.searchEventsByDuration(0).isEmpty())
        }

        /**
         * Test searching for events by duration when events with that duration exist.
         */
        @Test
        fun `search events by duration returns events when events with that duration exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())

            var searchResults = populatedEvents!!.searchEventsByDuration(60)
            assertTrue(searchResults.contains("Variations"))
            assertTrue(searchResults.contains("Talent Show"))
            assertFalse(searchResults.contains("Matilda"))
            assertFalse(searchResults.contains("Shrek the Musical"))
        }

        /**
         * Test searching for an event by ticket price when no event with that ticket price exists.
         */
        @Test
        fun `search event by ticket price returns no event when no event with that ticket price exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())
            val searchResults = populatedEvents!!.searchEventsByTicketPrice(0)
            assertTrue(searchResults.isEmpty())

            assertEquals(0, emptyEvents!!.numberOfEvents())
            assertTrue(emptyEvents!!.searchEventsByTicketPrice(0).isEmpty())
        }

        /**
         * Test searching for events by ticket price when events with that ticket price exist.
         */
        @Test
        fun `search events by ticket price returns events when events with that ticket price exist`() {
            assertEquals(4, populatedEvents!!.numberOfEvents())

            var searchResults = populatedEvents!!.searchEventsByTicketPrice(25)
            assertTrue(searchResults.contains("Matilda"))
            assertFalse(searchResults.contains("Shrek the Musical"))
        }
    }
}
