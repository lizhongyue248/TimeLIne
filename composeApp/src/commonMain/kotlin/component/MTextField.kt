package component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

internal const val AnimationDuration = 150

/**
 * Represents the colors of the input text, container, and content (including label, placeholder,
 * leading and trailing icons) used in a text field in different states.
 *
 * @constructor create an instance with arbitrary colors.
 * See [TextFieldDefaults.colors] for the default colors used in [TextField].
 * See [OutlinedTextFieldDefaults.colors] for the default colors used in [OutlinedTextField].
 *
 * @param focusedTextColor the color used for the input text of this text field when focused
 * @param unfocusedTextColor the color used for the input text of this text field when not
 * focused
 * @param disabledTextColor the color used for the input text of this text field when disabled
 * @param errorTextColor the color used for the input text of this text field when in error
 * state
 * @param focusedContainerColor the container color for this text field when focused
 * @param unfocusedContainerColor the container color for this text field when not focused
 * @param disabledContainerColor the container color for this text field when disabled
 * @param errorContainerColor the container color for this text field when in error state
 * @param cursorColor the cursor color for this text field
 * @param errorCursorColor the cursor color for this text field when in error state
 * @param textSelectionColors the colors used when the input text of this text field is selected
 * @param focusedIndicatorColor the indicator color for this text field when focused
 * @param unfocusedIndicatorColor the indicator color for this text field when not focused
 * @param disabledIndicatorColor the indicator color for this text field when disabled
 * @param errorIndicatorColor the indicator color for this text field when in error state
 * @param focusedLeadingIconColor the leading icon color for this text field when focused
 * @param unfocusedLeadingIconColor the leading icon color for this text field when not focused
 * @param disabledLeadingIconColor the leading icon color for this text field when disabled
 * @param errorLeadingIconColor the leading icon color for this text field when in error state
 * @param focusedTrailingIconColor the trailing icon color for this text field when focused
 * @param unfocusedTrailingIconColor the trailing icon color for this text field when not
 * focused
 * @param disabledTrailingIconColor the trailing icon color for this text field when disabled
 * @param errorTrailingIconColor the trailing icon color for this text field when in error state
 * @param focusedLabelColor the label color for this text field when focused
 * @param unfocusedLabelColor the label color for this text field when not focused
 * @param disabledLabelColor the label color for this text field when disabled
 * @param errorLabelColor the label color for this text field when in error state
 * @param focusedPlaceholderColor the placeholder color for this text field when focused
 * @param unfocusedPlaceholderColor the placeholder color for this text field when not focused
 * @param disabledPlaceholderColor the placeholder color for this text field when disabled
 * @param errorPlaceholderColor the placeholder color for this text field when in error state
 * @param focusedSupportingTextColor the supporting text color for this text field when focused
 * @param unfocusedSupportingTextColor the supporting text color for this text field when not
 * focused
 * @param disabledSupportingTextColor the supporting text color for this text field when
 * disabled
 * @param errorSupportingTextColor the supporting text color for this text field when in error
 * state
 * @param focusedPrefixColor the prefix color for this text field when focused
 * @param unfocusedPrefixColor the prefix color for this text field when not focused
 * @param disabledPrefixColor the prefix color for this text field when disabled
 * @param errorPrefixColor the prefix color for this text field when in error state
 * @param focusedSuffixColor the suffix color for this text field when focused
 * @param unfocusedSuffixColor the suffix color for this text field when not focused
 * @param disabledSuffixColor the suffix color for this text field when disabled
 * @param errorSuffixColor the suffix color for this text field when in error state
 */
