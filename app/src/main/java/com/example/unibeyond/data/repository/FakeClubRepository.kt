package com.example.unibeyond.data.repository

import javax.inject.Inject
import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.domain.model.Expense
import com.example.unibeyond.domain.repository.ClubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.collections.plus

class FakeClubRepository @Inject constructor() : ClubRepository {
    //Mock data in mutable list
    private val mockClubs = mutableListOf<Club>(
        //--- Mock Club 1: Photography Club ---
        Club(
            id = "c1",
            name = "Photography Club",
            description = "A club for photography enthusiasts.",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdMLSwaa1LPeVZjk49Kj7QyXUYAyASA39wSQ&s",
            category = "Electronics",
            ownerId = "u1",
            memberIds = listOf("u1"),
            pendingMemberIds = listOf("u3"),
            budgetTotal = 1000.0,
            budgetRemaining = 500.0,
            budgetSpent = 500.0,
            recentExpenses = listOf<Expense>(
                Expense(
                    id = "ex1",
                    title = "Photo Lights",
                    category = "Equipment",
                    amount = 200.0,
                    date = "2025-01-01"
                ),
                Expense(
                    id = "ex2",
                    title = "Photography Supplies",
                    category = "Expendables",
                    amount = 100.0,
                    date = "2025-02-01"
                )
            )
        ),
        Club(
            id = "c2",
            name = "Diving Club",
            description = "A club for diving enthusiasts.",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7JbPrl4ueSlVs1FJVxblox64x8q8Ob9jDWg&s",
            category = "Sports",
            ownerId = "u1",
            memberIds = listOf("u1","u2"),
            pendingMemberIds = listOf("u3"),
            budgetTotal = 2000.0,
            budgetRemaining = 1000.0,
            budgetSpent = 1000.0,
            recentExpenses = listOf<Expense>(
                Expense(
                    id = "ex1",
                    title = "Compressor Filter",
                    category = "Expendables",
                    amount = 300.0,
                    date = "2025-03-01"
                ),
                Expense(
                    id = "ex2",
                    title = "Diving Gear",
                    category = "Equipment",
                    amount = 200.0,
                    date = "2025-04-01"
                )
            )
        ),
        Club(
            id = "c3",
            name = "Cinema Club",
            description = "A club for movie enthusiasts.",
            imageUrl = "https://www.uni.lu/wp-content/uploads/sites/9/2025/02/Cinema_club_logo.png",
            category = "Entertainment",
            ownerId = "u3",
            memberIds = listOf("u3"),
            pendingMemberIds = listOf("u2","u1"),
            budgetTotal = 1500.0,
            budgetRemaining = 750.0,
            budgetSpent = 750.0,
            recentExpenses = listOf<Expense>(
                Expense(
                    id = "ex1",
                    title = "Movie Tickets",
                    amount = 500.0,
                    date = "2025-02-01",
                    category = "Entertainment"
                )
            )

        )
    )

    //--Read Operations (Data Streams--
    //Get all the created Clubs its a flow state because this should be constantly updated to show all available clubs
    override fun getClubs(): Flow<List<Club>> {
        return flowOf(mockClubs)
    }

    //Get the clubs that the user has joined
    override fun getClubsForUser(userId: String): Flow<List<Club>> {
        //Filter the clubs that contain the id of the user
        val userClubs = mockClubs.filter { it.memberIds.contains(userId) }
        return flowOf(userClubs)
    }

    //Search club by id if it doesn't find anything it returns null
    override suspend fun getClubById(clubId: String): Club? {
        return mockClubs.find {it.id == clubId}
    }

    //Search club by name if it doesn't find anything it returns null
    override suspend fun getClubByName(clubName: String): Club? {
        return mockClubs.find{it.name.equals(clubName, ignoreCase = true)}
    }


    //--Write Operations (Actions)--
    //Create a new club and append it to the list of clubs , i use auto increment to make sure no duplicate id's exist
    override suspend fun createClub(club: Club): Result<Unit> {
        val newClub = club.copy(id = (mockClubs.size + 1).toString())
        mockClubs.add(newClub)
        return Result.success(Unit)
    }

    //Update club info find the index through the id and replace the old object with the new
    override suspend fun updateClub(club: Club): Result<Unit> {
        return updateClubState(club.id) { oldClub ->
            oldClub.copy(
                name = club.name,
                description = club.description,
                imageUrl = club.imageUrl,
                category = club.category,
                ownerId = club.ownerId,)
        }
    }

    //Delete a club find the index through the id and remove it from the list
    override suspend fun deleteClub(clubId: String): Result<Unit> {
        val index = mockClubs.indexOfFirst { it.id == clubId }
        if (index != -1) {
            mockClubs.removeAt(index)
            return Result.success(Unit)
        }
        return Result.failure(Exception("Club not found"))
    }

    //Update economics
    override suspend fun addExpense(
        clubId: String,
        expense: Expense
    ): Result<Unit> {
        return updateClubState(clubId) { club ->
        club.copy(
            recentExpenses = club.recentExpenses + expense,
            budgetSpent = club.budgetSpent + expense.amount,
            budgetRemaining = club.budgetRemaining - expense.amount
        )
        }
    }

    //Update budget
    override suspend fun updateBudget(
        clubId: String,
        budget: Double): Result<Unit> {
        return updateClubState(clubId) { club ->
            club.copy(
                budgetTotal = budget,
                budgetRemaining = budget,
                budgetSpent = 0.0
            )
        }
    }

    //Add budget
    override suspend fun addBudget(
        clubId: String,
        amount: Double
    ): Result<Unit> {
        return updateClubState(clubId) { club ->
            club.copy(
                budgetTotal = club.budgetTotal + amount,
                budgetRemaining = club.budgetRemaining + amount
            )
        }
    }

    //--Member Management Operations--
    //An id requests to join the club and is added to the pending list
    override suspend fun joinClubRequest(
        clubId: String,
        userId: String
    ): Result<Unit> {
       return updateClubState(clubId) { club ->
           club.copy(
               pendingMemberIds = club.pendingMemberIds + userId
           )
       }
    }

    //An id leaves the club and removes them from the member list
    override suspend fun leaveClub(
        clubId: String,
        userId: String
    ): Result<Unit> {
        return updateClubState(clubId) { club ->
            club.copy(
                memberIds = club.memberIds - userId
            )
        }
    }

    //Accept an id to the club and remove them from the pending list
    override suspend fun acceptMember(
        clubId: String,
        userId: String
    ): Result<Unit> {
        return updateClubState(clubId) { club ->
           club.copy(
               pendingMemberIds = club.pendingMemberIds - userId,
               memberIds = club.memberIds + userId
           )
        }
    }

    //Decline an id to the club and remove them from the pending list
    override suspend fun declineMember(
        clubId: String,
        userId: String
    ): Result<Unit> {
        return updateClubState(clubId) { club ->
            club.copy(
                pendingMemberIds = club.pendingMemberIds - userId
            )
        }
    }

    //Remove an id from the list of members
    override suspend fun removeMember(
        clubId: String,
        userId: String
    ): Result<Unit> {
        return updateClubState(clubId) { club ->
            club.copy(
                memberIds = club.memberIds - userId
            )
        }
    }

    private fun updateClubState(clubId:String, transform: (Club) -> Club): Result<Unit>{
        val index = mockClubs.indexOfFirst { it.id == clubId }

        if(index != -1) {
            val currentClub = mockClubs[index]
            val newClub = transform(currentClub)
            mockClubs[index] = newClub
            return Result.success(Unit)
        }
        return Result.failure(Exception("Club not found"))
    }
}