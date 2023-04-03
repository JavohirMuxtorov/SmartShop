package com.example.smartshop.utils

import com.example.smartshop.model.CartModel
import com.example.smartshop.model.TopProductModel
import com.orhanobut.hawk.Hawk

object PrefUtils {
    const val PREF_FAVORITES = "pref_favorites"
    const val PREF_CART = "pref_cart"

    fun setFavorite(item: TopProductModel){
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())

        if (items.filter { it == item.id }.firstOrNull() != null){
            items.remove(item.id)
        }else{
            items.add(item.id)
        }
        Hawk.put(PREF_FAVORITES, items)
    }
    fun getFavoriteList(): ArrayList<Int>{
        return Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
    }
    fun checkFavorites(item: TopProductModel): Boolean{
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }

    fun setCart(item: TopProductModel){
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf<CartModel>())
        val cart = items.filter { it.product_id == item.id }.firstOrNull()
        if (cart != null){
            if (item.cartCount>0){
                cart.count = item.cartCount
            }else{
                items.remove(cart)
            }
        }else{
            val newCart = CartModel(item.id, item.cartCount)
            items.add(newCart)
        }

        Hawk.put(PREF_CART, items)
    }


    fun getCartList(): ArrayList<CartModel>{
        return Hawk.get(PREF_CART, arrayListOf<CartModel>())
    }
    fun getCartCount(item: TopProductModel): Int{
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf<CartModel>())
        return items.filter { it.product_id == item.id }.firstOrNull()?.count ?: 0
    }
}