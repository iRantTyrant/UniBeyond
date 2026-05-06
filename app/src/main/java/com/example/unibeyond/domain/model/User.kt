package com.example.unibeyond.domain.model

data class User (
    //--Identity of the user--
    val userId : String = "",
    val email : String ="",

    //--Profile information--
    val fullName : String = "",
    val studentId : String = "", //ex.21168 the id of the student of the university
    //What the student is studying
    val major : String = "",
    val year : String = "",
    val profileImageUrl: String = "",

    //--Social Aspect--
    val joinedClubIds : List<String> = emptyList(),
    val interestedEventsIds : List<String> = emptyList(),
    val friendsList : List<String> = emptyList(),


    //--Gamification aspect--
    val level : Int = 1,
    val xp : Int = 0,


    //--Avatar of the user--
    val avatarUrl : String = ""
)


