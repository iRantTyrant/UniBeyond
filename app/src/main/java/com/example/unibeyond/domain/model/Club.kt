package com.example.unibeyond.domain.model

data class Club(
    //Unique club ID (generated through firebase)
    val id:String = "",


    //--Basic club info--
    val name:String = "",
    val description:String = "",
    val imageUrl:String = "",
    val category:String = "",
    //--Basic Club Info--

    //--Club participants management--
    //The unique id of the owner
    val ownerId:String = "",
    //The list of member ids
    val memberIds: List<String> = emptyList(),
    //The list of the pending members
    val pendingMemberIds: List<String> = emptyList(),
    //--Club participants management--

    //--Economics--
    //Total budget
    val budgetTotal : Double = 0.0,
    //Budget remaining
    val budgetRemaining : Double = 0.0,
    //Budget spent ( used for graphs )
    val budgetSpent : Double = 0.0,

    val recentExpenses : List<Expense> = emptyList()
    //--Economics--


)
