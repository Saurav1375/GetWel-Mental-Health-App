package com.example.getwell.screens.resourcesection.practical

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.getwell.R
import com.example.getwell.screens.resourcesection.education.ShimmerFeedItem
import com.example.getwell.screens.resourcesection.education.ShimmerLoadingList
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun YouTubeVideoCard(
    video: YouTubeVideo,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.videoUrl))
                context.startActivity(intent)
            },
        elevation = 4.dp,
        backgroundColor = Color(31, 31, 37)
    ) {
        Column {
            Box {
                Image(
                    painter = rememberImagePainter(
                        data = video.thumbnailUrl,
                        builder = {
                            crossfade(true)
                            error(R.drawable.mountain) // Make sure to add a fallback error image
                        }
                    ),
                    contentDescription = video.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp),
                    contentScale = ContentScale.Crop
                )

                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = "Play",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = video.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = video.description,
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    maxLines = 2
                )
            }

        }





    }
}

@Composable
fun VideoFeed(
    viewModel: VideoFeedViewModel,
    modifier: Modifier = Modifier
) {
    val lazyPagingItems = viewModel.videos.collectAsLazyPagingItems()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh() }
        ) {
            LazyColumn (
                contentPadding = PaddingValues(bottom = 100.dp)
            ){
                items(
                    count = lazyPagingItems.itemCount,
                    key = { index ->
                        val item = lazyPagingItems[index]
                        item?.id ?: "item-$index"
                    }
                ) { index ->
                    val video = lazyPagingItems[index]
                    if (video != null) {
                        YouTubeVideoCard(
                            video = video,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    } else {
                        // Show placeholder while loading
                        VideoPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                // Show loading indicator at bottom when loading more items
                if (lazyPagingItems.loadState.append is LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }

        // Show initial loading state
        if (isLoading && lazyPagingItems.itemCount == 0) {
            ShimmerLoadingListVideos()
//            Column(
//                modifier = Modifier.fillMaxSize().padding(bottom = 100.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .padding(bottom = 8.dp)
//                        .size(48.dp)
//                )
//                Text(text = "Loading Videos.. ", style = MaterialTheme.typography.h6, color = Color.Cyan)
//            }

        }

        // Error handling
        error?.let { errorMessage ->
            AlertDialog(
                onDismissRequest = { viewModel.clearError() },
                title = { Text("Error") },
                text = { Text(errorMessage) },
                confirmButton = {
                    Button(onClick = { viewModel.clearError() }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
private fun VideoPlaceholder(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp)
            )
        }
    }
}

@Composable
fun ShimmerLoadingListVideos() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(10) {
            ShimmerFeedItem(120.dp, 1f)
        }
    }
}