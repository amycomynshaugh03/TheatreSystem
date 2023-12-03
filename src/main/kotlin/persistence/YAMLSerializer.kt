package persistence

import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * A serializer implementation for YAML format, providing read and write operations.
 *
 * @property file The [File] representing the YAML file to be serialized.
 */
class YAMLSerializer(private val file: File) : Serializer {

    /**
     * Reads the content from the YAML file and deserializes it into an object.
     *
     * @return The deserialized object read from the YAML file.
     * @throws Exception if there is an error during the reading or deserialization process.
     */
    @Throws(Exception::class)
    override fun read(): Any? {
        val yaml = Yaml()
        val reader = FileReader(file)
        val obj = yaml.load(reader) as Any
        reader.close()
        return obj
    }

    /**
     * Serializes the provided object and writes it to the YAML file.
     *
     * @param obj The object to be serialized and written to the YAML file.
     * @throws Exception if there is an error during the serialization or writing process.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val yaml = Yaml()
        val outputStream = FileWriter(file)
        yaml.dump(obj, outputStream)
        outputStream.close()
    }
}
