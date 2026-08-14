package uz.fayzullo.agrobank.presentation.screens.register

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RegisterPassword(navController: NavController) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val hasMinLength = password.length >= 8
    val hasDigit = password.any { it.isDigit() }
    val isPasswordMatch = password.isNotEmpty() && password == confirmPassword
    val isFormValid = hasMinLength && hasDigit && isPasswordMatch

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
                text = "Yangi parol yaratish",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Kirish uchun yangi parol o'rnating",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                shape = RoundedCornerShape(12.dp),
                label = { Text("Parolni kiriting") },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF0FE160),
                    unfocusedBorderColor = Color(0xFF0FE160),
                    focusedLabelColor = Color(0xFF9BA29E)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                shape = RoundedCornerShape(12.dp),
                label = { Text("Parolni tasdiqlang") },
                singleLine = true,
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(
                            imageVector = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF0FE160),
                    unfocusedBorderColor = Color(0xFF0FE160),
                    focusedLabelColor = Color(0xFF9BA29E)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Parol talablari:",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                RequirementItem(
                    text = "Kamida 8 ta belgi",
                    isSatisfied = hasMinLength
                )

                Spacer(modifier = Modifier.height(4.dp))

                RequirementItem(
                    text = "Kamida 1 ta katta harf A-Z",
                    isSatisfied = hasDigit
                )

                Spacer(modifier = Modifier.height(4.dp))

                RequirementItem(
                    text = "Kamida 1 ta kichik harf a-z",
                    isSatisfied = isPasswordMatch
                )
                Spacer(modifier = Modifier.height(4.dp))

                RequirementItem(
                    text = "faqat lotin harflari",
                    isSatisfied = isPasswordMatch
                )
                Spacer(modifier = Modifier.height(4.dp))

                RequirementItem(
                    text = "Kamida bitta raqam 0-9",
                    isSatisfied = isPasswordMatch
                )
                Spacer(modifier = Modifier.height(4.dp))

                RequirementItem(
                    text = "Kamida bitta belgi (masalan: !@#$%)",
                    isSatisfied = isPasswordMatch
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (isFormValid) {

                    }
                },
                enabled = isFormValid,
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

@Composable
fun RequirementItem(text: String, isSatisfied: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = if (isSatisfied) Color(0xFF0FE160) else Color.LightGray,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                color = if (isSatisfied) Color(0xFF0FE160) else Color.Gray
            )
        )
    }
}