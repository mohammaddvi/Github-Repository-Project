package com.example.githubrepositories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepositories.Project
import com.example.githubrepositories.data.repository.ProjectRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectViewModel(private val projectRepository: ProjectRepository
) : ViewModel() {

    private val mutableState: MutableStateFlow<DataState<List<Project>>> =
        MutableStateFlow(
            DataState(
                isLoading = true
            )
        )
    val state: StateFlow<DataState<List<Project>>> = mutableState
    var pagingResult: Flow<PagingData<Project>> = emptyFlow()

    init {
        loadData()
    }

    fun loadData(): Job {
        mutableState.update { it.copy(isLoading = true) }

        return viewModelScope.launch {
            try {
                pagingResult = projectRepository.getProjectsStream()
                    .catch { throwable ->
                        mutableState.update { dataState -> dataState.copy(error = throwable.message) }
                    }.cachedIn(viewModelScope)
                mutableState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                mutableState.update { dataState -> dataState.copy(error = e.message) }
            }
        }
    }
}

data class DataState<out T>(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val error: String? = null,
    val data: T? = null,
)
