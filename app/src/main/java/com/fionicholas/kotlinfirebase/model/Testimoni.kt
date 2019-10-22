package com.fionicholas.kotlinfirebase.model

class Testimoni (
    val id: String?,
    val name: String,
    val comment: String,
    val rating: Int
){

    constructor(): this("","","",0){

    }
}