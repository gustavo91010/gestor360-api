package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PurchaseRepository:JpaRepository<Purchase, Long> {

    @Query(value = "SELECT * FROM purchase WHERE user_id= :userId AND type= :type", nativeQuery = true)
    fun findByType(userId: Long, type: String): List<Purchase>

    @Query(value = "SELECT * FROM purchase WHERE user_id= :userId ", nativeQuery = true)
    fun findByUsers_Id(userId: Long): List<Purchase>

}
