package com.example.subcompose.data

import com.example.subcompose.model.FakeMenuDataSource
import com.example.subcompose.model.Menu
import com.example.subcompose.model.OrderMenu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MenuRepository {

    private val orderMenu = mutableListOf<OrderMenu>()

    init {
        if(orderMenu.isEmpty()){
            FakeMenuDataSource.dummyMenu.forEach {
                orderMenu.add(OrderMenu(it,0))
            }
        }
    }

    fun getAllMenu(): Flow<List<OrderMenu>>{
        return flowOf(orderMenu)
    }

    fun getOrderMenuById(menuId:Long):OrderMenu{
        return orderMenu.first{
            it.menu.id == menuId
        }
    }

    fun updateOrderMenu(menuId: Long,newCountValue:Int): Flow<Boolean>{
        val index = orderMenu.indexOfFirst { it.menu.id == menuId }
        val result = if(index>=0){
            val orderMenus = orderMenu[index]
            orderMenu[index]=
                orderMenus.copy(orderMenus.menu,newCountValue)
            true
        }else{
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderMenu(): Flow<List<OrderMenu>>{
        return getAllMenu()
            .map { orderMenu->
                orderMenu.filter {orderMenues ->
                orderMenues.count!=0
                }
            }
    }

    fun searchMenu(query: String):Flow<List<OrderMenu>>{
        return flowOf(orderMenu.filter {
            it.menu.title.contains(query,ignoreCase = true)

        })
    }

    companion object{
        @Volatile
        private var instance:MenuRepository?=null

        fun getInstance():MenuRepository=
            instance?: synchronized(this){
                MenuRepository().apply {
                    instance=this
                }
            }
    }
}