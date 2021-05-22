package com.blue.goeat.ui.main.ui.order

import androidx.lifecycle.*
import com.blue.goeat.data.entity.*
import com.blue.goeat.di.InjectorUtils
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val collegeId = MutableLiveData<Int>()
    private val hotelId = MutableLiveData<Int>()
    private val session = MutableLiveData<Int>()
    val colleges: LiveData<List<College>> =
        InjectorUtils.provideAppDb().getCollegeDao().getAllColleges()
    val hotels: LiveData<List<Hotel>> = collegeId.switchMap {
        InjectorUtils.provideAppDb().getHotelDao().getAllHotelsByCollegeId(it)
    }
    val sessions: LiveData<List<Session>> = hotelId.switchMap {
        InjectorUtils.provideAppDb().getSessionDao().getAllSessionsByHotelId(it)
    }
    val dishes: LiveData<List<Dish>> = session.switchMap {
        InjectorUtils.provideAppDb().getDishDao().getAllDishesBySessionId(it)
    }

    fun selectHotelByCollegeId(id:Int){
        collegeId.value = id
    }
    fun selectSessionByHotelId(id:Int){
        hotelId.value = id
    }
    fun selectDishsBySessionId(id:Int){
        session.value = id
    }

   fun save(dishOrder: DishOrder) {
       viewModelScope.launch{
           InjectorUtils.provideAppDb().getOrderDao().insert(dishOrder)
       }
    }


}