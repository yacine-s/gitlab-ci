package com.gitlab.ci.pipelines

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitlab.ci.GitlabApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PipelineUi(
    val id: String,
    val statusIcon: ImageVector,
)

class PipelineMapper {
    fun map(pipelines: List<Pipeline>): List<PipelineUi> {
        return pipelines.map {
            PipelineUi(
                it.id.toString(),
                it.status.toStatusIcon()
            )
        }
    }

    private fun Status.toStatusIcon() =
        if (this == Status.success) Icons.Default.CheckCircle else Icons.Default.Error

}

sealed interface State {
    object Loading : State
    data class DisplayPipelines(val pipelines: List<PipelineUi>) : State
}

class PipelineListViewModel(
    private val gitlabApi: GitlabApi,
    private val mapper: PipelineMapper
) : ViewModel() {

    val state: StateFlow<State> by lazy {
        MutableStateFlow<State>(State.Loading).apply {
            viewModelScope.launch {
                val pipelines = gitlabApi.pipelines("")
                emit(State.DisplayPipelines(mapper.map(pipelines)))
            }
        }
    }

}