package com.gitlab.ci.pipelines

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Pipeline(
    val id: Int,
    val iid: Int,
    val project_id: Int,
    val status: Status,
    val source: String,
    val ref: String,
    val sha: String,
    val web_url: String,
    @Serializable(DateSerializer::class) val created_at: LocalDateTime,
    @Serializable(DateSerializer::class) val updated_at: LocalDateTime
)

@Serializable
enum class Status {
    created,
    waiting_for_resource,
    preparing,
    pending,
    running,
    success,
    failed,
    canceled,
    skipped,
    manual,
    scheduled
}

object DateSerializer : KSerializer<LocalDateTime> {
    private val timeFormatter = DateTimeFormatter.ISO_DATE_TIME
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) =
        encoder.encodeString(value.format(timeFormatter))

    override fun deserialize(decoder: Decoder): LocalDateTime =
        LocalDateTime.parse(decoder.decodeString(), timeFormatter)
}
