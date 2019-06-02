package com.tushar.foodrecipesmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tushar.foodrecipesmvvm.model.Recipe
import kotlinx.android.synthetic.main.row_recipe.view.*

import android.view.animation.AnimationUtils
import com.tushar.foodrecipesmvvm.R


class RecipesAdapter(private var recipes: List<Recipe>, onRecipeSelectedListener: OnRecipeSelectedListener) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    private var lastPosition: Int = -1
    private var listener : OnRecipeSelectedListener = onRecipeSelectedListener

    interface OnRecipeSelectedListener{
        fun onClickRecipe(recipeUrl:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(holder, recipes[position], listener)
        setAnimation(holder,position)
    }

    fun updateData(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    override fun onViewDetachedFromWindow(holder: RecipeViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }


    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            listener.onClickRecipe(recipe.f2f_url)
        }

        private val recipesImage = itemView.recipeImage
        private val recipeTitle = itemView.recipeName
        private val recipeDesc = itemView.recipeShortDesc
        private lateinit var recipe: Recipe
        private lateinit var listener:OnRecipeSelectedListener

        fun bind(holder: RecipeViewHolder, recipe: Recipe, listener: OnRecipeSelectedListener) {
            itemView.setOnClickListener(this)
            Glide.with(holder.itemView.context).load(recipe.image_url).into(recipesImage)
            recipeTitle.text = recipe.title
            recipeDesc.text = recipe.publisher
            this.recipe = recipe
            this.listener = listener
        }
    }

    private fun setAnimation(holder: RecipeViewHolder, position: Int) {
        val animation = AnimationUtils.loadAnimation(holder.itemView.context,
            if (position > lastPosition)
                R.anim.up_from_bottom
            else
                R.anim.down_from_top
        )
        holder.itemView.startAnimation(animation)
        lastPosition = position
    }
}