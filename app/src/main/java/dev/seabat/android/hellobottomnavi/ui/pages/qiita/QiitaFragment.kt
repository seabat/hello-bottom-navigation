package dev.seabat.android.hellobottomnavi.ui.pages.qiita

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.hellobottomnavi.R
import dev.seabat.android.hellobottomnavi.databinding.PageQiitaBinding
import dev.seabat.android.hellobottomnavi.ui.dialog.showSimpleErrorDialog

@AndroidEntryPoint
class QiitaFragment: Fragment(R.layout.page_qiita) {
    companion object {
        val TAG: String = QiitaFragment::class.java.simpleName
    }

    private var binding: PageQiitaBinding? = null
    private val viewModel: QiitaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageQiitaBinding.bind(view)
        initView()
        initToolBar()
        initObserver()
        viewModel.loadQiitaArticles()
        return
    }

    private fun initView() {
        binding?.recyclerview?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            adapter = QiitaArticleListAdapter(onListItemClick = this@QiitaFragment.onListItemClick)
        }
    }

    private fun initToolBar() {
        // 戻るボタン
        this.findNavController().let {
            val appBarConfig = AppBarConfiguration(it.graph)
            binding?.toolbar?.setupWithNavController(it, appBarConfig)
        }

        // タイトル
        binding?.toolbar?.title = getString(R.string.qiita_title)
    }

    private fun initObserver() {
        viewModel.articles.observe(viewLifecycleOwner) {
            (binding?.recyclerview?.adapter as QiitaArticleListAdapter)?.updateArticleList(it)
        }

        viewModel.progressVisible.observe(viewLifecycleOwner) {
            if(it) {
                binding?.progressbar?.visibility = View.VISIBLE
            } else {
                binding?.progressbar?.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if(it != null) {
                showSimpleErrorDialog(
                    message = it,
                    requestKey = TAG,
                    requestBundle = bundleOf("errorMessage" to it),
                    onClickCallback = { key, bundle ->
                        if (key == TAG) {
                            android.util.Log.d("Hello", "Error dialog closed(${bundle.getString("errorMessage")})")
                            viewModel.clearError()
                        }
                    }
                )
            }
        }
    }



    private val onListItemClick: (title: String, htmlUrl: String) -> Unit =
        { title, htmlUrl ->
            //TODO:
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}