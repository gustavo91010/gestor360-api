package com.ajudaqui.gestor360_api.utils

import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.entity.PurchaseItem

fun Item.toPurchaseItems(quantity:Double, purchase: Purchase): PurchaseItem {
    return PurchaseItem(
        description = this.name,
        quantity = quantity,
        unitPrice = this.unitCost,
        purchase = purchase
    )
}
fun List<Item>.toPurchaseItems(quantities:List<Double>, purchase: Purchase): List<PurchaseItem> {
    require(this.size == quantities.size) { "O tamanho da lista de itens deve ser o mesmo da de quantidade" }
    return this.mapIndexed { index, item ->
       item.toPurchaseItems(quantities[index], purchase)}
}
