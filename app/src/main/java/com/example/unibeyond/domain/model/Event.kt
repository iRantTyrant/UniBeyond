package com.example.unibeyond.domain.model

data class Event(
    //--Identity of the event--
    val eventId : String = "",
    val eventName : String = "",
    //Which club hosts the event
    val clubId : String = "",

    //--Event information--
    val description : String = "",
    val date : String = "",
    val locationName : String ="",
    val category : String = "",

    //--Used for the map--
    val longitude : Double = 0.0,
    val latitude : Double = 0.0,

    //--Used to show an image for the card--
    val imageUrl : String = "",

    //--Social Aspect--
    val attendingUserIds : List<String> = emptyList()

)
