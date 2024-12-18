package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Purchase
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository:JpaRepository<Purchase, Long> {

}
