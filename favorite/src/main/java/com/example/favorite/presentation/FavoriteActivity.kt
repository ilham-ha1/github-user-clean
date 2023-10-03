package com.example.favorite.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.source.remote.response.ItemsItem
import com.example.core.domain.model.User
import com.example.core.ui.FavoriteAdapter
import com.example.core.ui.UserAdapter
import com.example.favorite.R
import com.example.favorite.databinding.ActivityFavoriteBinding
import com.example.favorite.di.favoriteModule
import com.example.githubusercleanarchitecture.presentation.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel:FavoriteViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite User"

        loadKoinModules(favoriteModule)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        lifecycleScope.launch {
            try {
                showLoading(true)

                favoriteViewModel.getFavorite().observe(this@FavoriteActivity) { user ->
                    showLoading(false)

                    val adapter = FavoriteAdapter()
                    adapter.onItemClick = {item ->
                        val userParcel = User(
                            id = item.id,
                            username = item.username,
                            avatarUrl = item.avatarUrl
                        )
                        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_DATA, userParcel)
                        startActivity(intent)
                    }

                    adapter.setData(user)
                    binding.rvUser.setHasFixedSize(true)
                    binding.rvUser.adapter = adapter
                }
            } catch (e: Exception) {
                showLoading(false)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvUser.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}