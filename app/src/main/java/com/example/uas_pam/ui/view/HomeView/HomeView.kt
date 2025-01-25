package com.example.uas_pam.ui.view.HomeView


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.uas_pam.R
import com.example.uas_pam.costumwidget.TanamanTopAppBar
import com.example.uas_pam.ui.navigation.DestinasiNavigasi

object DestinasiSplash : DestinasiNavigasi {
    override val route = "splash"
    override val titleRes = "Tambah Tanaman"
}
@Composable
fun Splash(

    onTanamanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State for animation control
    var isVisible by remember { mutableStateOf(false) }

    // Trigger animation when the view is loaded
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(Color(0xFFC0C0C0)) // Background color changed to silver
    ) {
        // Title at the top
        Text(
            text = "Pengelolaan Pertanian",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp) // Reduced bottom padding
                .align(Alignment.CenterHorizontally)
        )

        // Main content with buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Buttons with regular design
            RegularButton(text = "Tanaman", icon = Icons.Filled.Edit, onClick = onTanamanClick)
        }
    }
}

@Composable
fun RegularButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE) // Regular color for the button
        ),
        shape = RoundedCornerShape(12.dp), // Rounded corners for rectangle
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .size(250.dp, 60.dp) // Increase width to make the button wider
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp) // Padding between icon and text
        ) {
            // Icon with regular size
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp) // Padding between icon and text
                    .size(30.dp), // Icon size
                tint = Color.White // White icon color
            )
            // Text
            Text(
                text = text,
                fontSize = 16.sp, // Adjust font size
                fontWeight = FontWeight.Bold,
                color = Color.White // White text color
            )
        }
    }
}