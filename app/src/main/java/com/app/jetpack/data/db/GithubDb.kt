package com.app.jetpack.data.db


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.app.jetpack.data.model.Repos

/**
 * Main database description.
 */
@Database(
        entities = [Repos::class],
        version = 1,
        exportSchema = false
)
abstract class GithubDb : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}
