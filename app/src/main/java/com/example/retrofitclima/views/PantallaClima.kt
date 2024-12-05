package com.example.retrofitclima.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.retrofitclima.api.NetworkResponse
import com.example.retrofitclima.viewmodel.ClimaViewModel

@Composable
fun PantallaClima(viewModel: ClimaViewModel, modifier: Modifier) {
    //estado para nuestro textfield
    var ciudad by remember { mutableStateOf("") }
    //este es el live data que trae la resp
    val resultado = viewModel.resultado.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(value = ciudad, onValueChange = {
                ciudad = it
            }, label = {
                Text("Clima para la ciudad")
            })
            IconButton(onClick = {
                viewModel.getData(ciudad)
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "boton buscar")
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (val result = resultado.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.mensaje)
                }

                NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResponse.Success -> {
                    //aqui el codigo del composable correcto
                    Column {
                        Text(
                            text = result.data.name,
                            style = MaterialTheme.typography.displayMedium
                        )
                        Text(
                            text = result.data.main.temp.toString(),
                            style = MaterialTheme.typography.displayLarge
                        )

                        Text(text = "Humedad ${result.data.main.humidity}")
                        Text(text = "Temperatura Máxima ${result.data.main.temp_max}")



                        AsyncImage(
                            modifier= Modifier.size(256.dp),
                            model = "https://openweathermap.org/img/wn/${result.data.weather.get(0).icon}@2x.png",
                            contentDescription = "icono del clima"
                        )
                    }


                }

                null -> {}
            }
        }
    }
}
