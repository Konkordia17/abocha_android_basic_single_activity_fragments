package com.example.cupcake

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cupcake.model.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(viewModel: OrderViewModel, doOnSend: () -> Unit, navController: NavController) {
    val price by viewModel.priceFlow.collectAsState()
    val quantity by viewModel.quantity.collectAsState()
    val flavor by viewModel.flavor.collectAsState()
    val date by viewModel.date.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.pink_600),
                    titleContentColor = colorResource(id = R.color.white),
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = colorResource(id = R.color.white)
                        )
                    }
                },
                title = {
                    Text(stringResource(id = R.string.order_summary))
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            CategoryItem(
                titleId = R.string.quantity,
                description = quantity.toString()
            )
            CategoryItem(titleId = R.string.flavor, description = flavor)
            CategoryItem(
                titleId = R.string.pickup_date,
                description = date.orEmpty()
            )

            Text(
                text = stringResource(
                    id = R.string.total_price,
                    viewModel.getStringPrice(price = price)
                ).uppercase(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.End,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            )

            OutlinedButton(
                onClick = doOnSend,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, color = colorResource(id = R.color.pink_600)),
                colors =
                ButtonDefaults.outlinedButtonColors(
                    containerColor = colorResource(id = R.color.pink_600)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.send).uppercase(),
                    color = colorResource(id = R.color.white)
                )
            }

            OutlinedButton(
                onClick = {
                    viewModel.resetOrder()
                    navController.popBackStack(
                        route = NavigationItem.Start.route,
                        inclusive = false
                    )
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, color = Color.Gray.copy(alpha = 0.2f)),
                colors =
                ButtonDefaults.outlinedButtonColors(
                    containerColor = colorResource(id = R.color.white)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.cancel).uppercase(),
                    color = colorResource(id = R.color.pink_600)
                )
            }
        }
    }
}

@Composable
fun CategoryItem(titleId: Int, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(id = titleId).uppercase(),
            fontSize = 18.sp,
        )
        Text(
            text = description,
            fontWeight = FontWeight.W800,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
        Divider(modifier = Modifier.padding(top = 16.dp))
    }
}