@Immutable
class MTextFieldColors constructor(
    val focusedTextColor: Color,
    val unfocusedTextColor: Color,
    val disabledTextColor: Color,
    val errorTextColor: Color,
    val focusedContainerColor: Color,
    val unfocusedContainerColor: Color,
    val disabledContainerColor: Color,
    val errorContainerColor: Color,
    val cursorColor: Color,
    val errorCursorColor: Color,
    val textSelectionColors: TextSelectionColors,
    val focusedIndicatorColor: Color,
    val unfocusedIndicatorColor: Color,
    val disabledIndicatorColor: Color,
    val errorIndicatorColor: Color,
    val focusedLeadingIconColor: Color,
    val unfocusedLeadingIconColor: Color,
    val disabledLeadingIconColor: Color,
    val errorLeadingIconColor: Color,
    val focusedTrailingIconColor: Color,
    val unfocusedTrailingIconColor: Color,
    val disabledTrailingIconColor: Color,
    val errorTrailingIconColor: Color,
    val focusedLabelColor: Color,
    val unfocusedLabelColor: Color,
    val disabledLabelColor: Color,
    val errorLabelColor: Color,
    val focusedPlaceholderColor: Color,
    val unfocusedPlaceholderColor: Color,
    val disabledPlaceholderColor: Color,
    val errorPlaceholderColor: Color,
    val focusedSupportingTextColor: Color,
    val unfocusedSupportingTextColor: Color,
    val disabledSupportingTextColor: Color,
    val errorSupportingTextColor: Color,
    val focusedPrefixColor: Color,
    val unfocusedPrefixColor: Color,
    val disabledPrefixColor: Color,
    val errorPrefixColor: Color,
    val focusedSuffixColor: Color,
    val unfocusedSuffixColor: Color,
    val disabledSuffixColor: Color,
    val errorSuffixColor: Color,
) {

    /**
     * Returns a copy of this ChipColors, optionally overriding some of the values.
     * This uses the Color.Unspecified to mean “use the value from the source”
     */
    fun copy(
        focusedTextColor: Color = this.focusedTextColor,
        unfocusedTextColor: Color = this.unfocusedTextColor,
        disabledTextColor: Color = this.disabledTextColor,
        errorTextColor: Color = this.errorTextColor,
        focusedContainerColor: Color = this.focusedContainerColor,
        unfocusedContainerColor: Color = this.unfocusedContainerColor,
        disabledContainerColor: Color = this.disabledContainerColor,
        errorContainerColor: Color = this.errorContainerColor,
        cursorColor: Color = this.cursorColor,
        errorCursorColor: Color = this.errorCursorColor,
        textSelectionColors: TextSelectionColors? = this.textSelectionColors,
        focusedIndicatorColor: Color = this.focusedIndicatorColor,
        unfocusedIndicatorColor: Color = this.unfocusedIndicatorColor,
        disabledIndicatorColor: Color = this.disabledIndicatorColor,
        errorIndicatorColor: Color = this.errorIndicatorColor,
        focusedLeadingIconColor: Color = this.focusedLeadingIconColor,
        unfocusedLeadingIconColor: Color = this.unfocusedLeadingIconColor,
        disabledLeadingIconColor: Color = this.disabledLeadingIconColor,
        errorLeadingIconColor: Color = this.errorLeadingIconColor,
        focusedTrailingIconColor: Color = this.focusedTrailingIconColor,
        unfocusedTrailingIconColor: Color = this.unfocusedTrailingIconColor,
        disabledTrailingIconColor: Color = this.disabledTrailingIconColor,
        errorTrailingIconColor: Color = this.errorTrailingIconColor,
        focusedLabelColor: Color = this.focusedLabelColor,
        unfocusedLabelColor: Color = this.unfocusedLabelColor,
        disabledLabelColor: Color = this.disabledLabelColor,
        errorLabelColor: Color = this.errorLabelColor,
        focusedPlaceholderColor: Color = this.focusedPlaceholderColor,
        unfocusedPlaceholderColor: Color = this.unfocusedPlaceholderColor,
        disabledPlaceholderColor: Color = this.disabledPlaceholderColor,
        errorPlaceholderColor: Color = this.errorPlaceholderColor,
        focusedSupportingTextColor: Color = this.focusedSupportingTextColor,
        unfocusedSupportingTextColor: Color = this.unfocusedSupportingTextColor,
        disabledSupportingTextColor: Color = this.disabledSupportingTextColor,
        errorSupportingTextColor: Color = this.errorSupportingTextColor,
        focusedPrefixColor: Color = this.focusedPrefixColor,
        unfocusedPrefixColor: Color = this.unfocusedPrefixColor,
        disabledPrefixColor: Color = this.disabledPrefixColor,
        errorPrefixColor: Color = this.errorPrefixColor,
        focusedSuffixColor: Color = this.focusedSuffixColor,
        unfocusedSuffixColor: Color = this.unfocusedSuffixColor,
        disabledSuffixColor: Color = this.disabledSuffixColor,
        errorSuffixColor: Color = this.errorSuffixColor,
    ) = TextFieldColors(
        focusedTextColor.takeOrElse { this.focusedTextColor },
        unfocusedTextColor.takeOrElse { this.unfocusedTextColor },
        disabledTextColor.takeOrElse { this.disabledTextColor },
        errorTextColor.takeOrElse { this.errorTextColor },
        focusedContainerColor.takeOrElse { this.focusedContainerColor },
        unfocusedContainerColor.takeOrElse { this.unfocusedContainerColor },
        disabledContainerColor.takeOrElse { this.disabledContainerColor },
        errorContainerColor.takeOrElse { this.errorContainerColor },
        cursorColor.takeOrElse { this.cursorColor },
        errorCursorColor.takeOrElse { this.errorCursorColor },
        textSelectionColors.takeOrElse { this.textSelectionColors },
        focusedIndicatorColor.takeOrElse { this.focusedIndicatorColor },
        unfocusedIndicatorColor.takeOrElse { this.unfocusedIndicatorColor },
        disabledIndicatorColor.takeOrElse { this.disabledIndicatorColor },
        errorIndicatorColor.takeOrElse { this.errorIndicatorColor },
        focusedLeadingIconColor.takeOrElse { this.focusedLeadingIconColor },
        unfocusedLeadingIconColor.takeOrElse { this.unfocusedLeadingIconColor },
        disabledLeadingIconColor.takeOrElse { this.disabledLeadingIconColor },
        errorLeadingIconColor.takeOrElse { this.errorLeadingIconColor },
        focusedTrailingIconColor.takeOrElse { this.focusedTrailingIconColor },
        unfocusedTrailingIconColor.takeOrElse { this.unfocusedTrailingIconColor },
        disabledTrailingIconColor.takeOrElse { this.disabledTrailingIconColor },
        errorTrailingIconColor.takeOrElse { this.errorTrailingIconColor },
        focusedLabelColor.takeOrElse { this.focusedLabelColor },
        unfocusedLabelColor.takeOrElse { this.unfocusedLabelColor },
        disabledLabelColor.takeOrElse { this.disabledLabelColor },
        errorLabelColor.takeOrElse { this.errorLabelColor },
        focusedPlaceholderColor.takeOrElse { this.focusedPlaceholderColor },
        unfocusedPlaceholderColor.takeOrElse { this.unfocusedPlaceholderColor },
        disabledPlaceholderColor.takeOrElse { this.disabledPlaceholderColor },
        errorPlaceholderColor.takeOrElse { this.errorPlaceholderColor },
        focusedSupportingTextColor.takeOrElse { this.focusedSupportingTextColor },
        unfocusedSupportingTextColor.takeOrElse { this.unfocusedSupportingTextColor },
        disabledSupportingTextColor.takeOrElse { this.disabledSupportingTextColor },
        errorSupportingTextColor.takeOrElse { this.errorSupportingTextColor },
        focusedPrefixColor.takeOrElse { this.focusedPrefixColor },
        unfocusedPrefixColor.takeOrElse { this.unfocusedPrefixColor },
        disabledPrefixColor.takeOrElse { this.disabledPrefixColor },
        errorPrefixColor.takeOrElse { this.errorPrefixColor },
        focusedSuffixColor.takeOrElse { this.focusedSuffixColor },
        unfocusedSuffixColor.takeOrElse { this.unfocusedSuffixColor },
        disabledSuffixColor.takeOrElse { this.disabledSuffixColor },
        errorSuffixColor.takeOrElse { this.errorSuffixColor },
    )

    internal fun TextSelectionColors?.takeOrElse(block: () -> TextSelectionColors):
            TextSelectionColors = this ?: block()

    /**
     * Represents the color used for the leading icon of this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    internal fun leadingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        return rememberUpdatedState(
            when {
                !enabled -> disabledLeadingIconColor
                isError -> errorLeadingIconColor
                focused -> focusedLeadingIconColor
                else -> unfocusedLeadingIconColor
            }
        )
    }

    /**
     * Represents the color used for the trailing icon of this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    internal fun trailingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        return rememberUpdatedState(
            when {
                !enabled -> disabledTrailingIconColor
                isError -> errorTrailingIconColor
                focused -> focusedTrailingIconColor
                else -> unfocusedTrailingIconColor
            }
        )
    }

    /**
     * Represents the color used for the border indicator of this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    internal fun indicatorColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledIndicatorColor
            isError -> errorIndicatorColor
            focused -> focusedIndicatorColor
            else -> unfocusedIndicatorColor
        }
        return if (enabled) {
            animateColorAsState(targetValue, tween(durationMillis = AnimationDuration))
        } else {
            rememberUpdatedState(targetValue)
        }
    }

    /**
     * Represents the container color for this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    fun containerColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledContainerColor
            isError -> errorContainerColor
            focused -> focusedContainerColor
            else -> unfocusedContainerColor
        }
        return animateColorAsState(targetValue, tween(durationMillis = AnimationDuration))
    }

    /**
     * Represents the color used for the placeholder of this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    internal fun placeholderColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledPlaceholderColor
            isError -> errorPlaceholderColor
            focused -> focusedPlaceholderColor
            else -> unfocusedPlaceholderColor
        }
        return rememberUpdatedState(targetValue)
    }

    /**
     * Represents the color used for the label of this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    internal fun labelColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledLabelColor
            isError -> errorLabelColor
            focused -> focusedLabelColor
            else -> unfocusedLabelColor
        }
        return rememberUpdatedState(targetValue)
    }

    /**
     * Represents the color used for the input field of this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    internal fun textColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledTextColor
            isError -> errorTextColor
            focused -> focusedTextColor
            else -> unfocusedTextColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    internal fun supportingTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        return rememberUpdatedState(
            when {
                !enabled -> disabledSupportingTextColor
                isError -> errorSupportingTextColor
                focused -> focusedSupportingTextColor
                else -> unfocusedSupportingTextColor
            }
        )
    }

    /**
     * Represents the color used for the prefix of this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    internal fun prefixColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledPrefixColor
            isError -> errorPrefixColor
            focused -> focusedPrefixColor
            else -> unfocusedPrefixColor
        }
        return rememberUpdatedState(targetValue)
    }

    /**
     * Represents the color used for the suffix of this text field.
     *
     * @param enabled whether the text field is enabled
     * @param isError whether the text field's current value is in error
     * @param interactionSource the [InteractionSource] of this text field. Helps to determine if
     * the text field is in focus or not
     */
    @Composable
    internal fun suffixColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledSuffixColor
            isError -> errorSuffixColor
            focused -> focusedSuffixColor
            else -> unfocusedSuffixColor
        }
        return rememberUpdatedState(targetValue)
    }

    /**
     * Represents the color used for the cursor of this text field.
     *
     * @param isError whether the text field's current value is in error
     */
    @Composable
    internal fun cursorColor(isError: Boolean): State<Color> {
        return rememberUpdatedState(if (isError) errorCursorColor else cursorColor)
    }

    /**
     * Represents the colors used for text selection in this text field.
     */
    internal val selectionColors: TextSelectionColors
        @Composable get() = textSelectionColors


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is TextFieldColors) return false

        if (focusedTextColor != other.focusedTextColor) return false
        if (unfocusedTextColor != other.unfocusedTextColor) return false
        if (disabledTextColor != other.disabledTextColor) return false
        if (errorTextColor != other.errorTextColor) return false
        if (focusedContainerColor != other.focusedContainerColor) return false
        if (unfocusedContainerColor != other.unfocusedContainerColor) return false
        if (disabledContainerColor != other.disabledContainerColor) return false
        if (errorContainerColor != other.errorContainerColor) return false
        if (cursorColor != other.cursorColor) return false
        if (errorCursorColor != other.errorCursorColor) return false
        if (textSelectionColors != other.textSelectionColors) return false
        if (focusedIndicatorColor != other.focusedIndicatorColor) return false
        if (unfocusedIndicatorColor != other.unfocusedIndicatorColor) return false
        if (disabledIndicatorColor != other.disabledIndicatorColor) return false
        if (errorIndicatorColor != other.errorIndicatorColor) return false
        if (focusedLeadingIconColor != other.focusedLeadingIconColor) return false
        if (unfocusedLeadingIconColor != other.unfocusedLeadingIconColor) return false
        if (disabledLeadingIconColor != other.disabledLeadingIconColor) return false
        if (errorLeadingIconColor != other.errorLeadingIconColor) return false
        if (focusedTrailingIconColor != other.focusedTrailingIconColor) return false
        if (unfocusedTrailingIconColor != other.unfocusedTrailingIconColor) return false
        if (disabledTrailingIconColor != other.disabledTrailingIconColor) return false
        if (errorTrailingIconColor != other.errorTrailingIconColor) return false
        if (focusedLabelColor != other.focusedLabelColor) return false
        if (unfocusedLabelColor != other.unfocusedLabelColor) return false
        if (disabledLabelColor != other.disabledLabelColor) return false
        if (errorLabelColor != other.errorLabelColor) return false
        if (focusedPlaceholderColor != other.focusedPlaceholderColor) return false
        if (unfocusedPlaceholderColor != other.unfocusedPlaceholderColor) return false
        if (disabledPlaceholderColor != other.disabledPlaceholderColor) return false
        if (errorPlaceholderColor != other.errorPlaceholderColor) return false
        if (focusedSupportingTextColor != other.focusedSupportingTextColor) return false
        if (unfocusedSupportingTextColor != other.unfocusedSupportingTextColor) return false
        if (disabledSupportingTextColor != other.disabledSupportingTextColor) return false
        if (errorSupportingTextColor != other.errorSupportingTextColor) return false
        if (focusedPrefixColor != other.focusedPrefixColor) return false
        if (unfocusedPrefixColor != other.unfocusedPrefixColor) return false
        if (disabledPrefixColor != other.disabledPrefixColor) return false
        if (errorPrefixColor != other.errorPrefixColor) return false
        if (focusedSuffixColor != other.focusedSuffixColor) return false
        if (unfocusedSuffixColor != other.unfocusedSuffixColor) return false
        if (disabledSuffixColor != other.disabledSuffixColor) return false
        if (errorSuffixColor != other.errorSuffixColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = focusedTextColor.hashCode()
        result = 31 * result + unfocusedTextColor.hashCode()
        result = 31 * result + disabledTextColor.hashCode()
        result = 31 * result + errorTextColor.hashCode()
        result = 31 * result + focusedContainerColor.hashCode()
        result = 31 * result + unfocusedContainerColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + errorContainerColor.hashCode()
        result = 31 * result + cursorColor.hashCode()
        result = 31 * result + errorCursorColor.hashCode()
        result = 31 * result + textSelectionColors.hashCode()
        result = 31 * result + focusedIndicatorColor.hashCode()
        result = 31 * result + unfocusedIndicatorColor.hashCode()
        result = 31 * result + disabledIndicatorColor.hashCode()
        result = 31 * result + errorIndicatorColor.hashCode()
        result = 31 * result + focusedLeadingIconColor.hashCode()
        result = 31 * result + unfocusedLeadingIconColor.hashCode()
        result = 31 * result + disabledLeadingIconColor.hashCode()
        result = 31 * result + errorLeadingIconColor.hashCode()
        result = 31 * result + focusedTrailingIconColor.hashCode()
        result = 31 * result + unfocusedTrailingIconColor.hashCode()
        result = 31 * result + disabledTrailingIconColor.hashCode()
        result = 31 * result + errorTrailingIconColor.hashCode()
        result = 31 * result + focusedLabelColor.hashCode()
        result = 31 * result + unfocusedLabelColor.hashCode()
        result = 31 * result + disabledLabelColor.hashCode()
        result = 31 * result + errorLabelColor.hashCode()
        result = 31 * result + focusedPlaceholderColor.hashCode()
        result = 31 * result + unfocusedPlaceholderColor.hashCode()
        result = 31 * result + disabledPlaceholderColor.hashCode()
        result = 31 * result + errorPlaceholderColor.hashCode()
        result = 31 * result + focusedSupportingTextColor.hashCode()
        result = 31 * result + unfocusedSupportingTextColor.hashCode()
        result = 31 * result + disabledSupportingTextColor.hashCode()
        result = 31 * result + errorSupportingTextColor.hashCode()
        result = 31 * result + focusedPrefixColor.hashCode()
        result = 31 * result + unfocusedPrefixColor.hashCode()
        result = 31 * result + disabledPrefixColor.hashCode()
        result = 31 * result + errorPrefixColor.hashCode()
        result = 31 * result + focusedSuffixColor.hashCode()
        result = 31 * result + unfocusedSuffixColor.hashCode()
        result = 31 * result + disabledSuffixColor.hashCode()
        result = 31 * result + errorSuffixColor.hashCode()
        return result
    }
}


