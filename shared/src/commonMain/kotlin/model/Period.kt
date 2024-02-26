package model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import now

@Serializable
data class Period(
    val id: String,
    val name: String,
    val createDate: LocalDateTime = now(),
    val updateDate: LocalDateTime? = null,
)
