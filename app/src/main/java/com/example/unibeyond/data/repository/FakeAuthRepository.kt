package com.example.unibeyond.data.repository

import com.example.unibeyond.domain.model.User
import com.example.unibeyond.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay
import java.util.UUID

@Singleton
class FakeAuthRepository @Inject constructor() : AuthRepository {

    private data class AuthenticatedUser(
        val email: String,
        val password: String,
        val user: User
    )


    private val authUsers = mutableListOf(
        AuthenticatedUser(
            email = "irant@mail.com",
            password = "123",
            user = User(
                userId = "u1",
                email = "student@uni.edu",
                fullName = "Thomas Vitas",
                major = "Computer Science",
                year = "3rd Year"
            )
        ),
        AuthenticatedUser(
            email = "maria@uni.edu",
            password = "456",
            user = User(
                userId = "u2",
                email = "maria@uni.edu",
                fullName = "Maria Papadopoulou",
                major = "Physics",
                year = "1st Year"
            )
        )
    )

    // --- LOGIN ---
    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(1500) // latency

        // serach the list
        val match = authUsers.find { it.email == email && it.password == password }

        return if (match != null) {
            println("Login Success: ${match.user.fullName}")
            Result.success(Unit)
        } else {
            println("Login Failed: Wrong credentials")
            Result.failure(Exception("Invalid email or password"))
        }
    }

    // --- REGISTER ---
    override suspend fun register(
        email: String,
        password: String,
        fullName: String,
        major: String,
        year: String
    ): Result<Unit> {
        delay(2000) // Delay

        // Check if email already exits
        if (authUsers.any { it.email == email }) {
            return Result.failure(Exception("User already exists"))
        }

        // Create the user object with new id
        val newUser = User(
            userId = UUID.randomUUID().toString(), // random id
            email = email,
            fullName = fullName,
            major = major,
            year = year
        )

        // Creathe the authuser object
        val newAuthUser = AuthenticatedUser(
            email = email,
            password = password,
            user = newUser
        )

        // append it to the list
        authUsers.add(newAuthUser)

        println("New User Registered: ${newUser.fullName}")
        println("Total users in memory: ${authUsers.size}")

        return Result.success(Unit)
    }
}