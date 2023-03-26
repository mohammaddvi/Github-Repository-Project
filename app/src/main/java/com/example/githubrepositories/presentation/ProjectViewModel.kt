package com.example.githubrepositories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepositories.Project
import com.example.githubrepositories.data.repository.ProjectRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val projectRepository: ProjectRepository
) : ViewModel() {

    private val stateStream: MutableStateFlow<State> =
        MutableStateFlow(State(isLoading = true))
    val state: StateFlow<State> = stateStream
    var pagingResult: Flow<PagingData<Project>> = emptyFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            stateStream.update { it.copy(isLoading = true) }
            kotlin.runCatching {
                projectRepository.getProjectsStream()
            }.fold(onSuccess = { result ->
                pagingResult = result.cachedIn(viewModelScope)
                stateStream.update { it.copy(isLoading = false) }
            }, onFailure = {
                stateStream.update { dataState -> dataState.copy(error = it.message) }
            })
        }
    }

    fun onUserClicked(project: Project) {
        stateStream.update { it.copy(detailsProject = project) }
    }
}

data class State(
    val isEmpty: Boolean = false,
    val error: String? = null,
    val data: List<Project>? = null,
    val detailsProject: Project? = null,
    val isLoading: Boolean = false
)
