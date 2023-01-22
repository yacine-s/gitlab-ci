package com.gitlab.ci.pipelines

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun PipelineScreen() {
    val viewModel = koinViewModel<PipelineListViewModel>()
    val state by viewModel.state.collectAsState()
    when (state) {
        is State.DisplayPipelines -> PipelineList((state as State.DisplayPipelines).pipelines)
        State.Loading -> CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PipelineList(pipelines: List<PipelineUi>) {
    LazyColumn(content = {
        items(pipelines) { pipeline ->
            androidx.compose.material3.ListItem(
                headlineText = { Text(pipeline.id) }
            )
        }
    })
}