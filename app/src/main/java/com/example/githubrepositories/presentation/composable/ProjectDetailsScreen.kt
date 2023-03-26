package com.example.githubrepositories.presentation.composable

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubrepositories.Project
import com.example.githubrepositories.R


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProjectDetailsScreen(project: Project) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.align(CenterHorizontally)) {
            AsyncImage(
                model = project.ownerAvatarUrl,
                contentDescription = stringResource(id = R.string.owner_avatar_description),
                modifier = Modifier
                    .size(128.dp)
                    .padding(10.dp),
            )
        }
        DetailItem(title = stringResource(id = R.string.project_name_title), value = project.name)
        DetailItem(
            title = stringResource(R.string.project_fullname_title),
            value = project.fullName
        )
        DetailItem(
            title = stringResource(R.string.project_description_title),
            value = project.description ?: ""
        )
        DetailItem(
            title = stringResource(id = R.string.project_visibility_title),
            value = project.visibility
        )
        DetailItem(
            title = stringResource(id = R.string.project_private_title),
            value = project.isPrivate.toString()
        )
        Spacer(modifier = Modifier.size(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Red),
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(project.htmlUrl)))
            }
        ) {
            Text(text = stringResource(R.string.open_browser_text), color = Black)
        }
    }
}


@Composable
fun DetailItem(title: String, value: String) {
    Row(modifier = Modifier.padding(10.dp)) {
        Text(title)
        Spacer(modifier = Modifier.padding(2.dp))
        Text(value)
    }
}

