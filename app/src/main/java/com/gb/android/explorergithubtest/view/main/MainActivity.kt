package com.gb.android.explorergithubtest.view.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gb.android.explorergithubtest.R
import com.gb.android.explorergithubtest.databinding.ActivityMainBinding
import com.gb.android.explorergithubtest.model.User
import com.gb.android.explorergithubtest.presenter.main.PresenterContract
import com.gb.android.explorergithubtest.presenter.main.UsersPresenter
import com.gb.android.explorergithubtest.repository.GitHubRepository
import com.gb.android.explorergithubtest.repository.IDataSource
import com.gb.android.explorergithubtest.view.detail.DetailsActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.github.com"

class MainActivity : AppCompatActivity(), ViewContractMain {

    private val adapter = UsersAdapter()
    private val presenter: PresenterContract = UsersPresenter(this, createRepository())
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var totalCount: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.toDetailsActivityButton.setOnClickListener {
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setRecyclerView()
        setUsersList()
    }

    private fun setUsersList() {
        presenter.listUsers()
    }


    private fun setRecyclerView() {
        binding.usersRecyclerView.setHasFixedSize(true)
        binding.usersRecyclerView.adapter = adapter
    }

    override fun displayListUsers(usersList: List<User>) {
        adapter.submitList(usersList)
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun onUserClicked(user: User) {
        TODO("Not yet implemented")
    }

    private fun createRepository(): GitHubRepository {
        return GitHubRepository(createRetrofit().create(IDataSource::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}