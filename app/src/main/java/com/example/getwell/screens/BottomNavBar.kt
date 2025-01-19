package com.example.getwell.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.getwell.R
import com.example.getwell.data.NavItem


val robosto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold)
)
@Composable
fun BottomNavigationBar(
    list : List<NavItem>,
    navController: NavController,
    modifier : Modifier = Modifier,
    onNavClick: (NavItem) -> Unit,
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = Modifier.height(80.dp),
        backgroundColor = Color(23,23,27),
        elevation = 5.dp
    ) {

        list.forEach{item->
            val selected = item.route == backStackEntry.value?.destination?.route
            val color = remember{ mutableStateOf(Color.Transparent) }
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.White,

                onClick = {
                    onNavClick(item)
                    color.value = Color(181, 242, 255)
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    color = if (selected) Color(
                                        181,
                                        242,
                                        255
                                    ) else Color.Transparent,

                                    )
                                .size(50.dp, 38.dp)

                            )
                            Icon(
                                modifier = Modifier.size(25.dp),
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        }

                        Text(
                            text = item.title,
                            textAlign = TextAlign.Center,
                            fontFamily = robosto,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color.White
                        )

                    }

                }
            )

        }
    }
}



