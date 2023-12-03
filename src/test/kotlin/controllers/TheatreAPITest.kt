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
    event2 = Event(0, "Shrek the Musical", "Comedy", "shrek in musical form", 18, 50, 120)
    event3 = Event(0, "Maltida", "Enlighting", "Orphange Girl being adopted by a rich family", 10, 25, 120)
    event4 = Event(0, "Talent Show", "Comedy", "People show their talents to the world", 10,30,60)

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












}