fun fromMaterial3TextFieldColors(colors: TextFieldColors) =
    MTextFieldColors(
        colors.focusedTextColor,
        colors.unfocusedTextColor,
        colors.disabledTextColor,
        colors.errorTextColor,
        colors.focusedContainerColor,
        colors.unfocusedContainerColor,
        colors.disabledContainerColor,
        colors.errorContainerColor,
        colors.cursorColor,
        colors.errorCursorColor,
        colors.textSelectionColors,
        colors.focusedIndicatorColor,
        colors.unfocusedIndicatorColor,
        colors.disabledIndicatorColor,
        colors.errorIndicatorColor,
        colors.focusedLeadingIconColor,
        colors.unfocusedLeadingIconColor,
        colors.disabledLeadingIconColor,
        colors.errorLeadingIconColor,
        colors.focusedTrailingIconColor,
        colors.unfocusedTrailingIconColor,
        colors.disabledTrailingIconColor,
        colors.errorTrailingIconColor,
        colors.focusedLabelColor,
        colors.unfocusedLabelColor,
        colors.disabledLabelColor,
        colors.errorLabelColor,
        colors.focusedPlaceholderColor,
        colors.unfocusedPlaceholderColor,
        colors.disabledPlaceholderColor,
        colors.errorPlaceholderColor,
        colors.focusedSupportingTextColor,
        colors.unfocusedSupportingTextColor,
        colors.disabledSupportingTextColor,
        colors.errorSupportingTextColor,
        colors.focusedPrefixColor,
        colors.unfocusedPrefixColor,
        colors.disabledPrefixColor,
        colors.errorPrefixColor,
        colors.focusedSuffixColor,
        colors.unfocusedSuffixColor,
        colors.disabledSuffixColor,
        colors.errorSuffixColor
    )


