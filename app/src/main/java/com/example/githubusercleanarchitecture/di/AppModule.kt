package com.example.githubusercleanarchitecture.di

import com.example.core.domain.usecase.UserInteractor
import com.example.core.domain.usecase.UserUseCase
import com.example.githubusercleanarchitecture.MainViewModel
import com.example.githubusercleanarchitecture.presentation.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module{
    factory<UserUseCase> { UserInteractor(get())}
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}