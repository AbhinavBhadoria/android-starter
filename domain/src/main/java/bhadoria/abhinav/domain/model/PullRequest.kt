package bhadoria.abhinav.domain.model

import androidx.room.*
import org.joda.time.DateTime
import org.joda.time.Days

@Entity
data class PullRequest(
        @PrimaryKey
        @ColumnInfo(name = "pull_request_id")
        val id: Long,
        val title: String,
        val body: String?,
        val number: Long,
        val state: String,
        val locked: Boolean,
        val created_at: String,
        var isOfflineResult: Boolean = false,
        @Embedded()
        val user: User?
) {
    @Ignore
    val labels: List<Label>? = null

    fun getDaysSinceCreated(): String {
        val dt = DateTime(created_at)
        val currentDate = DateTime()

        return Days.daysBetween(dt, currentDate).days.toString()
    }
}

@Entity
data class User constructor(
        @PrimaryKey
        @ColumnInfo(name = "user_id")
        val id: Long,
        val login: String,
        val avatar_url: String
)

@Entity
data class Label constructor(
        @PrimaryKey
        @ColumnInfo(name = "label_id")
        val id: Long,
        val name: String,
        val color: String
)