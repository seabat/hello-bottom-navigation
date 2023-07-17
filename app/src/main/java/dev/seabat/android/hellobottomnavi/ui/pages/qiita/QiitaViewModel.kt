package dev.seabat.android.hellobottomnavi.ui.pages.qiita

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.hellobottomnavi.ErrorStringConverter
import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity
import dev.seabat.android.hellobottomnavi.domain.exception.HelloException
import dev.seabat.android.hellobottomnavi.domain.usecase.FetchQiitaArticlesUseCaseContract
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QiitaViewModel @Inject constructor(
    private val fetchQiitaArticlesUseCase: FetchQiitaArticlesUseCaseContract,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _articles =
        MutableLiveData<QiitaArticleListEntity>(QiitaArticleListEntity(arrayListOf()))
    val articles: LiveData<QiitaArticleListEntity>
        get() = _articles

    private var _progressVisible = MutableLiveData<Boolean>(false)
    val progressVisible: LiveData<Boolean>
        get() = _progressVisible

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    /**
     * @param startCreatedAt: 検索対象開始日 ex. 2023-07-02
     */
    fun loadQiitaArticles(startCreatedAt: String) {
        viewModelScope.launch {
            _progressVisible.value = true
            kotlin.runCatching {
                fetchQiitaArticlesUseCase(startCreatedAt)
            }.onSuccess {
                _articles.value = it
            }.onFailure {
                val errorString = ErrorStringConverter.convertTo((it as HelloException).errType)
                android.util.Log.d("Hello", errorString)
                _errorMessage.value = errorString
            }.also {
                _progressVisible.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}