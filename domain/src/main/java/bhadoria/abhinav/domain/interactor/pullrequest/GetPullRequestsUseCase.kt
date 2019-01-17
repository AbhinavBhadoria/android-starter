package bhadoria.abhinav.domain.interactor.pullrequest

import bhadoria.abhinav.domain.exception.NullParamsException
import bhadoria.abhinav.domain.interactor.base.ObservableUseCase
import bhadoria.abhinav.domain.model.PullRequest
import bhadoria.abhinav.domain.model.RepoDetailRequest
import bhadoria.abhinav.domain.repository.factory.IGithubRepositoryFactory
import io.reactivex.Observable
import javax.inject.Inject

open class GetPullRequestsUseCase @Inject constructor(
        private val githubRepositoryFactory: IGithubRepositoryFactory
) : ObservableUseCase<List<PullRequest>, GetPullRequestsUseCase.Params>() {

    public override fun buildUseCaseObservable(params: Params?): Observable<List<PullRequest>> {
        if (params == null) throw NullParamsException()

        return githubRepositoryFactory.getRepository().getPullRequests(params.repoDetailRequest.ownerName, params.repoDetailRequest.repoName,
                params.page, params.limit, params.repoDetailRequest.filter)
                .flatMap { pullRequests: List<PullRequest>  ->
                    githubRepositoryFactory.getLocal()
                            .savePullRequests(params.repoDetailRequest, pullRequests)
                            .andThen(Observable.just(pullRequests))
                }
    }

    data class Params constructor(val repoDetailRequest: RepoDetailRequest,
                                  val page: Int, val limit: Int)
}
