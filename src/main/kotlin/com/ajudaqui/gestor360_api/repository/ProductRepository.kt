package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ProductRepository: JpaRepository<Product, Long>{
    @Query(value = "SELECT * FROM product WHERE name = :name",nativeQuery = true)
    fun findByName(name: String): Optional<Product>

}