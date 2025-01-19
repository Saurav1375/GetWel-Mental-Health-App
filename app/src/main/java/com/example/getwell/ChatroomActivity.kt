package com.example.getwell

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.getwell.authSystem.AuthUiClient
import com.example.getwell.di.Injection
import com.google.android.gms.auth.api.identity.Identity
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.SearchMode
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.InitializationState
import io.getstream.chat.android.models.User
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory

class ChatroomActivity : ComponentActivity() {
    private val authUiClient by lazy {
        AuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext),
            Injection.instance()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            )
        )
        // 1 - Set up the OfflinePlugin for offline storage
        val offlinePluginFactory = StreamOfflinePluginFactory(appContext = applicationContext)
        val statePluginFactory =
            StreamStatePluginFactory(config = StatePluginConfig(), appContext = this)

        // 2 - Set up the client for API calls and with the plugin for offline storage
        val client = ChatClient.Builder(this.getString(R.string.chat_api_key), applicationContext)
            .withPlugins(offlinePluginFactory, statePluginFactory)
            .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
            .build()

        // 3 - Authenticate and connect the user
        val currentUser = authUiClient.getSignedInUser()

        val user = currentUser.let {
            it?.let { it1 ->
                it.username?.let { it2 ->
                    it.profilePictureUrl?.let { it3 ->
                        User(
                            id = it1.userId,
                            name = it2,
                            image = it3
                        )
                    }
                }
            }
        }

        if (user != null) {
            client.connectUser(
                user = user,
                token = client.devToken(user.id) // Ensure this token is valid
            ).enqueue { result ->
                if (result.isSuccess) {
                    println("User connected very successfully ${result.getOrNull()}")

                    // Now that the user is connected, add them to channels
//                    fetchChannels(user)
                    addUserToChannels(user)
                } else {
                    println("Error connecting user: ${result.errorOrNull()}")
                }
            }
        }


        // List of already created channel IDsc v

        setContent {
            // Observe the client connection state
            val clientInitialisationState by client.clientState.initializationState.collectAsState()

            ChatTheme(
                isInDarkMode = true
            ) {
                Box(modifier = Modifier.fillMaxSize().background(Color(16,19,20)).padding(top= 38.dp, bottom = 6.dp, start = 4.dp, end = 4.dp)){
                    when (clientInitialisationState) {
                        InitializationState.COMPLETE -> {

                            ChannelsScreen(
                                viewModelFactory = ChannelViewModelFactory(),
                                searchMode = SearchMode.Messages,
                                title = "GetWel+ Chatroom",
                                onChannelClick = { channel ->
                                    startActivity(
                                        ChannelActivity.getIntent(
                                            this@ChatroomActivity,
                                            channel.cid
                                        )
                                    )
                                },
                                onBackPressed = {
                                    finish()
                                },
                                onViewChannelInfoAction = {
                                },
                                onHeaderAvatarClick = {}

                            )
                        }

                        InitializationState.INITIALIZING -> {
                            MainChatLoadingScreen()
                        }

                        InitializationState.NOT_INITIALIZED -> {
                            MainChatLoadingScreen()
                            Toast.makeText(
                                this@ChatroomActivity,
                                "Please try again..",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}


private fun addUserToChannels(user: User) {
    // List of already created channel IDs
    val channelIds = listOf("CalmnessCorner", "StressFreeZone", "SerenitySpace", "PeacefulHeaven")

    channelIds.forEach { channelId ->
        val channelClient = ChatClient.instance().channel(
            channelType = "messaging",
            channelId = channelId,
        )

        // Add the current user to the channel
        channelClient.addMembers(listOf(user.id)).enqueue { result ->
            if (result.isSuccess) {
                println("User added to channel: $channelId")
            } else {
                println("Error adding user to channel $channelId: ${result.errorOrNull()?.message}")
            }
        }
    }

}


@Composable
fun MainChatLoadingScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Left sidebar for channels/groups
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.Black)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Search bar placeholder
                    item {
                        ShimmerSearchBar()
                    }

                    // Channels section
                    item {
                        ShimmerSectionHeader()
                    }

                    items(5) {
                        ShimmerChannelItem()
                    }

                    // Direct messages section
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        ShimmerSectionHeader()
                    }

                    items(4) {
                        ShimmerDirectMessageItem()
                    }
                }
            }

        }
    }
}

@Composable
private fun ShimmerSearchBar() {
    val shimmerAnimation = rememberInfiniteTransition(label = "")
    val alpha by shimmerAnimation.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray.copy(alpha = alpha))
    )
}

@Composable
private fun ShimmerSectionHeader() {
    val shimmerAnimation = rememberInfiniteTransition(label = "")
    val alpha by shimmerAnimation.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .width(100.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Gray.copy(alpha = alpha))
    )
}

@Composable
private fun ShimmerChannelItem() {
    val shimmerAnimation = rememberInfiniteTransition(label = "")
    val alpha by shimmerAnimation.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = alpha))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .width(160.dp)
                .height(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = alpha))
        )
    }
}

@Composable
private fun ShimmerDirectMessageItem() {
    val shimmerAnimation = rememberInfiniteTransition(label = "")
    val alpha by shimmerAnimation.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar placeholder
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = alpha))
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name placeholder
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = alpha))
        )
    }
}