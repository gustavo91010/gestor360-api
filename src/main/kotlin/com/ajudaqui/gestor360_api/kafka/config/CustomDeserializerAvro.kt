package com.ajudaqui.gestor360_api.kafka.config

import com.ajudaqui.gestor360_api.kafka.entity.BudgetItems
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException
import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.avro.message.BinaryMessageDecoder
import org.apache.avro.reflect.ReflectData
import org.apache.kafka.common.header.Headers
import org.apache.kafka.common.serialization.Deserializer
import java.io.ByteArrayInputStream
import java.io.IOException


class CustomDeserializerAvro<T> : Deserializer<T> {
    override fun configure(configs: Map<String?, *>?, isKey: Boolean) {
        super.configure(configs, isKey)
    }

    override fun deserialize(s: String?, bytes: ByteArray?): T? {
        val genericData: GenericData = ReflectData()
        try {
            ByteArrayInputStream(bytes).use { inputStream ->
                val binaryMessageDecoder: BinaryMessageDecoder<T?> = BinaryMessageDecoder(genericData, getSchema())
                return binaryMessageDecoder.decode(inputStream, null)
            }
        } catch (e: RestClientException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun getSchema(): Schema {

        return BudgetItems().schema
    }

    override fun deserialize(topic: String?, headers: Headers?, data: ByteArray?): T {
        return super.deserialize(topic, headers, data)
    }

    override fun close() {
        super.close()
    }
}