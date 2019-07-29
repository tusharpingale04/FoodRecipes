package com.tushar.foodrecipesmvvm.viewmodel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.tushar.foodrecipesmvvm.model.Recipes
import com.tushar.foodrecipesmvvm.model.RecipesRepository

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    private val recipesLiveData: LiveData<Recipes>
    private val recipesRepository: RecipesRepository = RecipesRepository()
    val isPerformingNetworkCall: ObservableBoolean = ObservableBoolean(false)

    init {
        recipesLiveData = recipesRepository.getRecipesLiveData()
    }

    fun getRecipesLiveData(): LiveData<Recipes> {
        return recipesLiveData
    }

    fun getRecipes(pageNo: String) {
        isPerformingNetworkCall.set(true)
        recipesRepository.getRecipes(pageNo)
    }

    fun searchNextPage() {
        if(!isPerformingNetworkCall.get()){
            isPerformingNetworkCall.set(true)
            recipesRepository.searchNextPage()
        }
    }

    fun setPerformingQuery(isPerformingNetworkCall: Boolean) {
        this.isPerformingNetworkCall.set(isPerformingNetworkCall)
    }

}