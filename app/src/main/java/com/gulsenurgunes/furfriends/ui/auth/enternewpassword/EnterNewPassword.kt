package com.gulsenurgunes.furfriends.ui.auth.enternewpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.auth.components.HeaderImage
import com.gulsenurgunes.furfriends.ui.auth.components.LabeledTextField
import com.gulsenurgunes.furfriends.ui.auth.components.ScreenHeader
import com.gulsenurgunes.furfriends.ui.auth.components.TextWithAction

@Composable
fun EnterNewPassword() {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderImage(R.drawable.animal1, "Sign In Header Image")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            ScreenHeader(
                title = "Enter New Password",
                subtitle = "Your new password must be different from previous used passwords."
            )
            Spacer(modifier = Modifier.height(24.dp))
            LabeledTextField(
                label = "Email Address *",
                value = password,
                onValueChange = { password = it }
            )
            LabeledTextField(
                label = "Confirm Password *",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isPassword = true
            )
            Spacer(modifier = Modifier.height(36.dp))
            AuthButton(
                text = "Sign In",
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black,
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp),
                height = 48.dp
            )
            TextWithAction(
                text = "Back To ",
                actionText = "Sign In",
                onActionClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnterNewPasswordPreview() {
    EnterNewPassword()
}