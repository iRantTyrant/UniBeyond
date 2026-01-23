package com.example.unibeyond.domain.repository

import com.example.unibeyond.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    //--Read Operations--

    //Get the user data in flow so we can recompose each time something changes
    fun getUser(userId: String): Flow<User?>

    //--Write Operations--

    //Create user we use the result<unit> to be sure the creation of the user was successful or not
    suspend fun createUser(user: User): Result<Unit>
    //Update user we use the result<unit> to be sure the update of the user was successful or not
    suspend fun updateUser(user: User): Result<Unit>

    //--Gamification & Social Aspect--

    //We can add xp to a user if he goes to an event this automatically updates the Level
    suspend fun addXp(userId: String, amount: Int): Result<Unit>

    //Add friend
    suspend fun addFriend(userId: String, friendId: String): Result<Unit>

    //Remove friend
    suspend fun removeFriend(userId: String, friendId: String): Result<Unit>





}