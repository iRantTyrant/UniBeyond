package com.example.unibeyond.di

import com.example.unibeyond.data.repository.FakeAuthRepository
import com.example.unibeyond.data.repository.FakeClubRepository
import com.example.unibeyond.data.repository.FakeEventRepository
import com.example.unibeyond.data.repository.FakeUserRepository
import com.example.unibeyond.domain.repository.AuthRepository
import com.example.unibeyond.domain.repository.ClubRepository
import com.example.unibeyond.domain.repository.EventRepository
import com.example.unibeyond.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    //Binds FakeUserRepository to the interface UserRepository
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        fakeUserRepository: FakeUserRepository
    ): UserRepository

    //Binds FakeEventRepository to the interface EventRepository
    @Binds
    @Singleton
    abstract fun bindEventRepository(
        fakeEventRepository: FakeEventRepository
    ): EventRepository

    //Binds FakeClubRepository to the interface ClubRepository
    @Binds
    @Singleton
    abstract fun bindClubRepository(
        fakeClubRepository: FakeClubRepository
    ): ClubRepository

    //Binds FakeUserRepository to the interface UserRepository
    @Binds
    @Singleton
    abstract fun bindsAuthRepository(
        fakeAuthRepository: FakeAuthRepository
    ): AuthRepository
}

