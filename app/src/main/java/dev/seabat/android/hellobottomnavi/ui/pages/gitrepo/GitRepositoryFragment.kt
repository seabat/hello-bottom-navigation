package dev.seabat.android.hellobottomnavi.ui.pages.gitrepo

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.hellobottomnavi.R
import dev.seabat.android.hellobottomnavi.databinding.PageGitRepositoryBinding
import dev.seabat.android.hellobottomnavi.ui.dialog.showSimpleErrorDialog

@AndroidEntryPoint
class GitRepositoryFragment : Fragment(R.layout.page_git_repository) {
    companion object {
        val TAG: String = GitRepositoryFragment::class.java.simpleName
    }

    private var binding: PageGitRepositoryBinding? = null
    private val viewModel: GitRepositoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageGitRepositoryBinding.bind(view)
        initAppBar()
        initView()
        initObserver()
        viewModel.loadRepositories()
        return
    }

    private fun initView() {
        binding?.recyclerview?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            adapter =
                GitRepositoryListAdapter(
                    onListItemClick = this@GitRepositoryFragment.onListItemClick
                )
        }

        binding?.search?.setOnCloseListener {
            binding?.search?.visibility = View.GONE
            binding?.toolbar?.visibility = View.VISIBLE
            true
        }

        binding?.search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean = false

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.loadRepositories(query)
                binding?.search?.visibility = View.GONE
                binding?.toolbar?.visibility = View.VISIBLE
                return false
            }
        })
    }

    private fun initObserver() {
        viewModel.repositories.observe(viewLifecycleOwner) {
            (binding?.recyclerview?.adapter as GitRepositoryListAdapter).updateRepositoryList(it)
        }

        viewModel.progressVisible.observe(viewLifecycleOwner) {
            if (it) {
                binding?.progressbar?.visibility = View.VISIBLE
            } else {
                binding?.progressbar?.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showSimpleErrorDialog(
                    message = it,
                    requestKey = TAG,
                    requestBundle = bundleOf("errorMessage" to it),
                    onClickCallback = { key, bundle ->
                        if (key == TAG) {
                            android.util.Log.d(
                                "Hello",
                                "Error dialog closed(${bundle.getString("errorMessage")})"
                            )
                            viewModel.clearError()
                        }
                    }
                )
            }
        }
    }

    private fun initAppBar() {
        // NOTE: フラグメントが所有するアプリバーは onCreateOptionsMenu ではなく
        //       ここで onViewCreated 等で inflate する
        //       ref. https://developer.android.com/guide/fragments/appbar?hl=ja#fragment-inflate
        binding?.toolbar?.inflateMenu(R.menu.top)
        binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    binding?.search?.visibility = View.VISIBLE
                    binding?.toolbar?.visibility = View.GONE
                    true
                }

                R.id.menu_refresh -> {
                    viewModel.loadRepositories()
                    true
                }

                R.id.menu_setting -> {
                    val action = GitRepositoryFragmentDirections.actionToSetting().apply {
                        inVisibleBottomNav =
                            true
                    }
                    findNavController().navigate(action)
                    true
                }

                else -> false
            }
        }
    }

    private val onListItemClick: (fullName: String, htmlUrl: String) -> Unit =
        { fullName, htmlUrl ->
            val action = GitRepositoryFragmentDirections.actionToGitRepoDetail().apply {
                repoName = fullName
                repoUrl = htmlUrl
            }
            this.findNavController().navigate(action)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}