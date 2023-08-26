package com.example.shoppinglist.domain.usecases

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {
    suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}