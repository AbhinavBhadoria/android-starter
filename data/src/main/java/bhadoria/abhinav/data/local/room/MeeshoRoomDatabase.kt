package bhadoria.abhinav.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CachedPullRequest::class],
        version = 1)
abstract class MeeshoRoomDatabase : RoomDatabase() {

    abstract fun githubDao(): GithubDao

    companion object {
        fun getInstance(context: Context): MeeshoRoomDatabase {
            return Room.databaseBuilder(context.applicationContext,
                    MeeshoRoomDatabase::class.java, "meesho.db")
                    .build()
        }
    }
}