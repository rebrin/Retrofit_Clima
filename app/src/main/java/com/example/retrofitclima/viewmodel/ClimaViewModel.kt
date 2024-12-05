package com.example.retrofitclima.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitclima.api.Instanciaretrofit
import com.example.retrofitclima.api.NetworkResponse
import com.example.retrofitclima.models.ClimaModel
import kotlinx.coroutines.launch

class ClimaViewModel: ViewModel() {
    private val climaAPI= Instanciaretrofit().climaAPI

    private val _clima_res= MutableLiveData<NetworkResponse<ClimaModel>>()
    val resultado=_clima_res

    fun getData(ciudad: String){
        _clima_res.value= NetworkResponse.Loading
        try{
            viewModelScope.launch{
                //paso los parametros para mi llamada
                val res=climaAPI.getClima("4c6d6e126cf504e942293dfb55ae0f7e",ciudad,"metric")//lanza la llamada
                if(res.isSuccessful){
                    res.body()?.let {
                        _clima_res.value= NetworkResponse.Success(it)
                    }
                }else{
                    _clima_res.value= NetworkResponse.Error("Algo salio mal, no podemos regresar los datos")
                }
            }
        }catch (e: Exception){
            _clima_res.value= NetworkResponse.Error("Algo salio mal, no podemos regresar los datos")
        }
    }
}