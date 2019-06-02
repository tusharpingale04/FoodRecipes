package com.tushar.foodrecipesmvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tushar.foodrecipesmvvm.remote.ServiceGenerator
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RecipesRepository {

    private var mPageNo: Int = 0

    private var recipeLiveData: MutableLiveData<Recipes> = MutableLiveData()

    private val parentJob = Job()

    private val coRoutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coRoutineContext)

    fun getRecipesLiveData(): LiveData<Recipes> {
        return recipeLiveData
    }

    fun getRecipes(pageNo: String) {
        mPageNo = pageNo.toInt()
        scope.launch {
            val response = ServiceGenerator.invoke().getRecipesAsync(mPageNo.toString()).await()
            recipeLiveData.postValue(response)
        }
    }

    fun searchNextPage() {
        getRecipes((mPageNo+1).toString())
    }

}