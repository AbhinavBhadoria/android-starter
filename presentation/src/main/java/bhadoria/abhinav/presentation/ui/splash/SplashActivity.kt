package bhadoria.abhinav.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import bhadoria.abhinav.presentation.injection.ViewModelFactory
import bhadoria.abhinav.presentation.ui.pullrequestviewer.PullRequestViewerActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        splashViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SplashViewModel::class.java)

        splashViewModel.getSplashCompletedLiveData().observe(this,
                Observer {
                    startActivity(Intent(this, PullRequestViewerActivity::class.java))
                    finish()
                })
    }
}