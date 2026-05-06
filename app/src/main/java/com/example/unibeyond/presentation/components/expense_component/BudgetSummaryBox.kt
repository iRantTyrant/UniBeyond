package com.example.unibeyond.presentation.components.expense_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unibeyond.R
import com.example.unibeyond.ui.theme.UniBeyondGradient

//A box that holds a bar that fills up as budget is used and three surfaces showing budget left,spent,total
@Composable
fun BudgetSummaryBox(
    budgetTotal: Double,
    budgetRemaining: Double,
    budgetSpent: Double
){
    val budgetUsed = budgetTotal - budgetRemaining
    val budgetUsedPercent : Int = ((budgetUsed / budgetTotal) * 100).toInt()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        //Row for the text and the percentage
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = stringResource(R.string.budget_used),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.gray)
            )
            Text(
                text = "$budgetUsedPercent%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Progress Indicator
        LinearProgressIndicator(
            progress = { budgetUsedPercent.toFloat() / 100f },
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape)
                .background(brush = UniBeyondGradient),
            color = Color.Transparent,
            trackColor = Color(0xFFE0E0E0)
        )

        // A row with the three surfaces
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between the surfaces
        ){
            BudgetSurface(
                text = stringResource(R.string.budget_total),
                value = "$budgetTotal€",
                textColor = colorResource(R.color.black),
                backgroundColor = colorResource(R.color.light_blue),
                modifier = Modifier.weight(1f), // Weight so they take the same space
            )
            BudgetSurface(
                text = stringResource(R.string.budget_spent),
                value = "$budgetSpent€",
                textColor = colorResource(R.color.red),
                backgroundColor = colorResource(R.color.light_red),
                modifier = Modifier.weight(1f), // Weight so they take the same space
            )
            BudgetSurface(
                text = stringResource(R.string.budget_remaining),
                value = "$budgetRemaining€",
                textColor = colorResource(R.color.green),
                backgroundColor = colorResource(R.color.light_green),
                modifier = Modifier.weight(1f), // Weight so they take the same space
            )
        }
    }
}
