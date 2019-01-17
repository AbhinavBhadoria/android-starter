package bhadoria.abhinav.data.remote.retrofit.service

import bhadoria.abhinav.domain.model.PullRequest
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("repos/{ownerName}/{repoName}/pulls")
    fun getPullRequests(@Path("ownerName") ownerName: String,
                        @Path("repoName") repoName: String,
                        @Query("page") page: Int,
                        @Query("limit") limit: Int,
                        @Query("state") state: String,
                        @Query("sort") sort: String,
                        @Query("direction") direction: String)
            : Observable<List<PullRequest>>
}