package com.example.githubusercleanarchitecture.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.core.data.source.local.entity.FavoriteUser
import com.example.core.domain.model.User
import com.example.githubusercleanarchitecture.R
import com.example.githubusercleanarchitecture.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail User"

        val bundle = intent.extras
        if(bundle != null) {
            val user = bundle.getParcelable<User>(EXTRA_DATA)
            binding.tvName.text = user?.username
            Glide.with(this)
                .load(user?.avatarUrl)
                .into(binding.profileImage)
            binding.tvIdUser.text = user?.id.toString()
        }

        var selected = false
        CoroutineScope(Dispatchers.IO).launch {
            val user = bundle?.getParcelable<User>(EXTRA_DATA)
            val counter = user?.let { detailViewModel.checkUser(it.id) }
            withContext(Dispatchers.Main) {
                if (counter != null) {
                    if (counter > 0) {
                        binding.toggleButton.isChecked = true
                        selected = true
                    } else {
                        binding.toggleButton.isChecked = false
                        selected = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener {
            showLoading(true)
            val user = bundle?.getParcelable<User>(EXTRA_DATA)
            selected = !selected
            if (selected) {
                if (user?.username != null && user.avatarUrl != null) {
                    detailViewModel.setFavorite(FavoriteUser(
                        user.id,
                        user.username,
                        user.avatarUrl
                    ))
                }
            } else {
                user?.id?.let { it1 ->
                    CoroutineScope(Dispatchers.IO).launch {
                        detailViewModel.removeFavorite(it1)
                    }
                }
            }
            showLoading(false)
            binding.toggleButton.isChecked = selected
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
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}