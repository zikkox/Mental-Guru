package com.example.mentalguru.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalguru.R
import com.example.mentalguru.presentation.screens.CustomTextField
import com.example.mentalguru.presentation.viewmodels.ProfileViewModel

@Composable
fun EditProfileDialog(profileViewModel: ProfileViewModel) {
    var newLocation by remember { mutableStateOf("") }
    var newDob by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = { profileViewModel.hideEditDialog() },
        containerColor = colorResource(R.color.dark_green),
        title = {
            Text(
                text = "Edit Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextField(
                    value = newLocation,
                    onValueChange = {
                        if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                            newLocation = it
                        }
                    },
                    label = "Location",
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    value = newDob,
                    onValueChange = {
                        if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                            newDob = it
                        }
                    },
                    label = "Date of Birth",
                    keyboardType = KeyboardType.Number
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (newLocation.isNotBlank()) profileViewModel.updateLocation(newLocation)
                    if (newDob.isNotBlank()) profileViewModel.updateDob(newDob)
                    profileViewModel.hideEditDialog()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Text("Save", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = { profileViewModel.hideEditDialog() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Text("Cancel", color = Color.White)
            }
        })
}
