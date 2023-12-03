package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import models.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * A serializer that handles the conversion of objects to JSON format and vice versa.
 * It uses XStream with a JettisonMappedXmlDriver for JSON processing and is capable
 * of reading from and writing to a file.
 *
 * @property file The file used to read from or write to.
 */
class JSONSerializer(private val file: File) : Serializer {

    /**
     * Reads the JSON file and converts it into an object.
     * The method uses XStream to deserialize JSON content to an object.
     * It allows only instances of Note class for security reasons.
     *
     * @throws Exception if the file reading process fails.
     * @return The object read from the JSON file.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Event::class.java))
        FileReader(file).use { fileReader ->
            xStream.createObjectInputStream(fileReader).use { inputStream ->
                return inputStream.readObject()
            }
        }
    }

    /**
     * Writes an object to the JSON file.
     * This method serializes the given object into JSON format and writes it
     * to the file specified in the 'file' property.
     *
     * @param obj The object to serialize to JSON. Must not be null.
     * @throws Exception if the object is null or the file writing process fails.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        obj ?: throw Exception("Cannot serialize null object")

        val xStream = XStream(JettisonMappedXmlDriver())
        FileWriter(file).use { fileWriter ->
            xStream.createObjectOutputStream(fileWriter).use { outputStream ->
                outputStream.writeObject(obj)
            }
        }
    }
}
