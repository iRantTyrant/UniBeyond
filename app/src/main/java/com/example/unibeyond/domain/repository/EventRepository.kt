package com.example.unibeyond.domain.repository

import com.example.unibeyond.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    //--Read Operations--

    //Get all the events .We will use this for the discover and the map tabs
    fun getEvents():Flow<List<Event>>

    //Get the events for a specific club
    fun getEventsForClub(clubId:String):Flow<List<Event>>

    //Get all future events for a club
    fun getUpcomingEventsForClub(clubId: String):Flow<List<Event>>

    //Get all past events for a club
    fun getPastEventsForClub(clubId: String):Flow<List<Event>>

    //Get the events for a specific user
    fun getEventsForUser(userId:String):Flow<List<Event>>

    //Get a specific event
    suspend fun getEventById(eventId:String):Event?

    //--Write Operations (Actions)--

    //Create an event from a club
    suspend fun createEvent(event: Event):Result<Unit>

    //Update an event
    suspend fun updateEvent(event: Event):Result<Unit>

    //Delete an event manually
    suspend fun deleteEvent(eventId: String):Result<Unit>

    //--Interaction Operations--
    suspend fun toggleInterest(eventId: String, userId: String, isInterested: Boolean):Result<Unit>

    //--Social Aspect--
    fun getInterestedUsers(eventId: String):Flow<List<String>>

}



