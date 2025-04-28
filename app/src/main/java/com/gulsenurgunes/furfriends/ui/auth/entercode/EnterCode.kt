package com.gulsenurgunes.furfriends.ui.auth.entercode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.auth.components.HeaderImage
import com.gulsenurgunes.furfriends.ui.auth.components.ScreenHeader
import com.gulsenurgunes.furfriends.ui.auth.components.TextWithAction

@Composable
fun EnterCode() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderImage(R.drawable.animal1, "Sign In Header Image")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 96.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ScreenHeader(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                title = "Enter Code",
                subtitle = "An Authentication Code Has Sent To",
            )
            Spacer(modifier = Modifier.height(124.dp))
            FourCodeInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextWithAction("If you don!t receive code! ", "Resend Code", onActionClick = {})
        }
        Spacer(modifier = Modifier.height(56.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthButton(
                text = "Send Mail",
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextWithAction(
                text = "Back To ",
                actionText = "Sign In",
                onActionClick = { }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EnterCodenPreview() {
    EnterCode()
}