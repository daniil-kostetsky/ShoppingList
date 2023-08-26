package com.example.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.presentation.viewmodels.MainViewModel
import com.example.shoppinglist.presentation.viewmodels.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    @Binds
    fun bindShopItemViewModule(viewModel: ShopItemViewModel): ViewModel

}