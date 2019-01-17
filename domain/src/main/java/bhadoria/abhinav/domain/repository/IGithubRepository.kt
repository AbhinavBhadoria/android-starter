package bhadoria.abhinav.domain.repository

import bhadoria.abhinav.domain.model.Filter
import bhadoria.abhinav.domain.model.PullRequest
import bhadoria.abhinav.domain.model.RepoDetailRequest
import io.reactivex.Completable
import io.reactivex.Observable

interface IGithubRepository {

    fun getPullRequests(ownerName: String, repoName: String,
                        page: Int, limit: Int,
                        filter: Filter)
            : Observable<List<PullRequest>>

    fun savePullRequests(repoDetailRequest: RepoDetailRequest,
                         pullRequests: List<PullRequest>)
            : Completable
}