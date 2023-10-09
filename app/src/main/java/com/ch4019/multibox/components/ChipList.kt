package com.ch4019.multibox.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ch4019.multibox.model.SelectList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipList(
    isSelected : MutableState<Boolean>,
    menuItems: List<SelectList>,
    value: MutableState<String>,
    setLabel: String
) {
    val toSelect = remember{ mutableStateOf(false) }
    val label = remember{ mutableStateOf(setLabel) }
    ElevatedFilterChip(
        selected = isSelected.value,
        onClick = { toSelect.value = !toSelect.value },
        label = {
            Text(text = label.value)
            DropdownMenu(
                expanded = toSelect.value,
                onDismissRequest = { toSelect.value = !toSelect.value }
            ) {
                menuItems.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.label)},
                        onClick = {
                            isSelected.value = true
                            label.value = it.label
                            value.value = it.value
                            toSelect.value = !toSelect.value
                        }
                    )
                }
            }
        }
    )
}