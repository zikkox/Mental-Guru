package com.example.mentalguru.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {

    val signupEmail by viewModel.signupEmail.collectAsState()
    val signupPassword by viewModel.signupPassword.collectAsState()
    val signupRepeatPassword by viewModel.signupRepeatPassword.collectAsState()
    val signupState by viewModel.signupState.collectAsState()

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

            //Signin text
            Text(
                text = AnnotatedString("Sign Up"),
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = AnnotatedString("Sign up now for free and start meditating, and explore Mental Guru."),
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
                value = signupEmail,
                onValueChange = { viewModel.onSignupEmailChange(it) },
                label = "Email Address",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(40.dp))

            //Password text field
            CustomTextField(
                value = signupPassword,
                onValueChange = { viewModel.onSignupPasswordChange(it) },
                label = "Password",
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(40.dp))

            //Repeat Password text field
            CustomTextField(
                value = signupRepeatPassword,
                onValueChange = { viewModel.onSignupRepeatPasswordChange(it) },
                label = "Repeat Password",
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(29.dp))

            //Signup button
            Button(
                onClick = {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    viewModel.signup()
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
                    text = "SIGNUP",
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 3.5.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            //Login text
            Row(modifier = Modifier
                .padding(start = 24.dp)
                .clickable { navController.navigate(Screen.Login.route) }
            ) {
                Text(
                    text = AnnotatedString("Already have an account?"),
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W400
                    )
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = AnnotatedString("Sign In"),
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        LoadingComponent(isLoading = signupState is AuthViewModel.SignupState.Loading)

        SnackbarHostComponent(snackbarHostState)
    }

    //Observe login state and navigate only on success
    LaunchedEffect(signupState) {
        when (signupState) {
            is AuthViewModel.SignupState.Success -> {
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


@Preview
@Composable
fun SignupScreenPreview() {
    SignUpScreen(navController = rememberNavController(), viewModel = viewModel<AuthViewModel>())
}