package dev.seabat.android.hellobottomnavi.ui.pages.qiitasearch

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.seabat.android.hellobottomnavi.R
import dev.seabat.android.hellobottomnavi.databinding.PageQiitaSearchBinding

class QiitaSearchFragment : BottomSheetDialogFragment(R.layout.page_qiita_search) {

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
                }
            }
        }
    }

    private fun initView(view: View) {
        // CLOSE
        val bind = PageQiitaSearchBinding.bind(view)
        bind.textClose.setOnClickListener {
            this.dismiss()
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