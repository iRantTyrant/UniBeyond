package com.example.unibeyond.presentation.clubs.manage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unibeyond.R
import com.example.unibeyond.presentation.components.generic_components.BackButton
import com.example.unibeyond.presentation.components.generic_components.EditCard
import com.example.unibeyond.presentation.components.generic_components.GenericFormTab

import com.example.unibeyond.presentation.components.generic_components.EditFieldConfig
import com.example.unibeyond.presentation.components.generic_components.UniTabRow

@Composable
fun ClubManageScreenContent(
    viewModel: ManageClubViewModel,
    onBackClick: () -> Unit,
    ){

    val titles = listOf(stringResource(R.string.details), stringResource(R.string.budget), stringResource(R.string.members), stringResource(R.string.events))
    var currentTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        UniTabRow(
            selectedTabIndex = currentTab,
            onTabSelected = { index -> currentTab = index},
            titles = titles
        )


        when (currentTab) {
            0 -> {
                //Tab : Details
                GenericFormTab(
                    title = stringResource(R.string.general_information),
                    onBackClick = onBackClick,//Handle back click,
                    fields = listOf(
                        EditFieldConfig(
                            label = stringResource(R.string.club_name),
                            value = viewModel.nameInput,
                            onValueChange = { viewModel.nameInput = it }
                        ),
                        EditFieldConfig(
                            label = stringResource(R.string.description),
                            value = viewModel.descriptionInput,
                            onValueChange = { viewModel.descriptionInput = it }
                        ),
                        EditFieldConfig(
                            label = stringResource(R.string.image_url),
                            value = viewModel.imageUrlInput,
                            onValueChange = { viewModel.imageUrlInput = it }
                        ),
                        EditFieldConfig(
                            label = stringResource(R.string.category),
                            value = viewModel.categoryInput,
                            onValueChange = { viewModel.categoryInput = it }
                        )
                    ),
                    onSaveClick = { viewModel.onSaveGeneralInfo() }
                )
            }
            1 -> {
                //Tab : Budget
                Column(modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(bottom = 16.dp)){
                    //Back button
                    BackButton(onClick = onBackClick, modifier = Modifier.padding(start = 16.dp))
                    //First Card : Budget overwrite
                    EditCard(
                        title = stringResource(R.string.budget_overwrite),
                        onSaveClick =  { viewModel.onSaveBudgetInfo() },
                        fields = listOf(
                            EditFieldConfig(
                                label = stringResource(R.string.budget),
                                value = viewModel.budgetInput,
                                onValueChange = { viewModel.budgetInput = it }
                            )
                        )
                    )
                    EditCard(
                        title = stringResource(R.string.add_budget),
                        onSaveClick =  { viewModel.onAddBudget() },
                        fields = listOf(
                            EditFieldConfig(
                                label = stringResource(R.string.amount),
                                value = viewModel.amountToAddInput,
                                onValueChange = { viewModel.amountToAddInput = it }
                            )
                        )

                    )
                    EditCard(
                        title = stringResource(R.string.add_expense),
                        onSaveClick =  { viewModel.onAddExpenseClick() },
                        fields = listOf(
                            EditFieldConfig(
                                label = stringResource(R.string.expenseTitle),
                                value = viewModel.expenseTitleInput,
                                onValueChange = { viewModel.expenseTitleInput = it }
                            ),
                            EditFieldConfig(
                                label = stringResource(R.string.amount),
                                value = viewModel.expenseAmountInput,
                                onValueChange = { viewModel.expenseAmountInput = it }
                            ),
                            EditFieldConfig(
                                label = stringResource(R.string.category),
                                value = viewModel.expenseCategoryInput,
                                onValueChange = { viewModel.expenseCategoryInput = it }
                            )
                        )
                    )
                }

            }
            2 -> Text(text = "Budget")
            3 -> Text(text = "eVENTSSSS")
        }
    }
}



