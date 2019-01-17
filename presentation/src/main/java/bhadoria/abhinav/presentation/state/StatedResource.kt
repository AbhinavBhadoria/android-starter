package bhadoria.abhinav.presentation.state

import bhadoria.abhinav.domain.model.RepoDetailRequest

sealed class StatedResource {
    class Success<out T>(val data: T) : StatedResource()
    class Error(val message: String?) : StatedResource()
    class Loadig(val repoDetailRequest: RepoDetailRequest) : StatedResource()
}
