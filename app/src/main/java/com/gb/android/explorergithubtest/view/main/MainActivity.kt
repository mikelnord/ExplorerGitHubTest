package com.gb.android.explorergithubtest.view.main


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gb.android.explorergithubtest.BuildConfig
import com.gb.android.explorergithubtest.R
import com.gb.android.explorergithubtest.databinding.ActivityMainBinding
import com.gb.android.explorergithubtest.model.User
import com.gb.android.explorergithubtest.repository.FakeGitHubRepository
import com.gb.android.explorergithubtest.repository.GitHubRepository
import com.gb.android.explorergithubtest.repository.IDataSource
import com.gb.android.explorergithubtest.repository.IGitHubRepository
import com.gb.android.explorergithubtest.view.detail.DetailsActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity(), ViewContractMain {

    private val adapter = UsersAdapter()

    //private val presenter: PresenterMainContract = UsersPresenter(this, createRepository())
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var totalCount: Int = 0
    private val repository by lazy { createRepository() }
    private val viewModel by viewModels<MainViewModel> {
        MyViewModelFactory(repository)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        viewModel.liveData.observe(this) { onStateChange(it) }
    }


    private fun onStateChange(screenState: ScreenState) {
        when (screenState) {
            is ScreenState.Working -> {
                val listUsers = screenState.list
                val totalCount = listUsers?.size
                binding.progressBar.visibility = View.GONE
                with(binding.totalCountTextView) {
                    visibility = View.VISIBLE
                    text =
                        String.format(
                            Locale.getDefault(),
                            getString(R.string.results_count),
                            totalCount
                        )
                }

                if (totalCount != null) {
                    this.totalCount = totalCount
                }
                adapter.submitList(listUsers)
            }
            is ScreenState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is ScreenState.Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, screenState.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupUI() {
        binding.toDetailsActivityButton.setOnClickListener {
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setRecyclerView()
    }


    private fun setRecyclerView() {
        binding.usersRecyclerView.setHasFixedSize(true)
        binding.usersRecyclerView.adapter = adapter
    }

    override fun displayListUsers(usersList: List<User>, count: Int) {
        adapter.submitList(usersList)
        totalCount = count
        binding.totalCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), totalCount)
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

    private fun createRepository(): IGitHubRepository {
        return if (BuildConfig.TYPE == FAKE) {
            FakeGitHubRepository()
        } else {
            GitHubRepository(createRetrofit().create(IDataSource::class.java))
        }

    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
        const val FAKE = "FAKE"
    }
}