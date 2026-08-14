package uz.fayzullo.agrobank.presentation.screens.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RegisterScreen(navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }

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

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Ro'yxatdan o'tish",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Telefon raqamingizni kiriting:",
                style = TextStyle(
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = phoneNumber,
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                onValueChange = { phone ->
                    if (phone.all { it.isDigit() } && phone.length <= 9) {
                        phoneNumber = phone
                    }
                },
                shape = RoundedCornerShape(12.dp),
                label = { Text("Telefon raqam") },
                prefix = {
                    Text(
                        "+998 ",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF0FE160),
                    unfocusedBorderColor = Color(0xFF0FE160),
                    focusedLabelColor = Color(0xFF9BA29E)
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Tasdiqlash SMS-kodi Antifrod'dan yuboriladi",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    append("<<Keyingisi>> tugamasini bosish orqali\n")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF0BF565),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Ommaviy oferta")
                    }
                    append(" shartlariga rozilik\nbildirasiz")
                },
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (phoneNumber.length == 9) {
                        navController.navigate("registerCode/$phoneNumber")
                    }
                },
                enabled = phoneNumber.length == 9,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0FE160),
                    disabledContainerColor = Color(0xFFCCCCCC)
                )
            ) {
                Text(
                    text = "Keyingisi",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}