package com.example.cupcake

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cupcake.model.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickUpScreen(viewModel: OrderViewModel, navController: NavController) {
    val date by viewModel.date.collectAsState()
    val dateOptions by viewModel.dateOptions.collectAsState()
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
                    Text(stringResource(id = R.string.choose_pickup_date))
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            RadioButtonGroup(
                radioOptions = dateOptions,
                selectedItem = date ?: dateOptions[0],
                doOnClick = { viewModel.setDate(it) })
            Divider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )
            SubtotalPrice(viewModel = viewModel)
            Buttons(doOnCancel = {
                viewModel.resetOrder()
                navController.popBackStack(
                    route = NavigationItem.Start.route,
                    inclusive = false
                )
            },
                doOnNext = {
                    navController.navigate(NavigationItem.Summary.route)
                }
            )
        }
    }
}

