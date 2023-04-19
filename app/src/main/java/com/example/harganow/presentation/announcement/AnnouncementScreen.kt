package com.example.harganow.presentation.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harganow.R
import com.example.harganow.presentation.user.Header
import com.example.harganow.ui.theme.Orange

@Composable
fun AnnouncementCard(title:String, detail:String, onClick: () -> Unit)
{
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(){
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ){
                Icon(
                    painter = painterResource(R.drawable.campaign_black_24dp),
                    contentDescription = "Announcement Icon",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .align(Alignment.CenterVertically)
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = title,
                        color = Orange,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = detail
                    )
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black.copy(alpha = 0.2f)))
        }
    }
}

@Composable
fun AnnouncementScreen(
    navigateToPreviousStack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(title = "Reset Password", titleSize = 64, navigateToPreviousStack = navigateToPreviousStack)

        // TODO: Take data from database and create annoucement card for each data
        AnnouncementCard(
            title = "Lorem ipsum",
            detail = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor." +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor." +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor.",
            ) {
        }
        AnnouncementCard(
            title = "Lorem ipsum",
            detail = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor." ,
            ) {
        }
        AnnouncementCard(
            title = "Lorem ipsum",
            detail = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor." ,
            ) {
        }
    }
}

@Preview
@Composable
fun AnnouncementScreenPreview() {
    AnnouncementScreen(
        navigateToPreviousStack = {  }
    )
}