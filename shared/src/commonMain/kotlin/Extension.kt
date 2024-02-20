import kotlinx.datetime.LocalDateTime

fun Int.toTwoString(): String =
    if (this > 10) {
        "$this"
    } else {
        "0$this"
    }

fun LocalDateTime.toDateString(): String {
    return "${this.year}-${this.monthNumber.toTwoString()}-${this.dayOfMonth.toTwoString()}"
}
