package com.example.githubrepositories.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.githubrepositories.Project
import com.example.githubrepositories.R
import com.example.githubrepositories.presentation.ProjectViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProjectListScreen(
    viewModel: ProjectViewModel,
    onRefresh: () -> Unit = {},
    onUserClicked: (Project) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifeCycleAwareStateFlow = remember(viewModel.state, lifecycleOwner) {
        viewModel.state.flowWithLifecycle(lifecycleOwner.lifecycle)
    }
    val state by lifeCycleAwareStateFlow.collectAsState(initial = viewModel.state.value)
    val lazyPagingItems = viewModel.pagingResult.collectAsLazyPagingItems()

    Surface(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
            onRefresh = onRefresh
        ) {
            lazyPagingItems.let { items ->
                LazyColumn {
                    items(items) { repositoryModel ->
                        if (repositoryModel != null) {
                            ProjectItem(
                                modifier = Modifier.background(Color.Yellow).padding(5.dp),
                                repositoryModel = repositoryModel,
                                onUserClicked = {
                                    viewModel.onUserClicked(it)
                                    onUserClicked(it)
                                }
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectItem(
    modifier: Modifier = Modifier,
    repositoryModel: Project,
    onUserClicked: (Project) -> Unit
) {
    Row(
        modifier
            .fillMaxSize()
            .clickable { repositoryModel.description?.let { onUserClicked(repositoryModel) } }
            .padding(10.dp)
    ) {
        AsyncImage(
            model = repositoryModel.ownerAvatarUrl,
            contentDescription = stringResource(R.string.owner_avatar_description),
            modifier = Modifier
                .size(64.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp)),
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Column {
                Text(stringResource(R.string.project_name_title), fontWeight = FontWeight.Bold)
                Text(
                    stringResource(R.string.project_visibility_title),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    stringResource(id = R.string.project_private_title),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Column {
                Text(repositoryModel.name)
                Text(repositoryModel.visibility)
                Text(repositoryModel.isPrivate.toString())

            }
        }
    }
}
