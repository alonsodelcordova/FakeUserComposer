package com.jacs.cordova.fakeusercomposer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.jacs.cordova.fakeusercomposer.model.User
import com.jacs.cordova.fakeusercomposer.ui.theme.FakeUserComposerTheme
import com.jacs.cordova.fakeusercomposer.ui.viewmodels.UserViewModel
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FakeUserComposerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}


@Composable
fun MyApp( viewModel: UserViewModel = hiltViewModel()) {

    val listUsers by viewModel.users.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = false)

    MyAppComponent (
        onClick = {
            viewModel.addUser()
        },
        users = listUsers,
        isLoading = isLoading,
        onDeleteUser = {
            viewModel.deleteUser(it)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppComponent(
    onClick: () -> Unit,
    users: List<User> = emptyList(),
    isLoading: Boolean = false,
    onDeleteUser: (User) -> Unit = {}
){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Fake User Composer", fontWeight = FontWeight.Bold)
                },
                actions = {
                    IconButton(onClick = {
                        onClick()
                    }) {
                        Icon(Icons.Default.Add, "add")
                    }
                }
            )
        }
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(top = 70.dp)
        ){
            var itemCount = users.size
            if (isLoading) {
                itemCount++
            }

            items(count = itemCount) { index ->
                var auxIndex = index
                if (isLoading){
                    if (auxIndex == 0){
                        return@items LoadingCard()
                    }
                    auxIndex--
                }

                val user = users[auxIndex]
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation =  CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                        .testTag("loadingCard")
                ){
                    Row (
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Image(
                            painter =  rememberImagePainter(
                                data = user.thumbnail,
                                builder = {
                                    placeholder(R.drawable.placeholder)
                                    error(R.drawable.placeholder)
                                }
                            ),
                            contentDescription = user.name,
                            modifier = Modifier.size(50.dp)
                        )
                        SpacerCom(8)
                        Column (modifier = Modifier.weight(1f)){
                            Text(text = "${user.name} ${user.lastName}", fontWeight = FontWeight.Bold)
                            Text(text = user.city)
                        }
                        SpacerCom(8)
                        IconButton(onClick = {
                            onDeleteUser(user)
                            }
                        ) {
                            Icon(Icons.Default.Delete, "add")
                        }
                    }
                }


            }
        }
    }
}


@Composable
fun LoadingCard(){
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .testTag("loadingCard")
    ) {
        Row (
            modifier = Modifier
                .padding(8.dp)
        ) {
            ImageLoading()
            SpacerCom(8)
            Column {
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
                SpacerCom(10)
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )

            }
        }
    }
}

@Composable
fun ImageLoading(){
    Box(modifier = Modifier.shimmer()){
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FakeUserComposerTheme {
        MyAppComponent(onClick = {} , isLoading = true)
    }
}

@Composable
fun SpacerCom(size:Int = 0) = Spacer(modifier = Modifier.size(size.dp))


