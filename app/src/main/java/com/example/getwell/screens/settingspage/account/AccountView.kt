package com.example.getwell.screens.settingspage.account

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.getwell.R
import com.example.getwell.authSystem.AuthUiClient
import com.example.getwell.authSystem.AuthViewModel
import com.example.getwell.authSystem.UserData
import com.example.getwell.data.Screen
import com.example.getwell.screens.customFont
import com.example.getwell.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AccountScreen(
    navController: NavHostController,
    authUiClient: AuthUiClient,
    userData: UserData?,
    viewModel: AuthViewModel ,
    homeViewModel : HomeViewModel

) {
    val auth = FirebaseAuth.getInstance()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color.Transparent, CircleShape)
                    .border(BorderStroke(2.dp, Color.White), CircleShape)
            ) {
                val userPic = userData?.profilePictureUrl
                if (userPic != null) {
                    AsyncImage(
                        model = userPic,
                        contentDescription = "profile picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop

                    )

                } else {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.profile_icon),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )

                }

            }
            Text(
                text = userData?.username ?: "Guest",
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 32.sp,
                    color = Color(255, 132, 86),
                    textAlign = TextAlign.Center
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }

        ItemCard(
            title = "Your Email: ",
            value = userData?.emailId ?: "",
            icon = Icons.Default.Email
        )

        if (userData != null) {
            UserInputForm(userData.emailId)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(50.dp)
                .background(Color(210, 213, 249), shape = RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    scope.launch {
                        authUiClient.signOut()
                        Toast
                            .makeText(
                                context,
                                "Signed Out Successfully",
                                Toast.LENGTH_LONG
                            )
                            .show()
                        homeViewModel.setDisplayState(true)
                        navController.navigate(Screen.SignInScreen.route)
                        viewModel.resetState()
                    }


                },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material.Text(
                text = "Sign Out",
                style = TextStyle(
                    fontFamily = customFont,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )
            )

        }


//        Box(
//            modifier = Modifier
//                .fillMaxWidth(0.5f)
//                .height(50.dp)
//                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
//                .border(
//                    BorderStroke(1.dp, Color.White),
//                    shape = RoundedCornerShape(10.dp)
//                )
//                .clickable {
////                    initiateAccountDeletion(auth, googleReauthLauncher, context)
//                },
//            contentAlignment = Alignment.Center
//        ) {
//            androidx.compose.material.Text(
//                text = "Permanent Delete",
//                style = TextStyle(
//                    fontFamily = customFont,
//                    color = Color.White,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 17.sp
//                )
//            )
//
//        }

    }
}


@Composable
fun ItemCard(
    title: String,
    value: String,
    icon: ImageVector,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = customFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color(31, 31, 37), RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color(111,111,111)
                    )
                )

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(111, 111, 111)
                )

            }

        }

    }

}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputForm(userId: String) {

    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    var phoneNumber by rememberSaveable { mutableStateOf(sharedPreferences.getString("phoneNumber$userId", "") ?: "") }
    var dateOfBirth by rememberSaveable { mutableStateOf(sharedPreferences.getString("dateOfBirth$userId", "") ?: "") }
    var isPhoneNumberSaved by rememberSaveable { mutableStateOf(phoneNumber.isNotEmpty()) }
    var isDobSaved by rememberSaveable { mutableStateOf(dateOfBirth.isNotEmpty()) }
    var showDatePicker by remember { mutableStateOf(false) }

    var showNum by remember { mutableStateOf(false) }
    var showDob by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Phone Number Input
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Phone Number",
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.White
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(31, 31, 37), RoundedCornerShape(3.dp))
                    .heightIn(min = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TextField(
                    value = phoneNumber,
                    singleLine = true,
                    onValueChange = {input->
                        val filteredInput = input.filter { it.isDigit() }.take(10)
                        phoneNumber = filteredInput
                        isPhoneNumberSaved = false
                        showNum = true
                    },
                    textStyle = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color(111,111,111)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Transparent),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                IconButton(
                    onClick = {
                        // Save to SharedPreferences
                        sharedPreferences.edit().putString("phoneNumber$userId", phoneNumber).apply()
                        isPhoneNumberSaved = true
                        showNum = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = if (isPhoneNumberSaved) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                    )
                }
            }
            if(showNum){

                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "Click to Save!",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        color = Color(111,111,111)
                    )
                )
            }
        }

        // Date of Birth Input
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Date of Birth",
                style = TextStyle(
                    fontFamily = customFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.White
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(31, 31, 37), RoundedCornerShape(3.dp))
                    .heightIn(min = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TextField(
                    value = dateOfBirth,
                    onValueChange = {

                    },
                    singleLine = true,
                    enabled = false,
                    textStyle = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color(111,111,111)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(31, 31, 37))
                        .clickable {
                            showDatePicker = true
                            showDob  = true
                                   },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledTextColor = MaterialTheme.colors.onSurface
                    ),
                )

                IconButton(
                    onClick = {
                        // Save to SharedPreferences
                        sharedPreferences.edit().putString("dateOfBirth$userId", dateOfBirth).apply()
                        isDobSaved = true
                        showDob = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = if (isDobSaved) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                    )
                }
            }

            if(showDob){

                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "Click to Save!",
                    style = TextStyle(
                        fontFamily = customFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        color = Color(111,111,111)
                    )
                )
            }
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Date(millis)
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        dateOfBirth = formatter.format(date)
                        isDobSaved = false
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}
