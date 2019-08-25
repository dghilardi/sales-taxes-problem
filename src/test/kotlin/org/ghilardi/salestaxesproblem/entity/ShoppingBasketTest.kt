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

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

internal class ShoppingBasketTest {

    @Test
    fun `produce shopping basket receipt`() {
        val basket = givenShoppingBasket(listOf(
                mockBasketItem(fullTaxes = BigDecimal("15.00"), fullPrice = BigDecimal("25.00"), receiptEntry = "1 book: 25.00"),
                mockBasketItem(fullTaxes = BigDecimal("5.00"), fullPrice = BigDecimal("16.50"), receiptEntry = "1 music CD: 16.50"),
                mockBasketItem(fullTaxes = BigDecimal("15.00"), fullPrice = BigDecimal("30.00"), receiptEntry = "1 bottle of perfume: 30.00")
        ))

        val receipt = basket.produceReceipt()

        assertEquals("""1 book: 25.00
            |1 music CD: 16.50
            |1 bottle of perfume: 30.00
            |Sales Taxes: 35.00
            |Total: 71.50
        """.trimMargin(), receipt)
    }

    @Test
    fun `Given shopping basket, verify full tax computation`() {
        val basket = givenShoppingBasket(listOf(
                mockBasketItem(fullTaxes = BigDecimal("15.00")),
                mockBasketItem(fullTaxes = BigDecimal("10.00")),
                mockBasketItem(fullTaxes = BigDecimal("5.00"))
        ))

        val fullBasketTaxes = basket.computeFullSalesTaxes()

        assertEquals(BigDecimal("30.00"), fullBasketTaxes)
    }

    @Test
    fun `Given shopping basket, verify full price computation`() {
        val basket = givenShoppingBasket(listOf(
                mockBasketItem(fullTaxes = BigDecimal("10.00")),
                mockBasketItem(fullTaxes = BigDecimal("10.00")),
                mockBasketItem(fullTaxes = BigDecimal("5.00"))
        ))

        val fullBasketTaxes = basket.computeFullSalesTaxes()

        assertEquals(BigDecimal("25.00"), fullBasketTaxes)
    }

    fun `Given empty shopping basket, verify full tax computation`() {
        val basket = givenShoppingBasket(listOf())

        val fullBasketTaxes = basket.computeFullSalesTaxes()

        assertEquals(BigDecimal("0.00"), fullBasketTaxes)
    }

    @Test
    fun `Given empty shopping basket, verify full price computation`() {
        val basket = givenShoppingBasket(listOf())

        val fullBasketTaxes = basket.computeFullSalesTaxes()

        assertEquals(BigDecimal("0.00"), fullBasketTaxes)
    }

    private fun givenShoppingBasket(
            basketItems: List<ShoppingBasketItem> = listOf()
    ) = ShoppingBasket(
            basketItems = basketItems
    )

    private fun mockBasketItem(
            fullTaxes: BigDecimal = BigDecimal.ZERO,
            fullPrice: BigDecimal = BigDecimal.ZERO,
            receiptEntry: String = ""
    ) = mock<ShoppingBasketItem> {
        on { computeFullTaxes() } doReturn fullTaxes
        on { computeFullPrice() } doReturn fullPrice
        on { produceReceiptEntry() } doReturn receiptEntry
    }
}