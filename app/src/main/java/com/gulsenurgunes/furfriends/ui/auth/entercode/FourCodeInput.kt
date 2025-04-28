package com.gulsenurgunes.furfriends.ui.auth.entercode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun FourCodeInput(
    modifier: Modifier = Modifier,
    onComplete: (String) -> Unit = {}
) {
    val digits = remember { mutableStateListOf("", "", "", "") } // List of 4 digits
    val focusRequesters = List(4) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        digits.forEachIndexed { index, digit ->
            OutlinedTextField(
                value = digit,
                onValueChange = { value ->
                    val filtered = value.filter { it.isDigit() }.takeLast(1) // Only keep the last digit
                    digits[index] = filtered

                    if (filtered.isNotEmpty()) {
                        if (index < focusRequesters.lastIndex) {
                            focusRequesters[index + 1].requestFocus()
                        } else {
                            onComplete(digits.joinToString(""))
                            focusManager.clearFocus()
                        }
                    }
                },
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent {
                        if (it.key == Key.Backspace && digits[index].isEmpty() && index > 0) {
                            focusRequesters[index - 1].requestFocus()
                            digits[index - 1] = ""
                            true
                        } else false
                    },
                maxLines = 1,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
