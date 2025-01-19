package com.example.getwell.screens.resourcesection.support

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.getwell.ChatroomActivity
import com.example.getwell.R
import com.example.getwell.data.Screen
import com.example.getwell.data.navItemList
import com.example.getwell.data.resourceItemList
import com.example.getwell.screens.BottomNavigationBar
import com.example.getwell.screens.resourcesection.education.ResourceItemView
import com.example.getwell.screens.customFont

data class SupportItem(
    val title : String,
    val desc : String,
   val icon : String,
    val helpline : String,
    val time : String,
    val languages : String,
    val email : String,
    val website : String
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SupportResourcesScreen(
    navController: NavHostController
) {
    BackHandler {
    navController.navigate(Screen.EducationScreen.route)
    }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val supportItems = listOf(
        SupportItem(
            title = "MPower Minds",
            desc = "The Mpower Minds Helpline is a mental health service dedicated to providing free, confidential support to individuals experiencing psychological distress. Available 24/7, the helpline connects callers with trained mental health professionals.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/HuBJXA-MPower.png",
            helpline = "1800-120-820050",
            time = "24 hours | 7 days a week",
            languages = "English, हिंदी, मराठी",
            email = "mpowerminds.info@abet.co.in",
            website = "https://mpowerminds.com/oneonone"
        ),
        SupportItem(
            title = "Fortis",
            desc = "This helpline by Fortis Healthcare is open to all, offering 24/7 crisis intervention by expert psychologists.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/XvvIsH-Fortis.png",
            helpline = "+91-8376804102",
            time = "24 Hours | Monday to Sunday",
            languages = "অসমীয়া, বাংলা, English, ગુજરાતી, हिंदी, ಕನ್ನಡ, कोंकणी, मराठी, മലയാളം, ਪੰਜਾਬੀ, தமிழ், తెలుగు, اُردُو, राजस्थानी, Achiku",
            email = "mentalhealth@fortishealthcare.com",
            website = "https://www.fortishealthcare.com"
        ),
        SupportItem(
            title = "1Life Suicide Prevention & Crisis Support",
            desc = "1Life is a non-profit organization committed to suicide prevention, supported by experienced mental health professionals and trained tele-counselors.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/liAAEP-1Life.jpeg",
            helpline = "7893078930",
            time = "5am to 12 am",
            languages = "हिंदी, English, తెలుగు, தமிழ், ಕನ್ನಡ, മലയാളം, ગુજરાતી, मराठी, ਪੰਜਾਬੀ, سنڌي, भोजपुरी, বাংলা",
            email = "support@1life.org.in",
            website = "https://1life.org.in/"
        ),
        SupportItem(
            title = "Voice That Cares (VTC)",
            desc = "VTC is a PAN India free public helpline providing psychological first aid counseling support for mental health and well-being, launched by Ripples of Change Foundation.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/XiEqft-VTC.jpg",
            helpline = "8448-8448-45",
            time = "9am-9pm | 7 days a week",
            languages = "English, हिंदी, తెలుగు",
            email = "info@rocf.org",
            website = "https://www.rocf.org/voice-that-cares/"
        ),
        SupportItem(
            title = "Connecting Trust Distress Helpline",
            desc = "Connecting Trust offers immediate emotional support to individuals in distress, especially those experiencing suicidal thoughts, with trained volunteers available to assist.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/FPhirc-Connecting-Trust-1.png",
            helpline = "+91-9922001122",
            time = "12:00 PM - 08:00 PM | All days of the week",
            languages = "English, हिंदी, मराठी",
            email = "distressmailsconnecting@gmail.com",
            website = "https://connectingngo.org/programs/distress-helpline-program/"
        ),
        SupportItem(
            title = "Roshni Trust",
            desc = "A voluntary organization valuing human life, Roshni Trust provides free, confidential emotional support services through its helpline.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/fIgAuE-Roshini.png",
            helpline = "+91 8142020033",
            time = "11:00 AM - 9:00 PM | 7 days a week",
            languages = "తెలుగు, हिंदी, English",
            email = "roshnihelp@gmail.com",
            website = "https://roshinitrust.com/"
        ),
        SupportItem(
            title = "Lifeline",
            desc = "Lifeline offers a free tele-helpline providing emotional support to people in despair, depression, or suicidal situations, with face-to-face befriending available by appointment.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/zAAzJy-Lifeline.png",
            helpline = "+91-9088030303",
            time = "10:00 AM - 10:00 PM | 7 days a week",
            languages = "বাংলা, हिंदी, English",
            email = "lifelinekolkata@gmail.com",
            website = ""
        ),
        SupportItem(
            title = "Mann Talks",
            desc = "A Shantital Shanghvi Foundation initiative, Mann Talks empowers individuals to take charge of their mental health through empathetic support from trained professionals.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/hcKfZa-Mann-talks.png",
            helpline = "+91-8686139139",
            time = "9:00 AM - 8:00 PM | 7 days a week",
            languages = "हिंदी, English",
            email = "counselling@manntalks.org",
            website = "https://www.manntalks.org/"
        ),
        SupportItem(
            title = "Arpita Foundation",
            desc = "Bangalore-based Arpita Foundation provides professional counseling and guidance across India, founded by Patrick and joined by Dr. Kalyani Keerthi Giridhara.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/QYUHht-Arpita.png",
            helpline = "+91 80 23656667",
            time = "7:00 AM - 09:00 PM | 7 days a week",
            languages = "English, हिंदी, اردو, ಕನ್ನಡ, தமிழ், తెలుగు, മലയാളം, कोंकणी, অহমিয়া, ગુજરती, বাংলা",
            email = "arpita.helpline@gmail.com",
            website = "https://www.arpitafoundation.org/"
        ),
        SupportItem(
            title = "Sangath",
            desc = "A Goa-based not-for-profit organization, Sangath works to make mental health services accessible and affordable, with a dedicated COVID-19 well-being centre.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/ItMeGB-Sangath.png",
            helpline = "011-41198666",
            time = "10:00 AM - 6:00 PM | 7 days a week",
            languages = "English, हिंदी, मराठी, कोंकणी",
            email = "contactus@sangath.in",
            website = "https://sangath.in/"
        ),
        SupportItem(
            title = "Sumaitri",
            desc = "Sumaitri offers unconditional and unbiased emotional support for those feeling depressed, distressed, or suicidal through crisis intervention services.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/IQyXcd-Sumaitri.png",
            helpline = "011-23389090",
            time = "12:30 PM - 5:00 PM | 7 days a week",
            languages = "हिंदी, English",
            email = "feelingsuicidal@sumaitri.net",
            website = "https://www.sumaitri.net/"
        ),
        SupportItem(
            title = "iCALL",
            desc = "iCALL is a psychosocial helpline offering free professional counseling via phone, email, and chat to individuals in emotional and psychological distress.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/gGvSxX-iCall.png",
            helpline = "022-25521111",
            time = "10 AM - 8 PM | Monday to Saturday",
            languages = "मराठी, বাংলা, తెలుగు, हिंदी, English",
            email = "icall@tiss.edu",
            website = "https://icallhelpline.org/"
        ),
        SupportItem(
            title = "Muktaa Helpline",
            desc = "Muktaa Charitable Foundation provides a free helpline run by experienced volunteers, supporting mental health, social welfare, and HIV/AIDS awareness.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/xZQeLS-Muktaa.png",
            helpline = "788-788-9882",
            time = "12-8 PM | Monday-Saturday",
            languages = "English, हिंदी, मराठी",
            email = "contactus@mcf.org.in",
            website = "https://mcf.org.in/"
        ),
        SupportItem(
            title = "Parivarthan",
            desc = "Parivarthan provides confidential counseling and support to individuals in crisis, aiming to facilitate emotional well-being.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/AnLrKP-Parivarthan.png",
            helpline = "+91-7676602602",
            time = "9:00 AM - 9:00 PM | Monday-Saturday",
            languages = "English, हिंदी, ಕನ್ನಡ",
            email = "parivarthanblr@gmail.com",
            website = "https://parivarthan.org/"
        ),
        SupportItem(
            title = "COOJ",
            desc = "COOJ offers free emotional support for those dealing with suicidal thoughts, emotional distress, or loss of a loved one.",
            icon = "https://cdn.thelivelovelaughfoundation.org/the_live_love_laugh/uploads/media/source/fdUmqP-COOJ.png",
            helpline = "+91-842-508-1914",
            time = "3 PM - 7 PM | Monday-Saturday",
            languages = "English, हिंदी, मराठी, कोंकणी",
            email = "coojtrust@gmail.com",
            website = "https://coojgoa.org/"
        )
    )


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                backgroundColor = Color(31, 31, 37),
                elevation = 5.dp,
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 40.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        resourceItemList.forEach { item ->
                            ResourceItemView(item = item, navController = navController) {
                                navController.navigate(item.route)

                            }
                        }

                    }
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Support Resources",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 28.sp,
                            color = Color.White

                        )
                    )


                }


            }

        },

        bottomBar = {
            BottomNavigationBar(
                list = navItemList,
                navController = navController,
                onNavClick = {
                    if (it.route == Screen.ChatroomScreen.route) {
                        val intent = Intent(context, ChatroomActivity::class.java)
                        context.startActivity(intent)

                    } else {
                        navController.navigate(it.route)
                    }
                }
            )

        }


    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(49, 38, 45))

        ) {

            Image(
                painter = painterResource(id = R.drawable.chatbg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {

                supportItems.forEach {
                    ExpandableCard(item = it)
                }
                Spacer(modifier = Modifier.height(100.dp))

            }
        }
    }
}



