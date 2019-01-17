package bhadoria.abhinav.data.factory

import android.net.ConnectivityManager
import bhadoria.abhinav.data.local.repository.GithubLocalRepository
import bhadoria.abhinav.data.remote.repository.GithubRemoteRepository
import bhadoria.abhinav.domain.repository.IGithubRepository
import bhadoria.abhinav.domain.repository.factory.IGithubRepositoryFactory
import javax.inject.Inject

class GithubRepositoryFactory @Inject constructor(
        private val githubRemoteRepository: GithubRemoteRepository,
        private val githubLocalRepository: GithubLocalRepository,
        private val connectivityManager: ConnectivityManager
) : IGithubRepositoryFactory {

    override fun getRepository(): IGithubRepository {
        return if (connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo.isConnected)
            getRemote()
        else
            getLocal()
    }

    override fun getRemote(): IGithubRepository {
        return githubRemoteRepository
    }

    override fun getLocal(): IGithubRepository {
        return githubLocalRepository
    }
}