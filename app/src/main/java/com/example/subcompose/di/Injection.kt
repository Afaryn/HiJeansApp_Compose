package com.example.subcompose.di

import com.example.subcompose.data.MenuRepository

object Injection {
    fun provideRepository():MenuRepository{
        return MenuRepository.getInstance()
    }
}