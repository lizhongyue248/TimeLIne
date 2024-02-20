fun Int.toTwoString(): String =
    if (this > 10) {
        "$this"
    } else {
        "0$this"
    }
