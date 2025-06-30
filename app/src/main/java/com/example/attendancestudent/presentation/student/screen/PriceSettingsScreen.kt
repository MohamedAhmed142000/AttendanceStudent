package com.example.attendancestudent.presentation.student.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.attendancestudent.data.local.entity.YearPriceEntity
@Composable
fun PriceSettingsScreen(
    prices: List<YearPriceEntity>,
    onPriceChange: (String, Int) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(prices) { item ->
            var priceText by remember { mutableStateOf(item.pricePerSession.toString()) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.year, modifier = Modifier.weight(1f))

                OutlinedTextField(
                    value = priceText,
                    onValueChange = {
                        priceText = it
                        it.toIntOrNull()?.let { newPrice ->
                            onPriceChange(item.year, newPrice)
                        }
                    },
                    label = { Text("السعر") },
                    modifier = Modifier.width(100.dp)
                )
            }
        }
    }
}