@Composable
fun ExpandableCard(item: SupportItem) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF5F5)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.icon)
                            .crossfade(true)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = "Sample Image",
                        error = painterResource(id = android.R.drawable.ic_dialog_info)
                    )
//                    Icon(
//                        painter = painterResource(id = android.R.drawable.ic_dialog_info),
//                        contentDescription = "VTC Logo",
//                        modifier = Modifier.padding(8.dp)
//                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = item.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFE57373)
                    )

                    Text(
                        text = item.desc,
                        color = Color(0xFF1976D2),
                        fontSize = 14.sp
                    )

                    if(!expanded){
                        Icon(
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(end = 64.dp, top = 4.dp),
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }

                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Interactive Contact Items
                    InteractiveContactItem(
                        icon = android.R.drawable.ic_dialog_dialer,
                        label = "Helpline",
                        value = item.helpline,
                        actionIcon = android.R.drawable.ic_menu_call,
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:" + parsePhoneNumber(item.helpline))
                            }
                            context.startActivity(intent)
                        }
                    )

                    InteractiveContactItem(
                        icon = android.R.drawable.ic_menu_recent_history,
                        label = "Time",
                        value = item.time,
                        showAction = false
                    )

                    InteractiveContactItem(
                        icon = android.R.drawable.ic_dialog_info,
                        label = "Languages",
                        value = item.languages,
                        showAction = false
                    )

                    InteractiveContactItem(
                        icon = android.R.drawable.ic_dialog_email,
                        label = "Email",
                        value = item.email,
                        actionIcon = android.R.drawable.ic_dialog_email,
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:" + item.email)
                            }
                            context.startActivity(intent)
                        }
                    )

                    if(item.website.isNotEmpty()){
                        InteractiveContactItem(
                            icon = android.R.drawable.ic_menu_compass,
                            label = "Website",
                            value = item.website,
                            actionIcon = android.R.drawable.ic_menu_view,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(item.website)
                                }
                                context.startActivity(intent)
                            }
                        )
                    }

                    Icon(
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 4.dp),
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun InteractiveContactItem(
    icon: Int,
    label: String,
    value: String,
    actionIcon: Int? = null,
    showAction: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    val isClickable = onClick != null && showAction

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isClickable) {
                    Modifier.clickable(
                        onClick = onClick!!,
                        indication = rememberRipple(bounded = true),
                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                    )
                } else Modifier
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = label,
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = value,
                        color = Color(0xFF1976D2),
                        fontSize = 14.sp
                    )
                }
            }

            if (showAction && actionIcon != null) {
                Icon(
                    painter = painterResource(id = actionIcon),
                    contentDescription = "Action",
                    tint = Color(0xFF1976D2),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//
//fun ExpandableCardPreview() {
//    ExpandableCard()
//}



fun parsePhoneNumber(phone: String): String {
    // Regular expression to remove non-digit characters
    val cleanedPhone = phone.replace("[^\\d+]".toRegex(), "")
    // Handle cases where phone number starts with + for international numbers
    return if (cleanedPhone.startsWith("+")) cleanedPhone else "+91$cleanedPhone"
}