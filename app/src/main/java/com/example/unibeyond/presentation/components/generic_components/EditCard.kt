package com.example.unibeyond.presentation.components.generic_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


data class EditFieldConfig(
    val label: String,
    val value: String,
    val onValueChange: (String) -> Unit,
)

@Composable
fun EditCard(
    title: String,
    fields: List<EditFieldConfig>,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().padding(top=16.dp,start=16.dp,end=16.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // For each field in list, create a UniBeyondTextField
            fields.forEach { field ->
                UniBeyondTextField(
                    value = field.value,
                    onValueChange = field.onValueChange,
                    label =  field.label)

            }

            Spacer(modifier = Modifier.height(8.dp))

            UniBeyondButton(
                text = "Save",
                onClick = onSaveClick
            )
        }

    }

}