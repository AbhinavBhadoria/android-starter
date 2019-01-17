package bhadoria.abhinav.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import bhadoria.abhinav.domain.model.PullRequest
import bhadoria.abhinav.domain.model.RepoDetailRequest

@Entity(tableName = PullRequestConstants.TABLE_NAME)
data class CachedPullRequest(
        @Embedded
        val repoDetailRequest: RepoDetailRequest,
        @Embedded
        val pullRequest: PullRequest
) {
    @PrimaryKey()
    @ColumnInfo(name = PullRequestConstants.COLUMN_PULL_CACHE_ID)
    var id: Long = pullRequest.id
}