package com.example.githubusercleanarchitecture.presentation.detail

import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubusercleanarchitecture.R
import com.example.githubusercleanarchitecture.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = R.string.detail_user.toString()

        val user = intent.getStringExtra(EXTRA_DETAIL_USER)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val img = intent.getStringExtra(EXTRA_IMG)

        binding.tvName.text = user
        Glide.with(this)
                .load(img)
                .into(binding.profileImage)
        binding.tvIdUser.text = id.toString()

        var selected = false
        CoroutineScope(Dispatchers.IO).launch {
            val counter = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (counter > 0) {
                    binding.toggleButton.isChecked = true
                    selected = true
                } else {
                    binding.toggleButton.isChecked = false
                    selected = false
                }
            }
        }

        binding.toggleButton.setOnClickListener {
            showLoading(true)
            selected = !selected
            if (selected) {
                if (user != null && img != null) {
                    img?.let { it1 ->
                        detailViewModel.setFavorite(
                            id,
                            user,
                            it1
                        )
                    }
                }
            } else {
                id.let { it1 ->
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

    companion object {
        const val EXTRA_DETAIL_USER = "extra_detail_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_IMG = "extra_img"
    }
}