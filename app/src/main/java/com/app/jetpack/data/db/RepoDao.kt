package com.app.jetpack.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.app.jetpack.data.model.Repos
import io.reactivex.Maybe

/**
 * Interface for database access on Repo related operations.
 */
@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repos>)

    @Query("SELECT * FROM repos WHERE owner_login = :ownerLogin")
    abstract fun load(ownerLogin: String): Maybe<List<Repos>>

}
