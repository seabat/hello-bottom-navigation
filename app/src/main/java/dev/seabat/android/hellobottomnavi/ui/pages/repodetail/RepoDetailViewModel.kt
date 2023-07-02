package dev.seabat.android.hellobottomnavi.ui.pages.repodetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.hellobottomnavi.domain.usecase.GithubUseCaseContract
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}