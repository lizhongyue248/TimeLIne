package state

data class AlertState(
    val visible: Boolean = false,
    val title: String = "",
    val text: String = "",
    val onConfirm: () -> Unit = {},
)
