package bhadoria.abhinav.presentation.ui.pullrequestviewer.repodetailrequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bhadoria.abhinav.domain.model.*
import bhadoria.abhinav.domain.model.*
import bhadoria.abhinav.presentation.R
import bhadoria.abhinav.presentation.utils.toEditable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_fragment_repo_detail_request.*
import kotlinx.android.synthetic.main.layout_filters.*

class RepoDetailRequestFragment constructor(
        private val currentRepoDetailRequest: RepoDetailRequest,
        private val repoDetailRequestFragmentCallback: IRepoDetailRequestFragmentCallback?
) : BottomSheetDialogFragment() {

    companion object {
        val TAG: String = RepoDetailRequestFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_repo_detail_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textInputEditTextOwnerName.text = currentRepoDetailRequest.ownerName.toEditable()
        textInputEditTextRepoName.text = currentRepoDetailRequest.repoName.toEditable()

        radioGroupPullRequestState.check(
                when (currentRepoDetailRequest.filter.state) {
                    PullRequestState.ALL -> radioButtonStateAll.id
                    PullRequestState.OPEN -> radioButtonStateOpen.id
                    PullRequestState.CLOSED -> radioButtonStateClosed.id
                }
        )

        radioGroupSort.check(
                when (currentRepoDetailRequest.filter.sort) {
                    Sort.CREATED -> radioButtonSortCreated.id
                    Sort.UPDATED -> radioButtonSortUpdated.id
                    Sort.POPULARITY -> radioButtonSortPopularity.id
                }
        )

        radioGroupDirection.check(
                when (currentRepoDetailRequest.filter.direction) {
                    Direction.ASC -> radioButtonDirectionAsc.id
                    Direction.DESC -> radioButtonDirectionDesc.id
                }
        )


        materialButtonSubmitDetails.setOnClickListener {
            repoDetailRequestFragmentCallback?.onSubmit(
                    RepoDetailRequest(
                            textInputEditTextOwnerName.text.toString().trim(),
                            textInputEditTextRepoName.text.toString().trim())
                            .let { repoDetailRequest ->
                                repoDetailRequest.filter = getFilterFromView()
                                repoDetailRequest
                            }
            )

            this@RepoDetailRequestFragment.dismiss()
        }

        materialButtonCancel.setOnClickListener {
            this@RepoDetailRequestFragment.dismiss()
        }
    }

    private fun getFilterFromView(): Filter {
        val pullRequestState: PullRequestState =
                when (radioGroupPullRequestState.checkedRadioButtonId) {
                    radioButtonStateAll.id -> PullRequestState.ALL
                    radioButtonStateOpen.id -> PullRequestState.OPEN
                    radioButtonStateClosed.id -> PullRequestState.CLOSED
                    else -> PullRequestState.OPEN
                }

        val sort = when (radioGroupSort.checkedRadioButtonId) {
            radioButtonSortCreated.id -> Sort.CREATED
            radioButtonSortUpdated.id -> Sort.UPDATED
            radioButtonSortPopularity.id -> Sort.POPULARITY
            else -> Sort.UPDATED
        }

        val direction = when (radioGroupDirection.checkedRadioButtonId) {
            radioButtonDirectionAsc.id -> Direction.ASC
            radioButtonDirectionDesc.id -> Direction.DESC
            else -> Direction.DESC
        }

        return Filter(pullRequestState, sort, direction)
    }

}

interface IRepoDetailRequestFragmentCallback {

    fun onSubmit(repoDetailRequest: RepoDetailRequest)
}