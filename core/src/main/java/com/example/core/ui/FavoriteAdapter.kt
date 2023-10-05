package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListUserBinding
import com.example.core.domain.model.User

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>(){
    private var listData = ArrayList<User>()
    var onItemClick: ((User) -> Unit)? = null

    fun setData(newListData: List<User>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListUserBinding.bind(itemView)
        fun bind(data: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .into(imgItemPhoto)
                tvItemName.text = data.username
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}