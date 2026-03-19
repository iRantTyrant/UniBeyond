package com.example.unibeyond.domain.repository

import com.example.unibeyond.presentation.auth.register.RegisterUiState

interface AuthRepository {

    // Επιστρέφει Result<Unit>: Αν πέτυχε (Success) ή αν απέτυχε με λάθος (Failure)
    suspend fun login(email: String, password: String): Result<Unit>

    // Περνάμε όλο το state για ευκολία, ή τα πεδία ξεχωριστά
    suspend fun register(
        email: String,
        password: String,
        fullName: String,
        major: String,
        year: String
    ): Result<Unit>
}