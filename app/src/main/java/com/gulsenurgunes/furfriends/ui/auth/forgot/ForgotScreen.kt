package com.gulsenurgunes.furfriends.ui.auth.forgot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.auth.components.HeaderImage
import com.gulsenurgunes.furfriends.ui.auth.components.LabeledTextField
import com.gulsenurgunes.furfriends.ui.auth.components.ScreenHeader
import com.gulsenurgunes.furfriends.ui.auth.components.TextWithAction

@Composable
fun ForgotScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        HeaderImage(R.drawable.animal3, "Forgot Header Image")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 240.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ScreenHeader(
                title = "Forgot Password?",
                subtitle = "Enter the email associated with your account and we will send and email to reset your password."
            )
            Spacer(modifier = Modifier.height(24.dp))
            LabeledTextField(
                label = "Email Address *",
                value = email,
                onValueChange = { email = it }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AuthButton(
                text = "Send Mail",
                onClick = {  },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(126.dp))
            TextWithAction(
                text = "Back To ",
                actionText = "Sign In",
                onActionClick = {  }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPreview() {
    ForgotScreen()
}