/**
 * <a href="https://m3.material.io/components/text-fields/overview" class="external" target="_blank">Material Design filled text field</a>.
 *
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * If you are looking for an outlined version, see [OutlinedTextField].
 *
 * A simple single line text field looks like:
 *
 * @sample androidx.compose.material3.samples.SimpleTextFieldSample
 *
 * You may provide a placeholder:
 *
 * @sample androidx.compose.material3.samples.TextFieldWithPlaceholder
 *
 * You can also provide leading and trailing icons:
 *
 * @sample androidx.compose.material3.samples.TextFieldWithIcons
 *
 * You can also provide a prefix or suffix to the text:
 *
 * @sample androidx.compose.material3.samples.TextFieldWithPrefixAndSuffix
 *
 * To handle the error input state, use [isError] parameter:
 *
 * @sample androidx.compose.material3.samples.TextFieldWithErrorState
 *
 * Additionally, you may provide additional message at the bottom:
 *
 * @sample androidx.compose.material3.samples.TextFieldWithSupportingText
 *
 * Password text field example:
 *
 * @sample androidx.compose.material3.samples.PasswordTextField
 *
 * Hiding a software keyboard on IME action performed:
 *
 * @sample androidx.compose.material3.samples.TextFieldWithHideKeyboardOnImeAction
 *
 * If apart from input text change you also want to observe the cursor location, selection range,
 * or IME composition use the TextField overload with the [TextFieldValue] parameter instead.
 *
 * @param value the input text to be shown in the text field
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier the [Modifier] to be applied to this text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param readOnly controls the editable state of the text field. When `true`, the text field cannot
 * be modified. However, a user can focus it and copy text from it. Read-only text fields are
 * usually used to display pre-filled forms that a user cannot edit.
 * @param textStyle the style to be applied to the input text. Defaults to [LocalTextStyle].
 * @param label the optional label to be displayed inside the text field container. The default
 * text style for internal [Text] is [Typography.bodySmall] when the text field is in focus and
 * [Typography.bodyLarge] when the text field is not in focus
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 * the input text is empty. The default text style for internal [Text] is [Typography.bodyLarge]
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param prefix the optional prefix to be displayed before the input text in the text field
 * @param suffix the optional suffix to be displayed after the input text in the text field
 * @param supportingText the optional supporting text to be displayed below the text field
 * @param isError indicates if the text field's current value is in error. If set to true, the
 * label, bottom indicator and trailing icon by default will be displayed in error color
 * @param visualTransformation transforms the visual representation of the input [value]
 * For example, you can use
 * [PasswordVisualTransformation][androidx.compose.ui.text.input.PasswordVisualTransformation] to
 * create a password text field. By default, no visual transformation is applied.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    contentPadding: PaddingValues =
        if (label == null) {
            TextFieldDefaults.contentPaddingWithoutLabel()
        } else {
            TextFieldDefaults.contentPaddingWithLabel()
        },
    pointerHand: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    // If color is not provided via the text style, use content color as a default
    val mColors = fromMaterial3TextFieldColors(colors)
    val textColor = textStyle.color.takeOrElse {
        mColors.textColor(enabled, isError, interactionSource).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    var boxModifier = Modifier
        .background(mColors.containerColor(enabled, isError, interactionSource).value, shape)
        .indicatorLine(enabled, isError, interactionSource, colors)
    if (onClick != null) {
        boxModifier = boxModifier
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
    }

    CompositionLocalProvider(LocalTextSelectionColors provides mColors.selectionColors) {
        BasicTextField(
            value = value,
            modifier = modifier
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight
                ),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(mColors.cursorColor(isError).value),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            decorationBox = @Composable { innerTextField ->
                // places leading icon, text field with label and placeholder, trailing icon
                TextFieldDefaults.DecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = {
//                        Box(modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)) { innerTextField() }
                        innerTextField()
                    },
                    placeholder = placeholder,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    shape = shape,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    contentPadding = contentPadding,
                    container = {
                        Box(
                            if (pointerHand) {
                                boxModifier.pointerHoverIcon(PointerIcon.Hand)
                            } else boxModifier
                        )
                    }
                )
            }
        )
    }
}