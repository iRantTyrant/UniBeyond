package com.example.unibeyond.domain.repository

import com.example.unibeyond.domain.model.Club
import com.example.unibeyond.domain.model.Expense
import kotlinx.coroutines.flow.Flow


interface ClubRepository {

    //--Read Operations (Data Streams)--

    // Get all the created Clubs its a flow state because this should be constantly updated to show all available clubs
    fun getClubs(): Flow<List<Club>>

    //Get the clubs that the user has joined
    fun getClubsForUser(userId: String): Flow<List<Club>>


    //Get a club referenced by club id its only used once and returns null if it doesn't find anything
    suspend fun getClubById(clubId: String): Club?

    //Get a club referenced by club name its only used once and returns null if it doesn't find anything
    suspend fun getClubByName(clubName: String): Club?

    //--Write Operations (Actions)--

    //Create a new club (we use result<unit> to be sure the creation of the club was successful or not
    suspend fun createClub(club: Club): Result<Unit>

    //Update club info
    suspend fun updateClub(club : Club):Result<Unit>

    //Delete a club
    suspend fun deleteClub(clubId: String):Result<Unit>

    //Update Expenses
    suspend fun addExpense(clubId: String, expense : Expense): Result<Unit>

    //Update budget
    suspend fun updateBudget(clubId: String, budget: Double): Result<Unit>

    //Add budget
    suspend fun addBudget(clubId: String, amount: Double): Result<Unit>


    //--Member Management Operations-
    //User asks to join a club
    suspend fun joinClubRequest(clubId: String, userId: String): Result<Unit>

    //User leaves a club
    suspend fun leaveClub(clubId:String, userId: String): Result<Unit>

    //Accept a join request
    suspend fun acceptMember(clubId: String, userId: String): Result<Unit>

    //Decline a join request
    suspend fun declineMember(clubId: String, userId: String): Result<Unit>

    //Remove a member from the club
    suspend fun removeMember(clubId: String, userId: String): Result<Unit>

}