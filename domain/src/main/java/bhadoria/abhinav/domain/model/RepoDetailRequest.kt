package bhadoria.abhinav.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore

@Entity
data class RepoDetailRequest(
        @ColumnInfo(name = "owner_name")
        val ownerName: String,
        @ColumnInfo(name = "repo_name")
        val repoName: String
) {
    @Ignore
    var filter: Filter = Filter()
}

data class Filter(
        val state: PullRequestState = PullRequestState.OPEN,
        val sort: Sort = Sort.CREATED,
        val direction: Direction = Direction.DESC
)

enum class Sort(val sortValue: String) {
    CREATED("created"),
    UPDATED("updated"),
    POPULARITY("popularity"),
}

enum class PullRequestState(val stateValue: String) {
    OPEN("open"),
    CLOSED("closed"),
    ALL("all")
}

enum class Direction(val directionValue: String) {
    ASC("asc"),
    DESC("desc")
}