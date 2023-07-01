package dev.seabat.android.hellobottomnavi.ui.pages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.seabat.android.hellobottomnavi.R
import dev.seabat.android.hellobottomnavi.databinding.ListitemGithubRepoBinding
import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryEntity
import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryListEntity

class RepositoryListAdapter : RecyclerView.Adapter<RepositoryListAdapter.RepositoryHolder>(){
    var items = RepositoryListEntity(arrayListOf())

    fun updateRepositoryList(repositoryList : RepositoryListEntity){
        this.items = repositoryList
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListitemGithubRepoBinding.inflate(layoutInflater)
        return RepositoryHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.bind(items[position])
    }

    class RepositoryHolder(val binding: ListitemGithubRepoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: RepositoryEntity){
            binding.textName.text = data.name
            binding.textDesc.text = data.description ?: ""
            binding.textCreatedDate.text = data.created_at
            
            Glide.with(binding.imageThubm)
                .load(data.owner.avatar_url)
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_foreground)
                .error(R.mipmap.ic_launcher_foreground)
                .fallback(R.mipmap.ic_launcher_foreground)
                .into(binding.imageThubm)
        }
    }
}