package com.tushar.foodrecipesmvvm.ui

import android.app.PendingIntent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tushar.foodrecipesmvvm.R
import com.tushar.foodrecipesmvvm.adapter.RecipesAdapter
import com.tushar.foodrecipesmvvm.databinding.ActivityMainBinding
import com.tushar.foodrecipesmvvm.model.Recipe
import com.tushar.foodrecipesmvvm.viewmodel.RecipesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), RecipesAdapter.OnRecipeSelectedListener {


    override fun onClickRecipe(recipeUrl: String) {

        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        builder.addDefaultShareMenuItem()

        val anotherCustomTab = CustomTabsIntent.Builder().build()

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_android)
        val requestCode = 100
        val intent = anotherCustomTab.intent
        intent.data = Uri.parse(recipeUrl)

        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setActionButton(bitmap, "Android", pendingIntent, true)
        builder.setShowTitle(true)


        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(recipeUrl))

    }

    val recipesViewModel: RecipesViewModel by viewModel()
    private var recipes: MutableList<Recipe> = ArrayList()
    private lateinit var recipesAdapter: RecipesAdapter
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.viewmodel = recipesViewModel

        recipesViewModel.getRecipes("1")
        recipesAdapter = RecipesAdapter(recipes, this)
        val itemDecorator = DividerItemDecoration(this, RecyclerView.VERTICAL)

        recipesRecyclerView.addItemDecoration(itemDecorator)
        recipesRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recipesRecyclerView.adapter = recipesAdapter

        recipesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (!recipesRecyclerView.canScrollVertically(1)) {
                    // search the next page
                    recipesViewModel.searchNextPage()
                }
            }
        })

        recipesViewModel.getRecipesLiveData().observe(this, Observer { recipes ->
            if (recipes == null) return@Observer
            this.recipes.addAll(recipes.recipes)
            recipesAdapter.updateData(this.recipes)
            recipesViewModel.setPerformingQuery(false)
        })
    }
}
