package com.example.listusertestapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.listusertestapp.data.model.UserInfo
import com.example.listusertestapp.presentation.sealed.ResourceState


/**
 * Created by Taranjeet Singh on 15/10/25.
 */


@Composable
fun ListScreen(
    navHostController: NavHostController, navGraphBuilder: NavGraphBuilder,
    innerPadding: PaddingValues,
    viewModel: UserViewModel,
    onClickListener: (contract: Bundle) -> Unit
) {
    // 1. Collect State
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        is ResourceState.Error -> {}
        is ResourceState.Idle -> {
            CircularProgressIndicator()
        }

        is ResourceState.Loading -> {
            LoadingScreen(innerPadding)
        }
        is ResourceState.Success<List<UserInfo>> -> {
            val dataVal = (state as ResourceState.Success<List<UserInfo>>).dataVal
            LazyColumn(
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(dataVal) { userInfo ->
                    ContactCard(userInfo, onClickListener)
                }
            }
        }
    }


}

@Composable
fun LoadingScreen(modifier: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(paddingValues = modifier)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        CircularProgressIndicator()
    }
}

//contact: Contact
@Composable
fun ContactCard(contract: UserInfo, onClickListener: (contract: Bundle) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                Log.e("TAG", "@@@@ userInfo: " + contract.name)
                val bundle: Bundle = Bundle().apply {
                    putParcelable("userData", contract)
                }
                onClickListener(bundle)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // Main Row: Left (Text) and Right (Image)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Side: Name, Company, Phone (Stacked in a Column)
            Column(
                modifier = Modifier.weight(1f) // Takes up remaining space
            ) {
                Text(
                    text = contract.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contract.company,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Phone: ${contract.phone}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }

            // Right Side: Image
            /* Image(
                 painter = painterResource(R.drawable.baseline_2k_24),//id = contact.imageResId
                 contentDescription = "Contact image for ${contract.name}",
                 modifier = Modifier
                     .size(60.dp)
                     .clip(MaterialTheme.shapes.extraSmall) // Apply corner radius
                     .padding(start = 8.dp) // Spacing from the text
             )*/

            CoilImageExample(
                contract.photo,
                "Contact image for ${contract.name}",
                Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.extraSmall) // Apply corner radius
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun CoilImageExample(imageUrl: String, contentDes: String, modifier: Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDes,
        modifier = modifier
    )
}