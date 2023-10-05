package com.example.favorite.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.FavoriteAdapter
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
                        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_DETAIL_USER, item.username)
                        intent.putExtra(DetailActivity.EXTRA_ID,item.id)
                        intent.putExtra(DetailActivity.EXTRA_IMG, item.avatarUrl)
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