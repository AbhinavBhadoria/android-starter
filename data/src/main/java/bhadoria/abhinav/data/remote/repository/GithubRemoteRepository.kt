package bhadoria.abhinav.data.remote.repository

import bhadoria.abhinav.data.remote.retrofit.service.GithubService
import bhadoria.abhinav.domain.model.Filter
import bhadoria.abhinav.domain.model.PullRequest
import bhadoria.abhinav.domain.model.RepoDetailRequest
import bhadoria.abhinav.domain.repository.IGithubRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class GithubRemoteRepository @Inject constructor(
        private val githubService: GithubService
) : IGithubRepository {
    override fun savePullRequests(repoDetailRequest: RepoDetailRequest, pullRequests: List<PullRequest>): Completable {
        throw UnsupportedOperationException("Save Pull Request is an offline operation")
    }

    override fun getPullRequests(ownerName: String, repoName: String, page: Int, limit: Int, filter: Filter): Observable<List<PullRequest>> {
        return githubService.getPullRequests(ownerName, repoName, 1, 100, filter.state.stateValue, filter.sort.sortValue, filter.direction.directionValue)
    }
}