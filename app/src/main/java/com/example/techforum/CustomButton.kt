package com.example.techforum


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.techforum.ui.theme.Blue



@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(Blue),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 8.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 8.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, color = Blue, modifier = Modifier.padding(vertical = 8.dp))
    }
}