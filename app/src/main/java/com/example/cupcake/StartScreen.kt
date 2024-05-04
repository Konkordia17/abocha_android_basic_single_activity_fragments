package com.example.cupcake

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cupcake.model.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController, viewModel: OrderViewModel) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.pink_600),
                    titleContentColor = colorResource(id = R.color.white),
                ),
                title = {
                    Text(stringResource(id = R.string.app_name))
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.cupcake),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(300.dp),
                contentScale = ContentScale.Inside
            )
            Text(
                text = stringResource(id = R.string.order_cupcakes),
                color = colorResource(id = R.color.material_on_background_emphasis_medium),
                textAlign = TextAlign.Center,
                letterSpacing = 0.00735294118.sp,
                fontSize = 34.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            CupcakeButton(
                textId = R.string.one_cupcake,
                quantity = 1,
                viewModel = viewModel,
                navController = navController
            )
            CupcakeButton(
                textId = R.string.six_cupcakes,
                quantity = 6,
                viewModel = viewModel,
                navController = navController
            )
            CupcakeButton(
                textId = R.string.twelve_cupcakes,
                quantity = 12,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@Composable
fun CupcakeButton(
    textId: Int, quantity: Int, viewModel: OrderViewModel, navController: NavController
) {
    TextButton(
        onClick = {
            viewModel.setQuantity(quantity)
            if (viewModel.hasNoFlavorSet()) {
                viewModel.setFlavor("Vanilla")
            }
            navController.navigate(NavigationItem.Flavor.route)
        },
        Modifier
            .padding(top = 8.dp)
            .background(
                color = colorResource(id = R.color.pink_600), shape = RoundedCornerShape(5.dp)
            )
            .defaultMinSize(minWidth = 250.dp),
    ) {
        Text(
            text = stringResource(id = textId).uppercase(),
            color = colorResource(id = R.color.white)
        )
    }
}