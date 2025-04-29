package com.gulsenurgunes.furfriends.ui.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.gulsenurgunes.furfriends.ui.auth.components.DividerWithText
import com.gulsenurgunes.furfriends.ui.auth.components.HeaderImage
import com.gulsenurgunes.furfriends.ui.auth.components.LabeledTextField
import com.gulsenurgunes.furfriends.ui.auth.components.ScreenHeader
import com.gulsenurgunes.furfriends.ui.auth.components.SocialIconsRow
import com.gulsenurgunes.furfriends.ui.auth.components.TextWithAction
import com.gulsenurgunes.furfriends.ui.auth.entercode.EnterCode
import com.gulsenurgunes.furfriends.ui.auth.enternewpassword.EnterNewPassword
import com.gulsenurgunes.furfriends.ui.auth.forgot.ForgotScreen
import com.gulsenurgunes.furfriends.ui.auth.signup.SignUpScreen

@Composable
fun SignInScreen(
    onForgotPasswordClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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
                title = "Sign In To Your Account",
                subtitle = "Welcome Back You've Been Missed"
            )
            Spacer(modifier = Modifier.height(24.dp))
            LabeledTextField(
                label = "Email Address *",
                value = email,
                onValueChange = { email = it }
            )
            LabeledTextField(
                label = "Password *",
                value = password,
                onValueChange = { password = it },
                isPassword = true
            )
            TextButton(
                onClick = { onForgotPasswordClick() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Forgot Password?")
            }
            Spacer(modifier = Modifier.height(16.dp))
            AuthButton(
                text = "Sign In",
                onClick = onHomeClick,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black,
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp),
                height = 48.dp
            )
            DividerWithText("Or Sign In With")
            SocialIconsRow()
            TextWithAction(
                text = "Not a member?",
                actionText = "Create an account",
                onActionClick = { onSignUpClick() })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen(onForgotPasswordClick = {}, onHomeClick = {}, onSignUpClick = {})
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUpScreen()
}


@Preview(showBackground = true)
@Composable
fun ForgotPreview() {
    ForgotScreen()
}

@Preview(showBackground = true)
@Composable
fun EnterCodenPreview() {
    EnterCode()
}

@Preview(showBackground = true)
@Composable
fun EnterNewPasswordPreview() {
    EnterNewPassword()
}