/*
 * The MIT License
 *
 * Copyright (c) 2019 Davide Ghilardi
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package org.ghilardi.salestaxesproblem.entity

import java.math.BigDecimal

class ShoppingBasket(
        private val basketItems: List<ShoppingBasketItem>
) {
    fun produceReceipt(): String {
        val itemsReceipt = basketItems
                .joinToString(separator = "\n") { it.produceReceiptEntry() }

        val salesTaxes = computeFullSalesTaxes()
        val totalPrice = computeTotalPrice()

        return """$itemsReceipt
            |Sales Taxes: $salesTaxes
            |Total: $totalPrice
        """.trimMargin()
    }

    fun computeFullSalesTaxes(): BigDecimal =
            basketItems
                    .map { it.computeFullTaxes() }
                    .fold(BigDecimal("0.00"), BigDecimal::add)

    fun computeTotalPrice(): BigDecimal =
            basketItems
                    .map { it.computeFullPrice() }
                    .fold(BigDecimal("0.00"), BigDecimal::add)
}