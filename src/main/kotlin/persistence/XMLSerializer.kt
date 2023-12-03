package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Event
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * The `XMLSerializer` class is responsible for reading and writing data in XML format using XStream.
 *
 * @param file The `File` where data will be read from and written to in XML format.
 */
class XMLSerializer(private val file: File) : Serializer {
    /**
     * Reads data from an XML file and deserializes it into an object.
     *
     * @return The deserialized data as an `Any` object.
     * @throws Exception if an error occurs during the read operation.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Event::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Writes data to an XML file by serializing the provided object.
     *
     * @param obj The object to be serialized and written to the XML file.
     * @throws Exception if an error occurs during the write operation.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
