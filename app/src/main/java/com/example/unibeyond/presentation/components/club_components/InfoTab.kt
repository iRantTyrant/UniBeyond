package com.example.unibeyond.presentation.components.club_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unibeyond.presentation.clubs.manage.ManageClubViewModel
import com.example.unibeyond.presentation.components.generic_components.BackButton
import com.example.unibeyond.presentation.components.generic_components.EditCard
import com.example.unibeyond.presentation.components.generic_components.EditFieldConfig

@Composable
fun InfoTab(viewModel: ManageClubViewModel,onBackClick: () -> Unit) {
    Column(modifier= Modifier.fillMaxSize()
        .verticalScroll(state = rememberScrollState())
        .padding(vertical = 16.dp)) {
        BackButton(onClick = onBackClick)
        EditCard(
            title = "General Information",
            fields = listOf(
                EditFieldConfig(
                    label = "Club Name",
                    value = viewModel.nameInput,
                    onValueChange = { viewModel.nameInput = it }
                ),
                EditFieldConfig(
                    label = "Description",
                    value = viewModel.descriptionInput,
                    onValueChange = { viewModel.descriptionInput = it }
                ),
                EditFieldConfig(
                    label = "Image URL",
                    value = viewModel.imageUrlInput,
                    onValueChange = { viewModel.imageUrlInput = it }
                ),
                EditFieldConfig(
                    label = "Category",
                    value = viewModel.categoryInput,
                    onValueChange = { viewModel.categoryInput = it }
                )
            ),
            onSaveClick = { viewModel.onSaveGeneralInfo() }
        )
    }
}

