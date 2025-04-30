package com.gulsenurgunes.furfriends.ui.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.common.UIState
import com.gulsenurgunes.furfriends.domain.model.User
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.auth.components.DividerWithText
import com.gulsenurgunes.furfriends.ui.auth.components.HeaderImage
import com.gulsenurgunes.furfriends.ui.auth.components.LabeledTextField
import com.gulsenurgunes.furfriends.ui.auth.components.ScreenHeader
import com.gulsenurgunes.furfriends.ui.auth.components.SocialIconsRow
import com.gulsenurgunes.furfriends.ui.auth.components.TextWithAction

@Composable
fun SignUpScreen(
    onSignUpSuccess: (User) -> Unit = {},
    onSignInClick: () -> Unit = {},
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {

    val email = signUpViewModel.e_mail
    val password = signUpViewModel.password
    val name = signUpViewModel.name
    val signUpState = signUpViewModel.signUpState

    LaunchedEffect(signUpState) {
        if (signUpState is UIState.Success) {
            onSignUpSuccess((signUpState).user)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderImage(
            R.drawable.animal2,
            "Sign Up Header Image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            ScreenHeader(
                title = "Create Your Account",
                subtitle = "Welcome back! Please enter your details"
            )
            Spacer(modifier = Modifier.height(24.dp))
            LabeledTextField(
                label = "Name *",
                value = name,
                onValueChange = { signUpViewModel.name }
            )
            LabeledTextField(
                label = "Email Address *",
                value = email,
                onValueChange = { signUpViewModel.onEmailChange(it) },
            )
            LabeledTextField(
                label = "Password *",
                value = password,
                onValueChange = { signUpViewModel.onPasswordChange(it) },
                isPassword = true
            )
            CheckboxConditions()
            AuthButton(
                text = "Sign Up",
                onClick = { signUpViewModel.onSignUpClick() },
                modifier = Modifier.padding(top = 16.dp)
            )
            DividerWithText("Or Sign Up With")
            SocialIconsRow()
            TextWithAction(
                text = "Already have and account? ",
                actionText = "Sign In",
                onActionClick = onSignInClick
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignUpPreview() {
//    SignUpScreen()
//}