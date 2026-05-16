package com.example.unibeyond.presentation.components.generic_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//A generic Form Tab where we send from thew caller the fields and the title
@Composable
fun GenericFormTab(title: String,
                   fields: List<EditFieldConfig>,
                   onSaveClick: () -> Unit,
                   onBackClick: () -> Unit,
                   modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        BackButton(onClick = onBackClick, modifier = Modifier.padding(start = 16.dp))
        EditCard(
            title = title,
            fields = fields,
            onSaveClick = onSaveClick
        )

    }
}


