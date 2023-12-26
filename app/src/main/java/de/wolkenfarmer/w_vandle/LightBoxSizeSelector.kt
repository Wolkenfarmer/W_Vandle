package de.wolkenfarmer.w_vandle

import android.util.Log
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.wolkenfarmer.w_vandle.ui.LightBoxSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LightBoxSizeSelector(
    modifier: Modifier = Modifier,
    onOptionSelected: (LightBoxSize) -> Unit
) {
    val options = listOf("Small", "Medium", "Large")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[1]) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Light Source Size") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        Log.d("Animation", "Selected option: $selectionOption")
                        when (selectionOption) {
                            "Small" -> onOptionSelected(LightBoxSize.SMALL)
                            "Medium" -> onOptionSelected(LightBoxSize.MEDIUM)
                            "Large" -> onOptionSelected(LightBoxSize.LARGE)
                            else -> Log.d("LightBoxSize", "Unknown option: $selectionOption")
                        }
                        expanded = false
                    }
                )
            }
        }
    }
}