package uz.fayzullo.agrobank.presentation.screens.language

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uz.fayzullo.agrobank.R

@Composable
fun LanguageScreen(navController: NavController) {
    val context= LocalContext.current
    Column() {
        Image(
            painter = painterResource(
                id = R.drawable.agrobank
            ),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.padding(80.dp))
        LanguageItem(image = R.drawable.uzbek, title = "O'zbek tili",onClick = {navController.navigate("register")})
        LanguageItem(image = R.drawable.english, title = "English",onClick = {Toast.makeText(context,"Oka hali ishlamadik",Toast.LENGTH_SHORT).show()})
        LanguageItem(image = R.drawable.rus, title = "Русский язык",onClick = { Toast.makeText(context,"Oka hali ishlamadik",Toast.LENGTH_SHORT).show()})
    }
}

@Composable
fun LanguageItem(
    image: Int,
    title: String,
    onClick:()-> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(32.dp))
            .clickable{onClick()},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .weight(1f)
                .weight(1f)
        )
        Icon(
            Icons.Default.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )

    }
}


//@Preview
//@Composable
//fun LanguageItemPre(){
//    LanguageItem()
//}