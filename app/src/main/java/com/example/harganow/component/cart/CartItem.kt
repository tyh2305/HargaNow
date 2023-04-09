package com.example.harganow.component.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.harganow.component.general.OnlineImage
import com.example.harganow.component.general.StateCircle
import com.example.harganow.model.Item
import com.example.harganow.ui.theme.OpenSansFamily
import com.example.harganow.ui.theme.VerifyComposeTheme

fun CartItemText() {}

@Composable
fun CartItemCard(
    item: Item
) {
    var isChosen: Boolean = false
    var price: Double = 123.0
    val toggleState = {
        isChosen = !isChosen
    }

    // UI
    Box(
        modifier = Modifier
            .background(color = Color.White)
    ) {

        Row(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
            content = {
                // Toggle button
                StateCircle(isChosen = isChosen, onClick = toggleState)
                // Image
                OnlineImage(imageUrl = item.img)
                // Information
                Column(
                    content = {
                        // Name
                        Text(
                            text = item.name,
                            fontFamily = OpenSansFamily
                        )
                        // Price
                        Text(
                            text = "RM" + price.toString(),
                            fontFamily = OpenSansFamily,
                            fontWeight = FontWeight.Light
                        )
                    }
                )
            }
        )
    }

}

@Preview
@Composable
fun CartItemCardPreview() {
    CartItemCard(
        item = Item(
            id = "1",
            name = "Apple",
            unit = "kg",
            group = "Fruit",
            category = "Fruit",
        )
    )
}
