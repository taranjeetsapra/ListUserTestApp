package com.example.listusertestapp.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.listusertestapp.data.model.UserInfo

/**
 * Created by Taranjeet Singh on 15/10/25.
 */

@Composable
fun DetailScreen(
    navHostController: NavHostController, navGraphBuilder: NavGraphBuilder,
    userInfo: UserInfo,
    onClickListener: () -> Unit
) {
    Log.e("TAG","sdfkdslkfhkdsf: ${userInfo.name}")
    ProjectListColumn(userInfo)
}




@Composable
fun DetailRow(header: String, text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Header (Left side - Bold, small, uppercase)
        Text(
            text = header.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = Color.Gray
            ),
            modifier = Modifier.weight(0.6f) // Fixed width for alignment
        )
        Spacer(modifier = Modifier.width(8.dp))

        // Detail Text (Right side - Readable, medium font)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif, // Different font family for contrast
                color = Color.DarkGray
            ),
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}

// 4. Composable for the Single Card Item
@Composable
fun ProjectCard(userInfo: UserInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Title
            Text(
                text = userInfo.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            CoilImageExample(
                imageUrl = userInfo.photo,
                contentDes = "Contact image for ${userInfo.name}",
                Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    // .clip(MaterialTheme.shapes.extraLarge) // Apply corner radius
                    .padding(start = 8.dp)
            )
            // Image (Full width, above details)
            /*Image(
                painter = painterResource(id = project.imageResId),
                contentDescription = "Project image for ${project.title}",
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )*/

            Spacer(modifier = Modifier.height(8.dp))
            // Details Section
            Column(modifier = Modifier.padding(start = 4.dp, end = 4.dp).fillMaxWidth()) {

                // Details using the reusable DetailRow
                DetailRow(header = "Id: ", text = userInfo.id.toString())
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(header = "company:", text = userInfo.company)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(header = "username:", text = userInfo.username)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(header = "email:", text = userInfo.email)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(header = "address:", text = userInfo.address)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(header = "zip:", text = userInfo.zip)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(header = "state:", text = userInfo.state)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(header = "country:", text = userInfo.company)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow(header = "phone:", text = userInfo.phone)
                Spacer(modifier = Modifier.height(8.dp))

                // Description (Treated as a dedicated block)
                /* Text(
                     text = "Ither:",
                     style = MaterialTheme.typography.labelMedium.copy(
                         fontWeight = FontWeight.Black,
                         fontSize = 10.sp,
                         color = Color.Gray
                     )
                 )
                 Text(
                     text = "zdfsdfhsldkfhksd",
                     style = MaterialTheme.typography.bodySmall.copy(
                         fontFamily = FontFamily.Serif,
                         color = Color.Black
                     ),
                     modifier = Modifier.padding(top = 4.dp)
                 )*/
            }
        }
    }
}

// 5. Composable for the Main Column List
@Composable
fun ProjectListColumn(userInfo: UserInfo) {
    // For a short list, a Column with verticalScroll is fine.
    // Use LazyColumn for long lists!
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProjectCard(userInfo = userInfo)
    }
}


// 6. Preview Function
@Preview(showBackground = true)
@Composable
fun PreviewProjectListColumn() {
    MaterialTheme {
        val userInfo =  UserInfo(
            id = 1,
            name = "Cary Mueller5",
            company = "Thiel - Quigley",
            username = "Mossie.Kunde42",
            email = "Myrna.Raynor76@gmail.com",
            address = "8713 Hillard Land",
            zip = "30995",
            state = "Maine",
            country = "Argentina",
            phone = "896.502.2683",
            photo = "https://json-server.dev/ai-profiles/66.png"
        )
        ProjectListColumn(userInfo)
    }
}