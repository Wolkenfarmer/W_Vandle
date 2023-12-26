package de.wolkenfarmer.w_vandle

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ColorPicker(
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    var red by remember { mutableStateOf(1f) }
    var green by remember { mutableStateOf(1f) }
    var blue by remember { mutableStateOf(1f) }

    Column(
        modifier = modifier
    ) {
        Text("Red: ${red * 255}")
        Slider(
            value = red,
            onValueChange = { red = it },
            valueRange = 0f..1f
        )
        Text("Green: ${green * 255}")
        Slider(
            value = green,
            onValueChange = { green = it },
            valueRange = 0f..1f
        )
        Text("Blue: ${blue * 255}")
        Slider(
            value = blue,
            onValueChange = { blue = it },
            valueRange = 0f..1f
        )


        val selectedColor = Color(red, green, blue)
        onColorSelected(selectedColor)
    }
}

@Preview
@Composable
fun ColorPickerPreview() {
    ColorPicker(
        modifier = Modifier,
        onColorSelected = {}
    )
}
