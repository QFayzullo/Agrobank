package uz.fayzullo.agrobank.presentation.screens.register

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun RegisterCode(
    phoneNumber: String,
    navController: NavController,
    viewModel: RegisterViewModel = viewModel()
) {
    var otpCode by remember { mutableStateOf("") }
    var timerSeconds by remember { mutableIntStateOf(59) }
    var isTimerRunning by remember { mutableStateOf(true) }

    val fullPhone = remember(phoneNumber) { "+998$phoneNumber" }
    val maskedPhone = remember(phoneNumber) {
        formatPhoneMask(phoneNumber)
    }

    val verifyState = viewModel.verifyState
    val resendState = viewModel.uiState

    // Kod to'g'ri tasdiqlangach - keyingi ekranga o'tamiz
    LaunchedEffect(verifyState) {
        if (verifyState is RegisterUiState.Success) {
            navController.navigate("registerPassword")
        }
    }

    LaunchedEffect(key1 = isTimerRunning, key2 = timerSeconds) {
        if (isTimerRunning && timerSeconds > 0) {
            delay(1000L)
            timerSeconds--
        } else if (timerSeconds == 0) {
            isTimerRunning = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Agrobank",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Tasdiqlash kodini kiriting",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "6 xonali kod $maskedPhone raqamiga\nyuborildi",
                style = TextStyle(fontSize = 18.sp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            BasicTextField(
                value = otpCode,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() } && newValue.length <= 6) {
                        otpCode = newValue
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                decorationBox = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(6) { index ->
                            val char = when {
                                index < otpCode.length -> otpCode[index].toString()
                                else -> ""
                            }
                            val isFocused = otpCode.length == index

                            Box(
                                modifier = Modifier
                                    .size(width = 46.dp, height = 54.dp)
                                    .border(
                                        width = if (isFocused) 2.dp else 1.dp,
                                        color = when {
                                            isFocused -> Color(0xFF0FE160)
                                            char.isNotEmpty() -> Color(0xFF0FE160)
                                            else -> Color.LightGray
                                        },
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = char,
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            if (isTimerRunning) {
                val formattedTime = String.format("%02d:%02d", timerSeconds / 60, timerSeconds % 60)
                Text(
                    text = "Kodni $formattedTime soniyadan so'ng olasiz",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                )
            } else if (resendState is RegisterUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text(
                    text = "Kod kelmadimi? Kodni qayta yuborish",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xFF0FE160),
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable {
                        viewModel.sendOtp(fullPhone)
                        timerSeconds = 59
                        isTimerRunning = true
                        otpCode = ""
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tasdiqlashdagi xatolik (masalan noto'g'ri OTP)
            if (verifyState is RegisterUiState.Error) {
                Text(
                    text = verifyState.message,
                    style = TextStyle(fontSize = 15.sp, color = Color.Red),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        Button(
            onClick = {
                if (otpCode.length == 6) {
                    viewModel.verifyOtp(fullPhone, otpCode)
                }
            },
            enabled = otpCode.length == 6 && verifyState !is RegisterUiState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0FE160),
                disabledContainerColor = Color(0xFFCCCCCC)
            )
        ) {
            if (verifyState is RegisterUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Tasdiqlash",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

fun formatPhoneMask(phone: String): String {
    val cleanDigits = phone.filter { it.isDigit() }
    return if (cleanDigits.length == 9) {
        val operator = cleanDigits.substring(0, 2)
        val lastTwo = cleanDigits.substring(7, 9)
        "+998 $operator *** ** $lastTwo"
    } else {
        "+998 $phone"
    }
}