package com.carce.countrysearch.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithIconAndClear(
    modifier: Modifier = Modifier,
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
            )
        },
        modifier = modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.titleMedium,
        colors = textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp)
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
