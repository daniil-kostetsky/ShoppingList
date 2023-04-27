package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
        val buttonAddShopItem = findViewById<FloatingActionButton>(R.id.fab_add_shop_item)
        buttonAddShopItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rcViewShopList = findViewById<RecyclerView>(R.id.rc_view_shop_list)
        shopListAdapter = ShopListAdapter()
        with(rcViewShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )

            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        setupOnLongClickListener()
        setupOnClickListener()
        setupSwipeListener(rcViewShopList)

    }

    private fun setupSwipeListener(rcViewShopList: RecyclerView) {
        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                mainViewModel.deleteShopItem(shopItem)
            }
        }

        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(rcViewShopList)
    }

    private fun setupOnClickListener() {
        shopListAdapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupOnLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            mainViewModel.changeEnableState(it)
        }
    }
}