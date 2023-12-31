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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationStyleSelector(
    modifier: Modifier = Modifier,
    onOptionSelected: (AnimationStyle) -> Unit
) {
    val options = listOf("Static", "Erratic")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

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
            label = { Text("Animation") },
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
                            "Static" -> onOptionSelected(AnimationStyle.STATIC)
                            "Erratic" -> onOptionSelected(AnimationStyle.ERRATIC)
                            else -> Log.d("Animation", "Unknown option: $selectionOption")
                        }
                        expanded = false
                    }
                )
            }
        }
    }
}