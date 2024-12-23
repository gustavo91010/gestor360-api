package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ProductRepository : JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM product WHERE user_id= :userId AND name = :name", nativeQuery = true)
    fun findByName(userId: Long, name: String): Optional<Product>

    @Query(value = "SELECT * FROM product WHERE user_id = :userId", nativeQuery = true)
    fun findAllByUserId(userId: Long): List<Product>


    @Query(value = "SELECT * FROM product WHERE user_id= :userId AND id = :productId", nativeQuery = true)
    fun findById(userId: Long, productId: Long): Optional<Product>

}