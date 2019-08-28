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

import org.ghilardi.salestaxesproblem.action.SalesTaxCalculator
import org.ghilardi.salestaxesproblem.action.ItemReceiptSerializer
import java.math.BigDecimal

class ShoppingBasketItem(
        private val name: String,
        private val count: Int,
        private val netPrice: BigDecimal,
        private val basicSalesTaxCalculator: SalesTaxCalculator,
        private val importDutySalesTaxCalculator: SalesTaxCalculator,
        private val itemReceiptSerializer: ItemReceiptSerializer
) {
    fun computeFullTaxes(): BigDecimal {
        return BigDecimal(count) * (
                basicSalesTaxCalculator.computeSalesTaxFromNetPrice(netPrice) +
                importDutySalesTaxCalculator.computeSalesTaxFromNetPrice(netPrice)
                )
    }

    fun computeFullPrice(): BigDecimal {
        val totNetPrice = netPrice * BigDecimal(count)
        return totNetPrice + computeFullTaxes()
    }

    fun produceReceiptEntry(): String {
        return itemReceiptSerializer.produceReceiptEntry(count, name, computeFullPrice())
    }
}