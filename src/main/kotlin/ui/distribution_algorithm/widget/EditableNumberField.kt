package ui.distribution_algorithm.widget

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
fun EditableNumberField(
    modifier: Modifier = Modifier,
    editableNumber: Int,
    maxNumberCount: Int,
    onDataChanged: (String) -> Unit
) {
    val stringPool = "01234567890"

    BorderedTitledComposable(
        modifier = modifier
    ) {
        BasicTextField(
            value = editableNumber.toString(),
            onValueChange = {
                if (it.isEmpty() || it.length in (1..maxNumberCount) && it.last() in stringPool) {
                    onDataChanged(it)
                }
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
            },
        )
    }
}