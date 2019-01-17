package bhadoria.abhinav.presentation.ui.pullrequestviewer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bhadoria.abhinav.domain.model.PullRequest
import bhadoria.abhinav.domain.model.RepoDetailRequest
import bhadoria.abhinav.presentation.R
import bhadoria.abhinav.presentation.injection.ViewModelFactory
import bhadoria.abhinav.presentation.state.StatedResource
import bhadoria.abhinav.presentation.ui.pullrequestviewer.repodetailrequest.IRepoDetailRequestFragmentCallback
import bhadoria.abhinav.presentation.ui.pullrequestviewer.repodetailrequest.RepoDetailRequestFragment
import bhadoria.abhinav.presentation.utils.inTransaction
import dagger.android.AndroidInjection
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_pull_request_viewer.*
import timber.log.Timber
import javax.inject.Inject


class PullRequestViewerActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_ID = R.id.fragmentContainer
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var pullRequestViewerViewModel: PullRequestViewerViewModel

    private val pullRequestAdapter: PullRequestAdapter by lazy {
        PullRequestAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request_viewer)
        AndroidInjection.inject(this)

        pullRequestViewerViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PullRequestViewerViewModel::class.java)

        pullRequestViewerViewModel.getPullRequestLiveData().observe(this,
                Observer<StatedResource> {
                    it?.let { handleDataState(it) }
                })

        supportActionBar?.title = pullRequestViewerViewModel
                .getCurrentRepoDetailRequest()
                .let {
                    it.ownerName + ":" + it.repoName
                }

        setupPullRequestRecyclerView()
        setupRepoDetailRequestFab()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pull_request_viewer, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_open_zerologicgames -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://smarturl.it/zerologicgames")))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    private fun setupRepoDetailRequestFab() {
        fabRequestRepoDetails.setOnClickListener {
            RepoDetailRequestFragment(pullRequestViewerViewModel.getCurrentRepoDetailRequest(), repoDetailRequestFragmentCallback).show(supportFragmentManager, RepoDetailRequestFragment.TAG)
        }
    }

    private fun setupPullRequestRecyclerView() {
        recyclerViewPullRequests.layoutManager = LinearLayoutManager(this)
        recyclerViewPullRequests.adapter = pullRequestAdapter
        recyclerViewPullRequests.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun handleDataState(statedResource: StatedResource) {
        when (statedResource) {
            is StatedResource.Success<*> -> {
                Timber.d("Success")

                progressLoadingState.visibility = GONE
                recyclerViewPullRequests.visibility = VISIBLE
                textErrorState.visibility = GONE

                pullRequestAdapter.submitList(statedResource.data as List<PullRequest>)
            }
            is StatedResource.Loadig -> {
                Timber.d("Loading")

                progressLoadingState.visibility = VISIBLE
                recyclerViewPullRequests.visibility = GONE
                textErrorState.visibility = GONE
            }
            is StatedResource.Error -> {
                Timber.d("Error: %s", statedResource.message)

                progressLoadingState.visibility = GONE
                recyclerViewPullRequests.visibility = GONE
                textErrorState.visibility = VISIBLE
            }
        }
    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment {
        supportFragmentManager.inTransaction {
            replace(FRAGMENT_ID, fragment)
        }
    }


    private val repoDetailRequestFragmentCallback: IRepoDetailRequestFragmentCallback = object : IRepoDetailRequestFragmentCallback {
        override fun onSubmit(repoDetailRequest: RepoDetailRequest) {
            pullRequestViewerViewModel.fetchPullRequests(repoDetailRequest)
        }
    }
}