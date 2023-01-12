package com.gallardo.shoppinglist.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListPaperSheetLine(
    description: String,
    quantity: String,
    penColor: Color,
    onDescriptionChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.End) {
        Text(" • ", color = penColor)
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = description,
            onValueChange = onDescriptionChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = penColor
            ),
        )
        BasicTextField(
            value = quantity,
            onValueChange = onQuantityChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = penColor, textAlign = TextAlign.Center
            ), modifier = Modifier.width(70.dp)
        )
    }
}