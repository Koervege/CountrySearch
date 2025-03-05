package com.carce.countrysearch.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithIconAndClear(
    leadingIcon: @Composable () -> Unit,
    fieldValue: String = "",
    onChanged: (String) -> Unit,
    placeHolderValue: String,
    focusManager: FocusManager
) {
    TextField(
        value = fieldValue,
        onValueChange = onChanged,
        leadingIcon = { leadingIcon() },
        trailingIcon = { if (fieldValue.isNotBlank()) TrailingIcon(onChanged, focusManager) },
        placeholder = {
            Text(
                text = placeHolderValue,
                style = MaterialTheme.typography.titleMedium,
                //color = MaterialTheme.colorScheme.onPrimary.copy(.5f)
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.titleMedium,
        colors = textFieldColors()
    )
}

@Composable
fun TrailingIcon(
    resetField: (String) -> Unit,
    focusManager: FocusManager
) {
    IconButton(
        onClick = {
            resetField("")
            focusManager.clearFocus()
        }
    ) {
        Icon(
            Icons.Default.Clear,
            contentDescription = "Clear",
        )
    }
}
