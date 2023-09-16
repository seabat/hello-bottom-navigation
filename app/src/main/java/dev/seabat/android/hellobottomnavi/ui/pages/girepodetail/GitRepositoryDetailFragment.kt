package dev.seabat.android.hellobottomnavi.ui.pages.girepodetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.hellobottomnavi.R
import dev.seabat.android.hellobottomnavi.databinding.PageGitRepositoryDetailBinding

@AndroidEntryPoint
class GitRepositoryDetailFragment : Fragment(R.layout.page_git_repository_detail) {
    companion object {
        val TAG: String = GitRepositoryDetailFragment::class.java.simpleName
    }

    private var binding: PageGitRepositoryDetailBinding? = null
    private val viewModel: GitRepositoryDetailViewModel by viewModels()
    val args: GitRepositoryDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageGitRepositoryDetailBinding.bind(view)
        initView()
        initToolBar()
        initObserver()
        return
    }

    private fun initView() {
        val repoUrl = args.repoUrl
        val repoWebView = binding?.webview
        repoWebView?.loadUrl(repoUrl)
    }

    private fun initToolBar() {
        // 戻るボタン
        this.findNavController().let {
            val appBarConfig = AppBarConfiguration(it.graph)
            binding?.toolbar?.setupWithNavController(it, appBarConfig)
        }

        // タイトル
        val repoName = args.repoName
        binding?.toolbar?.title = repoName
    }

    private fun initObserver() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}