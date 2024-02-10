package com.example.keepsilent.design

import android.service.controls.actions.ModeAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.keepsilent.R
import com.example.keepsilent.Authentication.signIn.Userdata
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onSignOut : () -> Unit,
    onIconButtonClicked : () -> Unit,
    userdata: Userdata?,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            MainScreenTopBar(
                userdata = userdata,
                onSignOut = onSignOut
            )}
    ) {innerpadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerpadding)
        ){
            Column(
                modifier = modifier
            ){

            }
            //Add icon button to navigate to maps screen
            IconButton(
                onClick = { onIconButtonClicked()},
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 40.dp)
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "add icon",
                    tint = Color.Gray,
                    modifier = modifier
                        .size(80.dp)

                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopBar(
    userdata : Userdata?,
    onSignOut : () -> Unit,
    modifier : Modifier = Modifier
){
    var showDropDown by remember { mutableStateOf(false) }
    TopAppBar(
        navigationIcon = {
            UserProfile(
                userdata = userdata,
            )
        },
        title = {
            Text(
                text = stringResource(R.string.added_location),
                fontSize = 25.sp,
                fontWeight = FontWeight.Light,
                modifier = modifier
                    .padding(start = 10.dp, top = 5.dp)
            )
        },
        actions = {
            IconButton(
                onClick = {showDropDown = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "menu top bar"
                )
            }
            DropdownMenu(
                expanded = showDropDown,
                onDismissRequest = { showDropDown = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.sign_out),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.displaySmall,
                            fontSize = 15.sp
                        )},
                    onClick = { onSignOut() },
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }
    )
}

@Composable
fun UserProfile(
    userdata : Userdata?,
    modifier: Modifier = Modifier
){
    if(userdata?.userImageUrl != null){
        AsyncImage(
            model = userdata.userImageUrl,
            contentDescription = "profile image",
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .padding(top = 5.dp, start = 3.dp)
        )
    }else{
            Image(
                painter = painterResource(R.drawable.user),
                contentDescription = "user image",
                modifier = modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(top = 5.dp, start = 3.dp)
            )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    MainScreen(
        onSignOut = {},
        onIconButtonClicked = {},
        userdata = null
    )
}