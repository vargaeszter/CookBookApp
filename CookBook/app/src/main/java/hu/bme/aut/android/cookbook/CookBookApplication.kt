package hu.bme.aut.android.cookbook

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.cookbook.data.conventers.RecipeDatabase
import hu.bme.aut.android.cookbook.data.repository.RecipeRepositoryImpl

class CookBookApplication : Application() {

    companion object {
        private lateinit var db: RecipeDatabase

        lateinit var repository: RecipeRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "todo_database"
        ).fallbackToDestructiveMigration().build()

        repository = RecipeRepositoryImpl(db.dao)
    }
}