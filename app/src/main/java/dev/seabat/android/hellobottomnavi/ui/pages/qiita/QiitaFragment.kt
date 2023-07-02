package dev.seabat.android.hellobottomnavi.ui.pages.qiita

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dev.seabat.android.hellobottomnavi.R
import dev.seabat.android.hellobottomnavi.databinding.PageQiitaBinding

class QiitaFragment: Fragment(R.layout.page_qiita) {
    companion object {
        val TAG: String = QiitaFragment::class.java.simpleName
    }

    private var binding: PageQiitaBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageQiitaBinding.bind(view)
        initView()
        initToolBar()
        initObserver()
        return
    }

    private fun initView() {
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}