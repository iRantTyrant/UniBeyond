package com.example.unibeyond.data.repository

import com.example.unibeyond.domain.model.User
import com.example.unibeyond.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeUserRepository @Inject constructor()  : UserRepository {
    //Mock  user data in list
    private val mockUsers = mutableListOf<User>(
        User(
            userId = "u1",
            fullName = "Thomas Vitas",
            email = "test@mail.com",
            studentId = "21168",
            major = "Computer Science",
            year = "Super Senior (5th)",
            profileImageUrl = "https://pulsar-radio.ece.uop.gr/wp-content/uploads/2024/05/vitas_profile.jpg",
            joinedClubIds = listOf("c2"),
            interestedEventsIds = listOf("ev1", "ev2"),
            friendsList = listOf("u2", "u3"),
            level = 1,
            xp = 100
            ),
        User(
            userId = "u2",
            fullName = "Fake Person",
            email = "test2@mail.com",
            studentId = "20151",
            major = "Mechanical Engineer",
            year = "Sophomore (2th)",
            profileImageUrl = "https://www.shutterstock.com/image-photo/young-adolescent-university-student-boy-600nw-2473697481.jpg",
            joinedClubIds = listOf("c1","c2"),
            interestedEventsIds = listOf("ev1", "ev3"),
            friendsList = listOf("u1","u3"),
            level = 0 ,
            xp = 50
        ),
        User(
            userId = "u3",
            fullName = "Fake Personnette",
            email = "test3@mail.com",
            studentId = "20152",
            major = "Civil Engineering",
            year = "Junior (3th)",
            profileImageUrl = "https://d39l2hkdp2esp1.cloudfront.net/img/photo/131089/131089_00_2x.jpg?20170904115044",
            joinedClubIds = listOf("c3"),
            interestedEventsIds = listOf("ev2"),
            friendsList = listOf("u1","u2"),
            level = 0,
            xp = 0
        )
    )

    //Get  user from the list as a flow to update constantly
    override fun getUser(userId: String): Flow<User?>{
        return flowOf(mockUsers.find { it.userId == userId })
    }

    //Create user and give him an auto increment id
    override suspend fun createUser(user: User): Result<Unit> {
        val newUser = user.copy(userId = (mockUsers.size + 1).toString())
        if (mockUsers.add(newUser)){
            return Result.success(Unit)
        }
        return Result.failure(Exception("Failed to create user"))
    }

    //Update the user and place him in the same position as the old user object
    override suspend fun updateUser(user: User): Result<Unit> {
        return updateUserState(user.userId) { currentUser ->
            currentUser.copy(
                fullName = user.fullName,
                email = user.email,
                studentId = user.studentId,
                major = user.major,
                year = user.year,
                profileImageUrl = user.profileImageUrl
            )
        }
    }

    //Simple algorithm to check current xp and make it into levels
    override suspend fun addXp(userId: String, amount: Int): Result<Unit> {
        return updateUserState(userId) { user ->
        val newXp = user.xp + amount

        val newLevel = 1 + (newXp/100)
        user.copy(
            xp = newXp,
            level = newLevel
        )
        }
    }


    //User follows a friend
    override suspend fun addFriend(
        userId: String,
        friendId: String
    ): Result<Unit> {
        return updateUserState(userId) { user ->
            user.copy(
                friendsList = user.friendsList + friendId
            )
        }
    }

    //User unfollows a friend
    override suspend fun removeFriend(
        userId: String,
        friendId: String
    ): Result<Unit> {
        return updateUserState(userId) { user ->
            user.copy(
                friendsList = user.friendsList - friendId)
            }
        }



    //Helper Function for updating state
    private fun updateUserState(userId: String, transform: (User) -> User): Result<Unit> {
    val index = mockUsers.indexOfFirst { it.userId == userId }
    if (index != -1){
        val currentUser = mockUsers[index]
        val newUser = transform(currentUser)
        mockUsers[index] = newUser
        return Result.success(Unit)
    }
    return Result.failure(Exception("User not found"))
    }
}