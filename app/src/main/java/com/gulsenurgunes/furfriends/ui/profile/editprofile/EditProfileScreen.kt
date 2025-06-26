package com.gulsenurgunes.furfriends.ui.profile.editprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.navigation.TopBar
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.auth.components.LabeledTextField

@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit = {},
) {
    var fullName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Edit Profile",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Box(contentAlignment = Alignment.BottomEnd) {
                    Image(
                        painter = painterResource(R.drawable.first),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.LightGray, CircleShape)
                    )
                    Icon( //Burada düzenleme yap
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Icon",
                        modifier = Modifier
                            .offset(x = (-4).dp, y = (-4).dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
                            .padding(6.dp),
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            LabeledTextField(
                label = "Full Name",
                value = fullName,
                onValueChange = { fullName = it },
            )
            Spacer(modifier = Modifier.height(12.dp))
            LabeledTextField(
                label = "Mobile Number",
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
            )
            Spacer(modifier = Modifier.height(12.dp))
            LabeledTextField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
            )
            Spacer(modifier = Modifier.height(12.dp))
            LabeledTextField(
                label = "Location",
                value = location,
                onValueChange = { location = it },
            )
            Spacer(modifier = Modifier.height(24.dp))
            AuthButton(
                "Update Profile",
                onClick = {}
            )
        }
    }
}