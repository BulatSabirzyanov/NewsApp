package com.example.on_boarding.domain.repository

interface OnBoardingRepository {
    suspend fun saveOnBoardingState(state: Boolean)

}
