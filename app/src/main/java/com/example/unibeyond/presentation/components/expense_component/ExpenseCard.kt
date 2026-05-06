package com.example.unibeyond.presentation.components.expense_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unibeyond.R
import com.example.unibeyond.domain.model.Club

@Composable
fun ExpenseCard(
    club: Club
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {

            // Header with icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.AttachMoney, contentDescription = null)
                Text(
                    text = stringResource(R.string.budget_management),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // The budget summary box
            BudgetSummaryBox(
                budgetTotal = club.budgetTotal,
                budgetRemaining = club.budgetRemaining,
                budgetSpent = club.budgetSpent
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                color = Color(0xFFF0F0F0)
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Expense Items
            for(expense in club.recentExpenses) {
                ExpenseItem(title = expense.title,
                    price = expense.amount.toString(),
                    category = expense.category,
                    date = expense.date)
            }
        }
    }
}
