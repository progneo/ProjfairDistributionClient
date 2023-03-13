package ui.details.project.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.BorderedTitledComposable
import common.theme.BlueMainLight

@Composable
fun EditableDescriptionField(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    var contentText by rememberSaveable(TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(content))
    }

    BorderedTitledComposable(
        modifier = modifier,
        title = title
    ) {
        BasicTextField(
            value = contentText,
            onValueChange = {
                contentText = it
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = BlueMainLight,
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.padding(12.dp)) {
                    innerTextField()
                }
            }
        )
    }
}