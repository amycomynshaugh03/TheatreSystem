package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import models.Event
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * The `JSONSerializer` class is responsible for reading and writing data in JSON format using XStream.
 *
 * @param file The `File` where data will be read from and written to.
 */
class JSONSerializer(private val file: File) : Serializer {
    /**
     * Reads data from a JSON file and deserializes it into an object.
     *
     * @return The deserialized data as an `Any` object.
     * @throws Exception if an error occurs during the read operation.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Event::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Writes data to a JSON file by serializing the provided object.
     *
     * @param obj The object to be serialized and written to the JSON file.
     * @throws Exception if an error occurs during the write operation.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(JettisonMappedXmlDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}