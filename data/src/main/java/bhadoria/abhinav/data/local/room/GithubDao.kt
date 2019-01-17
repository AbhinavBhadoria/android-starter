package bhadoria.abhinav.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
abstract class GithubDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    abstract fun insertPullRequests(pullRequests: List<CachedPullRequest>)

    @Query(PullRequestConstants.QUERY_VENUES_BY_OWNER_REPO)
    @JvmSuppressWildcards
    abstract fun getRequestForOwnerAndRepoName(ownerName: String, repoName: String): Observable<List<CachedPullRequest>>
}