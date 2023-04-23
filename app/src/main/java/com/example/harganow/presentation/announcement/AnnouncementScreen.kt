package com.example.harganow.presentation.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.harganow.data.repository.AnnouncementRepository
import com.example.harganow.domain.model.Announcement
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.presentation.components.Header
import com.example.harganow.ui.theme.Orange

@Composable
fun AnnouncementCard(title:String?, description:String?, onClick: () -> Unit)
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
                    Text(text = title?:"",
                        color = Orange,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = description?:"",
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
    val announcementRepository = AnnouncementRepository();
//  val announcementData =
//        remember { mutableStateOf<DataOrException<List<Announcement>, Exception>>(DataOrException()) }
    var announcements by remember { mutableStateOf(emptyList<Announcement>()) }



    LaunchedEffect(Unit) {
        announcementRepository.getAllAnnouncement().data?.let {
            announcements = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Header(
            title = "Announcement",
            titleSize = 64,
            navigateToPreviousStack = navigateToPreviousStack
        )

        // TODO: Take data from database and create announcement card for each data

//        AnnouncementCard(
//            title = "Lorem ipsum",
//            detail = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor." +
//                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor." +
//                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor.",
//            ) {
//        }
//        AnnouncementCard(
//            title = "Lorem ipsum",
//            detail = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor." ,
//            ) {
//        }
//        AnnouncementCard(
//            title = "Lorem ipsum",
//            detail = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eleifend pulvinar auctor." ,
//            ) {
//        }
//        when {
//            announcementData.value.data != null -> {
//                announcementData.value.data?.forEach { announcement ->
//                    AnnouncementCard(
//                        title = announcement.title,
//                        description = announcement.description,
//                        onClick = { /* Handle click here */ }
//                    )
//                }
//            }
//            announcementData.value.exception != null -> {
//                // Handle exception here
//            }
//            else -> {
//                // Show loading indicator here
//            }
//        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            items(announcements.size) { announcement ->
                AnnouncementCard(
                    title = announcements[announcement].title,
                    description = announcements[announcement].description,
                    onClick = {}
                )
            }
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