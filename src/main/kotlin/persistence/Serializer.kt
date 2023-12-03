package persistence

/**
 * The `Serializer` interface defines the contract for classes that provide
 * serialization and deserialization functionality for data storage and retrieval.
 */
interface Serializer {
    /**
     * Writes an object to a data source.
     *
     * @param obj The object to be serialized and written to the data source.
     * @throws Exception if an error occurs during the write operation.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads an object from a data source and deserializes it.
     *
     * @return The deserialized object, or `null` if no data is found.
     * @throws Exception if an error occurs during the read operation.
     */
    @Throws(Exception::class)
    fun read(): Any?
}