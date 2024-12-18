package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemRepository : JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM item WHERE user_id= :userId AND LIKE %:name%", nativeQuery = true)
    fun findByName(userId: Long, name: String): List<Item>
    @Query(value = "SELECT * FROM item WHERE user_id= :userId AND brand= :brand", nativeQuery = true)
    fun findByBrand(brand: String, userId: Long): List<Item>

    @Query(value = "SELECT * FROM item WHERE name = :name AND brand = :brand AND user_id= :userId", nativeQuery = true)
    fun findByNameAndBrand(
        @Param("name") name: String,
        @Param("brand") brand: String ,
        @Param("userId") userId: Long): List<Item>

    @Query(value = "SELECT * FROM item WHERE id IN (:ids)", nativeQuery = true)
    fun findByIds(ids: List<Int>): List<Item>

    @Query(value = "SELECT * FROM item WHERE user_id = :userId", nativeQuery = true)
    fun findItemByUser(userId: Long): List<Item>




}