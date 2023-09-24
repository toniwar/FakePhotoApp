package toniwar.projects.fakephotoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import toniwar.projects.fakephotoapp.Constants


@Database(entities = [ClipArtDBModel::class], version = 1)
abstract class  AppDataBase: RoomDatabase() {

    abstract fun clipArtsDao(): ClipArtsDAO

    companion object AppDataBaseInstance{

        private var INSTANCE: AppDataBase? = null

        private val LOCK = Any()

        fun getInstance(context: Context): AppDataBase{
            INSTANCE?.let {
                return it
            }

            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
            }

            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                Constants.CLIP_ARTS_DB_NAME
            ).build()
            INSTANCE = db
            return db
        }
    }

}
