package bhadoria.abhinav.presentation.ui.pullrequestviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bhadoria.abhinav.data.local.sharedpref.IPreferenceStorage
import bhadoria.abhinav.domain.interactor.pullrequest.GetPullRequestsUseCase
import bhadoria.abhinav.domain.model.PullRequest
import bhadoria.abhinav.domain.model.RepoDetailRequest
import bhadoria.abhinav.presentation.state.StatedResource
import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import javax.inject.Inject

class PullRequestViewerViewModel @Inject constructor(
        private val getPullRequestsUseCase: GetPullRequestsUseCase,
        private val preferenceStorage: IPreferenceStorage
) : ViewModel() {

    private val pullRequestLiveData: MutableLiveData<StatedResource> = MutableLiveData()

    private var repoDetailRequest: RepoDetailRequest = preferenceStorage.lastRepoDetailRequested

    init {
        fetchPullRequests(repoDetailRequest)
    }

    override fun onCleared() {
        super.onCleared()

        getPullRequestsUseCase.dispose()
    }

    fun getPullRequestLiveData(): LiveData<StatedResource> {
        return pullRequestLiveData
    }

    fun fetchPullRequests(repoDetailRequest: RepoDetailRequest) {
        this.repoDetailRequest = repoDetailRequest

        preferenceStorage.lastRepoDetailRequested = repoDetailRequest

        pullRequestLiveData.postValue(StatedResource.Loadig(repoDetailRequest))

        getPullRequestsUseCase.execute(GetPullRequestObserver(),
                GetPullRequestsUseCase.Params(repoDetailRequest, 1, 30))
    }

    fun getCurrentRepoDetailRequest(): RepoDetailRequest {
        return repoDetailRequest
    }

    inner class GetPullRequestObserver : DisposableObserver<List<PullRequest>>() {
        override fun onComplete() {
            Timber.d("onComplete: GetPullRequestObserver")
        }

        override fun onNext(t: List<PullRequest>) {
            Timber.d("onNext: GetPullRequestObserver: %s", t.toString())

            pullRequestLiveData.postValue(StatedResource.Success(t))
        }

        override fun onError(e: Throwable) {
            Timber.d(e)

            pullRequestLiveData.postValue(StatedResource.Error(e.localizedMessage))
        }
    }
}