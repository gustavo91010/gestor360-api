package com.ajudaqui.gestor360_api.response

import com.ajudaqui.gestor360_api.entity.Roles

data class ResponseLogin(val id:Long,
    val name: String,
    val email:String,
    val roles: MutableList<Roles>)