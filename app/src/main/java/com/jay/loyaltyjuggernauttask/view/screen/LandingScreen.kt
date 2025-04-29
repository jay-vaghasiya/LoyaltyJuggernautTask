package com.jay.loyaltyjuggernauttask.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jay.loyaltyjuggernauttask.model.local.entity.GitHubRepoEntity
import com.jay.loyaltyjuggernauttask.util.NetworkState
import com.jay.loyaltyjuggernauttask.viewmodel.NetworkViewModel
import com.jay.loyaltyjuggernauttask.viewmodel.StateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LandingScreen(navController: NavHostController) {
    val viewModel: NetworkViewModel = koinViewModel()
    val stateViewModel: StateViewModel = koinViewModel()
    val networkState by viewModel.repositories.collectAsStateWithLifecycle()
    val language by stateViewModel.language
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {


        viewModel.fetchRepositories(if (language.isBlank()) "" else "language:${language.trim()}")


    }


    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = language,
            onValueChange = { stateViewModel.setLanguage(it) },
            prefix = { Text("language:") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            trailingIcon = {
                IconButton(onClick = {
                    scope.launch(Dispatchers.IO) {

                        viewModel.fetchRepositories(if (language.isBlank()) "" else "language:${language.trim()}")

                    }
                }) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),

            )

        when (networkState) {
            is NetworkState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Red)
                ) {
                    Text(text = (networkState as NetworkState.Error).message, color = Color.Black)
                }
            }

            NetworkState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            is NetworkState.Success -> {
                Content(
                    navController,
                    (networkState as NetworkState.Success<List<GitHubRepoEntity>>).data
                )
            }
        }
    }

}

@Composable
fun Content(
    navController: NavHostController,
    data: List<GitHubRepoEntity>,
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        if (data.isNotEmpty()) {
            itemsIndexed(items = data) { _, item ->
                RepositoryItem(item) {
                    navController.navigate(Web(item.htmlUrl))
                }
            }
        } else {
            item {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = "No Repository",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}


@Composable
fun RepositoryItem(item: GitHubRepoEntity, onClick: () -> Unit) {
    ElevatedCard(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = "Repository : " + item.repoName
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = "Provider : " + item.username.toString()
            )
        }
    }
}

