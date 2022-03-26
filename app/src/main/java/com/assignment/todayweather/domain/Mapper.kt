package com.assignment.todayweather.domain


interface Mapper<T : Any, V : Any> {

    fun map(data: T): V
}