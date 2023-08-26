package com.example.shoppinglist.domain.usecases

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {
    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}