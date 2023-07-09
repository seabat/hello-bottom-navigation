package dev.seabat.android.hellobottomnavi.ui.pages.qiitasearch

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.hellobottomnavi.R
import dev.seabat.android.hellobottomnavi.databinding.PageQiitaSearchBinding
import dev.seabat.android.hellobottomnavi.ui.dialog.showSimpleErrorDialog
import dev.seabat.android.hellobottomnavi.ui.pages.top.TopFragment
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class QiitaSearchFragment : BottomSheetDialogFragment(R.layout.page_qiita_search) {
    private val viewModel: QiitaSearchViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener { it ->
                val bottomSheetDialog = (it as? BottomSheetDialog) ?: return@setOnShowListener
                val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) ?: return@setOnShowListener

                BottomSheetBehavior.from(bottomSheet).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    skipCollapsed = true
                }

                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = layoutParams

                //WARNING: 以下のコードでは layoutParams が更新されない！
//                bottomSheet.layoutParams.apply {
//                    this.height = WindowManager.LayoutParams.MATCH_PARENT
//                }

                this@QiitaSearchFragment.view?.let {
                    initView(it)
                    initObserver(it)
                }

            }
        }
    }

    private fun initView(view: View) {
        PageQiitaSearchBinding.bind(view).let {
            // CLOSEボタン
            it.textClose.setOnClickListener {
                this.dismiss()
            }

            // 開始日ボタン
            it.buttonStartDate.setOnClickListener {
                //TODO: 日付ピッカーを起動する
            }

            // 終了日ボタン
            it.buttonEndDate.setOnClickListener {
                //TODO: 日付ピッカーを起動する
            }

            // 検索ボタン
            it.buttonSearch.setOnClickListener {
                viewModel.search()
                this.dismiss()
            }
        }
    }

    private fun initObserver(view: View) {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if(it != null) {
                showSimpleErrorDialog(
                    message = it,
                    requestKey = TopFragment.TAG,
                    requestBundle = bundleOf("errorMessage" to it),
                    onClickCallback = { key, bundle ->
                        if (key == TopFragment.TAG) {
                            android.util.Log.d("Hello", "Error dialog closed(${bundle.getString("errorMessage")})")
                            viewModel.clearError()
                        }
                    }
                )
            }
        }

        val binding = PageQiitaSearchBinding.bind(view)
        viewModel.startDate.observe(viewLifecycleOwner) {
            binding.textStartDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(it)
        }

        viewModel.endDate.observe(viewLifecycleOwner) {
            binding.textEndDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(it)
        }
    }

    /**
     * 上部の角を丸くしたデザインを適用する
     *
     * ref. https://note.com/yasukotelin/n/nb1c877358d4a
     */
    override fun getTheme(): Int {
        return R.style.QiitaSearchBottomSheetDialogTheme
    }
}