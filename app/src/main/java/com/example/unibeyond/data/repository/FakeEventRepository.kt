package com.example.unibeyond.data.repository

import com.example.unibeyond.domain.model.Event
import com.example.unibeyond.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalDateTime


class FakeEventRepository : EventRepository {

    //Mock data for events
    private val mockEvents = mutableListOf<Event>(
        Event(
            eventId = "ev1",
            eventName = "Photography Exhibition",
            description = "Join us for a showcase of our latest photography techniques",
            clubId = "c1",
            date = "2026-02-15T18:00",
            locationName = "Main Hall",
            category = "Showcase",
            longitude = 40.6892,
            latitude = -74.0445,
            imageUrl = "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Ftemple-news.com%2Ftylers-reimagined-pyramid-club-gallery-enters-final-exhibition-month%2F&ved=0CBYQjRxqFwoTCMDpuo2Wn5IDFQAAAAAdAAAAABAI&opi=89978449",
            attendingUserIds =listOf("u2")
        ),
        Event(
            eventId = "ev2",
            eventName = "Try Scuba!",
            description = "Come have a try at scuba diving",
            clubId = "c2",
            date = "2026-02-16T10:00",
            locationName = "Pool facilities",
            category = "Sports",
            longitude = 40.6192,
            latitude = -74.0345,
            imageUrl = "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fwww.santoriniscubaacademy.com%2Fproduct%2Ftry-scuba%2F&ved=0CBYQjRxqFwoTCPDSyeqWn5IDFQAAAAAdAAAAABAI&opi=89978449",
            attendingUserIds = listOf("u1","u3")
        ),
        Event(
            eventId = "ev3",
            eventName = "Movie Night",
            description = "Join us for a movie night with our talented actors",
            clubId = "c3",
            date = "2026-02-17T21:00",
            locationName = "Cinema Hall",
            category = "Entertainment",
            longitude = 40.6892,
            latitude = -74.0445,
            imageUrl = "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fwww.sortiraparis.com%2Fel%2Fchobi%2Ffestival-kinimatografou%2Fguides%2F79140-oi-pio-anamenomenes-tainies-tes-anoixes-tou-2026-stous-kinematographous&ved=0CBYQjRxqFwoTCKCZ94OZn5IDFQAAAAAdAAAAABAI&opi=89978449",
            attendingUserIds = listOf("u1")

        )
    )

    override fun getEvents(): Flow<List<Event>> {
       return flowOf(mockEvents)
    }

    override fun getEventsForClub(clubId: String): Flow<List<Event>> {
        return flowOf(mockEvents.filter { it.clubId == clubId })
    }

    override fun getUpcomingEventsForClub(clubId: String): Flow<List<Event>> {

        val now = LocalDateTime.now()

        return flowOf(
            mockEvents.filter { event ->
                try {

                    val eventDateTime = LocalDateTime.parse(event.date)


                    event.clubId == clubId && eventDateTime.isAfter(now)
                } catch (e: Exception) {
                    false
                }
            }
        )
    }

    override fun getPastEventsForClub(clubId: String): Flow<List<Event>> {
        val now = LocalDateTime.now()

        return flowOf(
            mockEvents.filter { event ->
                try {
                    val eventDateTime = LocalDateTime.parse(event.date)
                    // Κρατάμε όσα είναι ΠΡΙΝ από τώρα
                    event.clubId == clubId && eventDateTime.isBefore(now)
                } catch (e: Exception) {
                    false
                }
            }
        )
    }


    override fun getEventsForUser(userId: String): Flow<List<Event>> {
        return flowOf(mockEvents.filter { it.attendingUserIds.contains(userId) })
    }

    override suspend fun getEventById(eventId: String): Event? {
        return mockEvents.find { it.eventId == eventId }
    }

    override suspend fun createEvent(event: Event): Result<Unit> {
        val newEvent = event.copy(eventId = (mockEvents.size + 1).toString())
        mockEvents.add(newEvent)
        if(mockEvents.contains(newEvent)){
            return Result.success(Unit)
        }
        return Result.failure(Exception("Failed to create event"))
    }

    override suspend fun updateEvent(event: Event): Result<Unit> {
        val index = mockEvents.indexOfFirst { it.eventId == event.eventId }
        if (index != -1) {
            mockEvents[index] = event
            return Result.success(Unit)
        }
        return Result.failure(Exception("Event not found"))
    }

    override suspend fun deleteEvent(eventId: String): Result<Unit> {
        val index = mockEvents.indexOfFirst { it.eventId == eventId }
        if (index != -1) {
            mockEvents.removeAt(index)
            return Result.success(Unit)
        }
        return Result.failure(Exception("Event not found"))
    }

    override suspend fun toggleInterest(
        eventId: String,
        userId: String,
        isInterested: Boolean
    ): Result<Unit> {

        val index = mockEvents.indexOfFirst { it.eventId == eventId }

        if (index != -1) {
            val currentEvent = mockEvents[index]
            val currentList = currentEvent.attendingUserIds


            val newList = if (isInterested) {

                if (!currentList.contains(userId)) currentList + userId else currentList
            } else {

                currentList - userId
            }


            val newEvent = currentEvent.copy(attendingUserIds = newList)
            mockEvents[index] = newEvent

            return Result.success(Unit)
        }

        return Result.failure(Exception("Event not found"))
    }

    override fun getInterestedUsers(eventId: String): Flow<List<String>> {
        val event = mockEvents.find { it.eventId == eventId }
        return flowOf(event?.attendingUserIds ?: emptyList())

    }
}