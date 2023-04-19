package com.example.harganow.presentation.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harganow.ui.theme.Orange

@Composable
fun Paragraph(text: String)
{
    Text(
        text = text,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
    )
}


@Composable
fun PolicyScreen(
    navigateToPreviousStack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        Header(title = "Policy", titleSize = 64, navigateToPreviousStack = navigateToPreviousStack)

        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Term of Service",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                color = Orange,
                fontSize = 24.sp
            )

            Paragraph(
                text = "1. INTRODUCTION"
            )

            Paragraph(
                text = "1.1 Welcome to the HargaNow platform (the \"Site\"). Please read the following Terms of Service carefully before using this Site " +
                        "or opening a HargaNow account (\"Account\") so that you are aware of your legal rights and obligations with respect to HargaNow " +
                        "Mobile Malaysia Sdn. Bhd. (Company Registration No. 201501009497 (1134832-W)), its affiliates and/or subsidiaries (individually and " +
                        "collectively, \"HargaNow\", \"we\", \"us\" or \"our\"). The \"Services\" we provide or make available include (a) the Site, (b) the " +
                        "services provided by the Site and by HargaNow client software made available through the Site, and (c) all information, linked pages, " +
                        "features, data, text, images, photographs, graphics, music, sounds, video (including live streams), messages, tags, content, programming," +
                        " software, application services (including, without limitation, any mobile application services) or other materials made available through " +
                        "the Site or its related services (\"Content\"). Any new features added to or augmenting the Services are also subject to these Terms of Service. " +
                        "These Terms of Service govern your use of Services provided by HargaNow."
            )

            Paragraph(
                text = "1.2 The Services include an online platform service that provides a place and opportunity for the sale of goods between the buyer (“Buyer”) and the" +
                        " seller (“Seller”) (collectively “you”, “Users” or “Parties”). The actual contract for sale is directly between Buyer and Seller and HargaNow is not " +
                        "a party to that or any other contract between Buyer and Seller and accepts no obligations in connection with any such contract. Parties to such " +
                        "transaction will be entirely responsible for the sales contract between them, the listing of goods, warranty of purchase and the like. HargaNow is" +
                        " not involved in the transaction between Users. HargaNow may or may not pre-screen Users or the Content or information provided by Users. HargaNow " +
                        "reserves the right to remove any Content or information posted by you on the Site in accordance to Section 6.4 herein. HargaNow cannot ensure that " +
                        "Users will actually complete a transaction."
            )

            Paragraph(
                text = "1.3 Before becoming a User of the Site, you must read and accept all of the terms and conditions in, and linked to, these Terms of Service and you " +
                        "must consent to the processing of your personal data as described in the Privacy Policy linked hereto."
            )

            Paragraph(
                text = "1.4 HargaNow reserves the right to change, modify, suspend or discontinue all or any part of this Site or the Services at any time or upon notice as " +
                        "required by local laws. HargaNow may release certain Services or their features in a beta version, which may not work correctly or in the same way the " +
                        "final version may work, and we shall not be held liable in such instances. HargaNow may also impose limits on certain features or restrict your access " +
                        "to parts of, or the entire, Site or Services in its sole discretion and without notice or liability."
            )

            Paragraph(
                text = "1.2 The Services include an online platform service that provides a place and opportunity for the sale of goods between the buyer (“Buyer”) and the" +
                        " seller (“Seller”) (collectively “you”, “Users” or “Parties”). The actual contract for sale is directly between Buyer and Seller and HargaNow is not " +
                        "a party to that or any other contract between Buyer and Seller and accepts no obligations in connection with any such contract. Parties to such " +
                        "transaction will be entirely responsible for the sales contract between them, the listing of goods, warranty of purchase and the like. HargaNow is" +
                        " not involved in the transaction between Users. HargaNow may or may not pre-screen Users or the Content or information provided by Users. HargaNow " +
                        "reserves the right to remove any Content or information posted by you on the Site in accordance to Section 6.4 herein. HargaNow cannot ensure that " +
                        "Users will actually complete a transaction."
            )

            Paragraph(
                text = "1.2 The Services include an online platform service that provides a place and opportunity for the sale of goods between the buyer (“Buyer”) and the" +
                        " seller (“Seller”) (collectively “you”, “Users” or “Parties”). The actual contract for sale is directly between Buyer and Seller and HargaNow is not " +
                        "a party to that or any other contract between Buyer and Seller and accepts no obligations in connection with any such contract. Parties to such " +
                        "transaction will be entirely responsible for the sales contract between them, the listing of goods, warranty of purchase and the like. HargaNow is" +
                        " not involved in the transaction between Users. HargaNow may or may not pre-screen Users or the Content or information provided by Users. HargaNow " +
                        "reserves the right to remove any Content or information posted by you on the Site in accordance to Section 6.4 herein. HargaNow cannot ensure that " +
                        "Users will actually complete a transaction."
            )

            Paragraph(
                text = "1.5 HargaNow reserves the right to refuse to provide you access to the Site or Services or to allow you to open an Account for any reason."
            )

            Paragraph(
                text = "BY USING HargaNow SERVICES OR OPENING AN ACCOUNT, YOU GIVE YOUR IRREVOCABLE ACCEPTANCE OF AND CONSENT TO THE TERMS OF THIS AGREEMENT, " +
                        "INCLUDING THOSE ADDITIONAL TERMS AND CONDITIONS AND POLICIES REFERENCED HEREIN AND/OR LINKED HERETO."
            )
            
            Paragraph(text = "IF YOU DO NOT AGREE TO THESE TERMS, PLEASE DO NOT USE OUR SERVICES OR ACCESS THE SITE. IF YOU ARE UNDER THE AGE OF 18 OR THE LEGAL AGE FOR " +
                    "GIVING CONSENT HEREUNDER PURSUANT TO THE APPLICABLE LAWS IN YOUR COUNTRY (THE “LEGAL AGE”), YOU MUST GET PERMISSION FROM A PARENT OR LEGAL GUARDIAN " +
                    "TO OPEN AN ACCOUNT AND THAT PARENT OR LEGAL GUARDIAN MUST AGREE TO THE TERMS OF THIS AGREEMENT. IF YOU DO NOT KNOW WHETHER YOU HAVE REACHED THE " +
                    "LEGAL AGE, OR DO NOT UNDERSTAND THIS SECTION, PLEASE DO NOT CREATE AN ACCOUNT UNTIL YOU HAVE ASKED YOUR PARENT OR LEGAL GUARDIAN FOR HELP. IF YOU ARE " +
                    "THE PARENT OR LEGAL GUARDIAN OF A MINOR WHO IS CREATING AN ACCOUNT, YOU MUST ACCEPT THE TERMS OF THIS AGREEMENT ON THE MINOR'S BEHALF AND YOU WILL BE " +
                    "RESPONSIBLE FOR ALL USE OF THE ACCOUNT OR COMPANY SERVICES USING SUCH ACCOUNT, WHETHER SUCH ACCOUNT IS CURRENTLY OPEN OR CREATED LATER.")
       }
    }
}

@Preview
@Composable
fun PolicyScreenPreview() {
    PolicyScreen(
        navigateToPreviousStack = {  },
    )
}