package bhadoria.abhinav.presentation.ui.pullrequestviewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bhadoria.abhinav.domain.model.PullRequest
import bhadoria.abhinav.presentation.databinding.ItemPullRequestBinding

class PullRequestAdapter : ListAdapter<PullRequest, RecyclerView.ViewHolder>(PullRequestDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPullRequestBinding.inflate(inflater)
        return PullRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PullRequestViewHolder).run {
            bind(getItem(position))
        }
    }

    inner class PullRequestViewHolder constructor(val binding: ItemPullRequestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PullRequest) {
            binding.pullRequest = item
            binding.executePendingBindings()
        }
    }
}

class PullRequestDiffer : DiffUtil.ItemCallback<PullRequest?>() {

    override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
        return oldItem == newItem
    }
}