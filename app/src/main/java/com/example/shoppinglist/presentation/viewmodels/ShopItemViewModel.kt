package com.example.shoppinglist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.usecases.AddShopItemUseCase
import com.example.shoppinglist.domain.usecases.EditShopItemUseCase
import com.example.shoppinglist.domain.usecases.GetShopItemUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val editShopItemUseCase: EditShopItemUseCase,
    private val getShopItemUseCase: GetShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _isCanBeClosedScreen = MutableLiveData<Unit>()
    val isCanBeClosedScreen: LiveData<Unit>
        get() = _isCanBeClosedScreen

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isInputValid = validationInputValue(name, count)
        if (isInputValid) {
            _shopItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(item)
                    closeScreen()
                }
            }
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val
                name = parseName(inputName)
        val count = parseCount(inputCount)
        val isInputValid = validationInputValue(name, count)
        if (isInputValid) {
            viewModelScope.launch {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase.addShopItem(shopItem)
                closeScreen()
            }
        }
    }

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val shopItem = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.value = shopItem
        }
    }

    private fun parseName(inputName: String?): String = inputName?.trim() ?: ""

    private fun parseCount(inputCount: String?): Int = inputCount?.trim()?.toIntOrNull() ?: 0

    private fun validationInputValue(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun closeScreen() {
        _isCanBeClosedScreen.value = Unit
    }
}