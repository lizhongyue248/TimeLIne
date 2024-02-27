package state

data class PeriodDialogState(
    val name: String = "",
    val visible: Boolean = false,
    val onDismissRequest: () -> Unit = {},
    val onConfirmation: (String) -> Unit = {},
)
