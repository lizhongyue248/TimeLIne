import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

fun Int.toTwoString(): String =
    if (this > 10) {
        "$this"
    } else {
        "0$this"
    }

val pattern = Regex("^\\d+\$")

fun String.isDigit() = matches(pattern)
fun String.isNotDigit() = !isDigit()

fun LocalDateTime.toDateString(): String {
    return "${this.year}-${this.monthNumber.toTwoString()}-${this.dayOfMonth.toTwoString()}"
}

fun LocalDateTime.toDateTimeWithoutSecondString(): String {
    return "${toDateString()} ${hour.toTwoString()}:${minute.toTwoString()}"
}

fun LocalDateTime.toLocalDate(): LocalDate {
    return LocalDate(year = year, monthNumber = monthNumber, dayOfMonth = dayOfMonth)
}

fun LocalDateTime.toLocalTime(): LocalTime {
    return LocalTime(hour, minute, second, nanosecond)
}

fun LocalTime.toTImeWithoutSecondString(): String {
    return "${hour.toTwoString()}:${minute.toTwoString()}"
}

fun LocalDate.toDateString(): String {
    return "${this.year}-${this.monthNumber.toTwoString()}-${this.dayOfMonth.toTwoString()}"
}