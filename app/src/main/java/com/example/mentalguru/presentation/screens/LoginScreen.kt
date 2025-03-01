package com.example.mentalguru.presentation.screens

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.R
import com.example.mentalguru.presentation.navigation.Screen
import com.example.mentalguru.presentation.ui.components.LoadingComponent
import com.example.mentalguru.presentation.ui.components.SnackbarHostComponent
import com.example.mentalguru.presentation.viewmodels.AuthViewModel
import com.example.mentalguru.presentation.viewmodels.AuthViewModelFactory

@Composable
fun LoginScreen(navController: NavController) {

    val application = LocalContext.current.applicationContext as Application
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(application = application)
    )

    val loginEmail by viewModel.loginEmail.collectAsState()
    val loginPassword by viewModel.loginPassword.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_account),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(top = 100.dp, start = 35.dp, end = 35.dp)) {

            //Logo
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(50.dp)
                    .scale(2f)
            )

            Spacer(modifier = Modifier.height(31.dp))

            //Sign in text
            Text(
                text = "Sign In",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Sign in now to access your exercises and saved music.",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Left
                )
            )

            Spacer(modifier = Modifier.height(55.dp))

            //Email text field
            CustomTextField(
                value = loginEmail,
                onValueChange = { viewModel.onLoginEmailChange(it) },
                label = "Email Address",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(40.dp))

            //Password text field
            CustomTextField(
                value = loginPassword,
                onValueChange = { viewModel.onLoginPasswordChange(it) },
                label = "Password",
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(9.dp))

            //Forgot password text
            ClickableText(
                text = AnnotatedString("Forgot Password?"),
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = { /* TODO: Handle forgot password */ }
            )

            Spacer(modifier = Modifier.height(29.dp))

            //Login button
            Button(
                onClick = {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    viewModel.login()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = "LOGIN",
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 3.5.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            //Signup text
            Row(modifier = Modifier
                .padding(start = 24.dp)
                .clickable { navController.navigate(Screen.Signup.route) }
            ) {
                Text(
                    text = "Don't have an account?",
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W400
                    )
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Sign Up",
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        LoadingComponent(isLoading = loginState is AuthViewModel.LoginState.Loading)

        SnackbarHostComponent(snackbarHostState)
    }


    //Observe login state and navigate only on success
    LaunchedEffect(loginState) {
        when (loginState) {
            is AuthViewModel.LoginState.Success -> {
                navController.navigate(Screen.Main.route)
            }

            else -> {}
        }
    }

    //Collect the SharedFlow and show Snackbar on error
    LaunchedEffect(Unit) {
        viewModel.errorMessage.collect { error ->
            snackbarHostState.showSnackbar(error)
        }
    }
}


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.LightGray, fontSize = 18.sp) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        singleLine = true,
        textStyle = androidx.compose.ui.text.TextStyle(
            color = Color.White,
            fontSize = 18.sp
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.White,
            focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray,
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        )
    )
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}