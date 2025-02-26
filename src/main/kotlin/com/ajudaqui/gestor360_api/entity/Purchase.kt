package com.ajudaqui.gestor360_api.entity

import com.ajudaqui.gestor360_api.utils.EPurchaseType
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

@Entity
data class Purchase(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: EPurchaseType,

    @Column(name = "description", nullable = false)
    val description: String,
    // val totalPrice: BigDecimal = BigDecimal.ZERO,

    @OneToMany(mappedBy = "purchase")
    val items: MutableList<PurchaseItem> = mutableListOf(),

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    val users: Users

) {
    val totalPrice: BigDecimal
        get() = items.stream()
            .filter { it.totalPrice != null }
            .map { it.totalPrice }
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_UP)

}
