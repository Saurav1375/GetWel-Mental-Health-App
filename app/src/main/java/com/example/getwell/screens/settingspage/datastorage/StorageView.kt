package com.example.getwell.screens.settingspage.datastorage

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getwell.R
import com.example.getwell.screens.customFont

@Composable

fun StorageView(viewModel: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
)) {
    val storageInfo by viewModel.storageInfo.collectAsState()
    val dataUsage by viewModel.dataUsage.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.refreshStorageInfo()
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Transparent, RoundedCornerShape(5.dp))

            ) {

                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(9.dp)),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .scale(1.5f),
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "GetWel+",
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    color = Color.White
                )
            )
            Text(
                text = "Version 1.0",
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(111,111,111)
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Storage Usage",
            style = TextStyle(
                fontFamily = customFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp,
                color = Color.White
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(Color(31, 31, 37), RoundedCornerShape(15.dp))
                .padding(start = 16.dp, end = 16.dp)
        ){
            storageInfo?.let {info->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    CardItemView(title = "Total", value = formatSize(info.totalSize))
                    CardItemView(title = "App", value = formatSize(info.appSize))
                    CardItemView(title = "Data", value = formatSize(info.dataSize))
                    CardItemView(title = "Cache", value = formatSize(info.cacheSize))
                }

            }

        }
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Data Usage",
            style = TextStyle(
                fontFamily = customFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp,
                color = Color.White
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(Color(31, 31, 37), RoundedCornerShape(15.dp))
                .padding(start = 16.dp, end = 16.dp)
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                dataUsage?.let { info ->
                    CardItemView(title = "Total", value = formatSize(info.totalBytes))
                    CardItemView(title = "Downloaded", value = formatSize(info.receivedBytes))
                    CardItemView(title = "Uploaded", value = formatSize(info.transmittedBytes))
                }
            }

        }
        Spacer(modifier = Modifier.height(32.dp))


    }
}

private fun formatSize(bytes: Long): String {
    val df = DecimalFormat("#.##")
    val kb = bytes / 1024.0
    val mb = kb / 1024.0
    val gb = mb / 1024.0

    return when {
        gb >= 1 -> "${df.format(gb)} GB"
        mb >= 1 -> "${df.format(mb)} MB"
        kb >= 1 -> "${df.format(kb)} KB"
        else -> "$bytes Bytes"
    }
}

@Composable
fun CardItemView(
    title: String,
    value : String
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(
//                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    color = Color.White
                )
            )
            Text(
                text = value,
                style = TextStyle(
//                    fontFamily = customFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(111,111,111)
                )
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color(111,111,111)
        )
    }
}