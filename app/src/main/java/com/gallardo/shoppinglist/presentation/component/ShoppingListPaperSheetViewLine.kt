package com.gallardo.shoppinglist.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListPaperSheetViewLine(
    description: String,
    quantity: String,
    penColor: Color,
    done: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        Text(" • ", color = penColor)
        Text(
            modifier = Modifier.weight(1f),
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = penColor,
                textDecoration = if (done) TextDecoration.LineThrough else TextDecoration.None
            ),

        )
        Text(
            modifier = Modifier.width(70.dp),
            text = quantity,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = penColor, textAlign = TextAlign.Center
            )
        )
    }
}