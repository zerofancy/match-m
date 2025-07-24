package top.ntutn.match.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Math Theme
 */
object MTT {
    /**
     * 该配色方案使用取色器提取自[https://pratikborsadiya.in/vali-admin/bootstrap-components.html](https://pratikborsadiya.in/vali-admin/bootstrap-components.html)
     */
    @Composable
    fun primaryButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xff009688),
        contentColor = Color.White,
        disabledContainerColor = Color(0xff59BAB1),
        disabledContentColor = Color(0xffFFF8E1)
    )

    @Composable
    fun secondaryButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xff6C757D),
        contentColor = Color.White,
        disabledContainerColor = Color(0xff9FA5AA),
        disabledContentColor = Color(0xffFBFFFF)
    )

    @Composable
    fun PrimaryButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
        shape: Shape = MaterialTheme.shapes.small,
        border: BorderStroke? = null,
        colors: ButtonColors = primaryButtonColors(),
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        content: @Composable RowScope.() -> Unit
    ) = Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )

    @Composable
    fun SecondaryButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
        shape: Shape = MaterialTheme.shapes.small,
        border: BorderStroke? = null,
        colors: ButtonColors = secondaryButtonColors(),
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        content: @Composable RowScope.() -> Unit
    ) = Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )

    @Composable
    fun SimpleDialog(
        title: String,
        content: String,
        confirmButton: Pair<String, () -> Unit>,
        cancelButton: Pair<String, () -> Unit>? = null,
        onCancel: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onCancel,
            confirmButton = {
                PrimaryButton(
                    onClick = confirmButton.second
                ) {
                    Text(text = confirmButton.first)
                }
            },
            dismissButton = {
                SecondaryButton(
                    onClick = cancelButton?.second ?: {}
                ) {
                    Text(text = cancelButton?.first ?: "")
                }
            },
            title = { Text(text = title) },
            text = { Text(text = content) },
        )
    }
}

@Composable
fun PrimaryButtonPreview() = MTT.PrimaryButton(onClick = {}) {
    Text("默认按钮")
}

@Composable
fun SecondaryButtonPreview() = MTT.SecondaryButton(onClick = {}) {
    Text("二级按钮")
}