package bhadoria.abhinav.data.local.repository

import bhadoria.abhinav.data.local.room.CachedPullRequest
import bhadoria.abhinav.data.local.room.MeeshoRoomDatabase
import bhadoria.abhinav.domain.model.Filter
import bhadoria.abhinav.domain.model.PullRequest
import bhadoria.abhinav.domain.model.RepoDetailRequest
import bhadoria.abhinav.domain.repository.IGithubRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class GithubLocalRepository @Inject constructor(
        private val database: MeeshoRoomDatabase
) : IGithubRepository {
    override fun savePullRequests(repoDetailRequest: RepoDetailRequest, pullRequests: List<PullRequest>): Completable {
        return Completable.fromCallable {
            database.githubDao()
                    .insertPullRequests(pullRequests.map { CachedPullRequest(repoDetailRequest, it) })
        }
    }

    override fun getPullRequests(ownerName: String, repoName: String, page: Int, limit: Int, filter: Filter): Observable<List<PullRequest>> {
        return database
                .githubDao()
                .getRequestForOwnerAndRepoName(ownerName, repoName)
                .map {
                    it.reversed().map { cachedPullRequest ->
                        cachedPullRequest.pullRequest.isOfflineResult = true
                        cachedPullRequest.pullRequest
                    }
                }
    }
}