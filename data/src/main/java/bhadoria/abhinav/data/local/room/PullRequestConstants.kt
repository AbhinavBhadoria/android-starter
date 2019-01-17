package bhadoria.abhinav.data.local.room

object PullRequestConstants {

    const val TABLE_NAME = "pull_requests"

    const val COLUMN_PULL_REQUEST_ID = "pull_request_id"
    const val COLUMN_PULL_USER_ID = "user_id"
    const val COLUMN_PULL_LABEL_ID = "label_id"
    const val COLUMN_PULL_CACHE_ID = "cached_pull_request_id"

    const val COLUMN_REPO_NAME = "repo_name"
    const val COLUMN_OWNER_NAME = "owner_name"

    const val QUERY_ALL_VENUES = "SELECT * FROM $TABLE_NAME"

    const val QUERY_VENUES_BY_OWNER_REPO = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_OWNER_NAME = :ownerName AND $COLUMN_REPO_NAME = :repoName"
}