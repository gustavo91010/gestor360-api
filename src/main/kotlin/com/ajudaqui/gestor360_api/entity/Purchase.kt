package com.ajudaqui.gestor360_api.entity

import com.ajudaqui.gestor360_api.utils.EPurchaseType
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Purchase(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Enumerated(EnumType.STRING)
    val type: EPurchaseType,
    val description: String,
    val totalPrice: BigDecimal = BigDecimal.ZERO,

    @OneToMany(mappedBy = "purchase")
    val items: MutableList<PurchaseItem> = mutableListOf(),

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    val users: Users

)
