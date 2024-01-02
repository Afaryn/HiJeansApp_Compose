package com.example.subcompose.ui.screen.keranjang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subcompose.data.MenuRepository
import com.example.subcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class KeranjangViewModel ( val repository: MenuRepository):ViewModel() {
    private val _uiState: MutableStateFlow<UiState<KeranjangState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<KeranjangState>>
        get() = _uiState

    fun getAddedOrderMenu(){
        viewModelScope.launch {
            _uiState.value =UiState.Loading
            repository.getAddedOrderMenu()
                .collect{orderMenu->
                    val totalHarga = orderMenu.sumOf { it.menu.harga * it.count }
                    _uiState.value =UiState.Success(KeranjangState(orderMenu,totalHarga))
                }
        }
    }

    fun updateOrderMenu(menuId : Long, count: Int){
        viewModelScope.launch {
            repository.updateOrderMenu(menuId,count)
                .collect{isUpdate->
                    if (isUpdate){
                        getAddedOrderMenu()
                    }
                }
        }
    }
}