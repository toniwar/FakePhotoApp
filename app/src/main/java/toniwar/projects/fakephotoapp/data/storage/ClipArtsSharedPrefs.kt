package toniwar.projects.fakephotoapp.data.storage

import android.content.Context
import android.content.SharedPreferences
import toniwar.projects.fakephotoapp.Constants
import javax.inject.Inject


class ClipArtsSharedPrefs @Inject constructor(context: Context) {

    private val sharedPreferences = context.applicationContext.getSharedPreferences(Constants.USER_SHARED_PREFS, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun writeInt(name: String,data: Int){
        editor.putInt(name, data).apply()

    }

    fun readInt(name: String): Int{
        return sharedPreferences.getInt(name, 0)
    }

}
