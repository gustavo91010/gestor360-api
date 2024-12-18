package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.PurchaseItem
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseItemRepository:JpaRepository<PurchaseItem, Long> {

}
