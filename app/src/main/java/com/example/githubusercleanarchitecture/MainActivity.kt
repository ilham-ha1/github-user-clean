package com.example.githubusercleanarchitecture

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.UserAdapter
import com.example.githubusercleanarchitecture.databinding.ActivityMainBinding
import com.example.githubusercleanarchitecture.presentation.favorite.FavoriteActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        lifecycleScope.launch {
            try {
                mainViewModel.setUsername("ilham")
                showLoading(true)

                mainViewModel.getUser().observe(this@MainActivity) { user ->
                    showLoading(false)

                    val adapter = UserAdapter()
                    adapter.setData(user)
                    binding.rvUser.setHasFixedSize(true)
                    binding.rvUser.adapter = adapter
                }
            } catch (e: Exception) {
                showLoading(false)
                showNotFound(true)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_user_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.setUsername(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                mainViewModel.setUsername(newText)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu2 ->{
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
//            R.id.menu1 ->{
//                Intent(this, SettingActivity::class.java).also {
//                    startActivity(it)
//                }
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvUser.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvNotFound.visibility = View.GONE
    }

    private fun showNotFound(isNotFound: Boolean) {
        binding.progressBar.visibility = View.GONE
        binding.rvUser.visibility = View.GONE
        binding.tvNotFound.visibility = if (isNotFound) View.VISIBLE else View.GONE
    }

}