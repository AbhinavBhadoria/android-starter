package bhadoria.abhinav.data.local.sharedpref

import android.content.Context
import bhadoria.abhinav.domain.model.RepoDetailRequest
import com.google.gson.Gson
import javax.inject.Inject


interface IPreferenceStorage {
    var lastRepoDetailRequested: RepoDetailRequest
}

class SharedIPreferenceStorage @Inject constructor(context: Context,
                                                   private val gson: Gson) :
        IPreferenceStorage {
    override var lastRepoDetailRequested: RepoDetailRequest
        get() {
            val lastRepoDetailStr: String? = prefs.getString(PREF_LAST_REPO_DETAIL_REQUESTED, gson
                    .toJson(RepoDetailRequest("nickbutcher", "plaid")))
            return gson.fromJson(lastRepoDetailStr, RepoDetailRequest::class.java) as RepoDetailRequest
        }
        set(value) {
            prefs.edit().putString(PREF_LAST_REPO_DETAIL_REQUESTED, gson.toJson(value)).apply()
        }

    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREFS_NAME = "meesho.prefs"
        const val PREF_LAST_REPO_DETAIL_REQUESTED = "pref_last_repo_detail_requested"
    }
}
