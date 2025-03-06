package com.carce.countrysearch.view.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomColumn(
    title: @Composable () -> Unit,
    mainContent: @Composable () -> Unit,
    appScaffoldPaddingValues: PaddingValues = PaddingValues(),
) {

    val sidePadding = 16.dp
    val columnPadding = PaddingValues(
        bottom = appScaffoldPaddingValues.calculateBottomPadding(),
        start = sidePadding,
        end = sidePadding
    )

    val columnModifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(columnPadding)

    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        title()
        mainContent()
    }
}