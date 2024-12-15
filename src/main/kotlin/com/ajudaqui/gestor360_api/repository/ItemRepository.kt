package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface ItemRepository: JpaRepository<Item, Long>{

    fun findByName(name: String): List<Item>
    fun findByBrand(brand: String): List<Item>

    @Query(value = "SELECT * FROM item WHERE name = :name AND brand = :brand",nativeQuery = true)
    fun findByNameAndBrand(@Param("name") name: String, @Param("brand") brand: String): Optional<Item>

}