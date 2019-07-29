package com.tushar.foodrecipesmvvm.di

import com.tushar.foodrecipesmvvm.model.RecipesRepository
import com.tushar.foodrecipesmvvm.remote.ApiInterface
import com.tushar.foodrecipesmvvm.remote.ServiceGenerator
import com.tushar.foodrecipesmvvm.viewmodel.RecipesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {

    single { ApiInterface() }
    single { ServiceGenerator() }
    single { RecipesRepository() }
    viewModel { RecipesViewModel(get()) }
}