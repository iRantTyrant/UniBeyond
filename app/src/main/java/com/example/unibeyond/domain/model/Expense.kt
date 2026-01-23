package com.example.unibeyond.domain.model

data class Expense(
    val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val date: String = "",
    val category: String = ""
)
