package com.example.cupcake

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cupcake.model.OrderViewModel
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlavorScreen(viewModel: OrderViewModel, navController: NavController) {
    val context = LocalContext.current
    val radioOptions = persistentListOf(
        context.getString(R.string.vanilla),
        context.getString(R.string.chocolate),
        context.getString(R.string.red_velvet),
        context.getString(R.string.salted_caramel),
        context.getString(R.string.coffee)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.pink_600),
                    titleContentColor = colorResource(id = R.color.white),
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.resetOrder()
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = colorResource(id = R.color.white)
                        )
                    }
                },
                title = {
                    Text(stringResource(id = R.string.choose_flavor))
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
            RadioButtonGroup(radioOptions = radioOptions,
                selectedItem = viewModel.flavor.value,
                doOnClick = { viewModel.setFlavor(it) })
            Divider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )
            SubtotalPrice(viewModel = viewModel)
            Buttons(doOnCancel = {
                viewModel.resetOrder()
                navController.popBackStack()
            }, doOnNext = {
                navController.navigate(NavigationItem.PickUp.route)
            })
        }
    }

}

@Composable
fun SubtotalPrice(viewModel: OrderViewModel) {
    val price by viewModel.priceFlow.collectAsState()
    Text(
        text = stringResource(id = R.string.subtotal_price, viewModel.getStringPrice(price)),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
        fontSize = 18.sp
    )
}

@Composable
fun RadioButtonGroup(
    radioOptions: List<String>,
    doOnClick: (String) -> Unit,
    selectedItem: String?
) {
    var selectedOptionId by remember { mutableStateOf(selectedItem ?: radioOptions[0]) }

    Column(modifier = Modifier) {
        radioOptions.forEach { text ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = text == selectedOptionId,
                    onClick = {
                        selectedOptionId = text
                        doOnClick.invoke(text)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun Buttons(doOnCancel: () -> Unit, doOnNext: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {

        OutlinedButton(
            onClick = doOnCancel,
            modifier = Modifier.weight(1f),
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
        Spacer(modifier = Modifier.width(16.dp))

        OutlinedButton(
            onClick = doOnNext,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, color = colorResource(id = R.color.pink_600)),
            colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = colorResource(id = R.color.pink_600)
            )
        ) {
            Text(
                text = stringResource(id = R.string.next).uppercase(),
                color = colorResource(id = R.color.white)
            )
        }
    }
}