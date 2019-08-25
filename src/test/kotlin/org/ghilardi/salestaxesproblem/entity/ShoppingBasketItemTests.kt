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

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.ghilardi.salestaxesproblem.action.BasicSalesTaxCalculator
import org.ghilardi.salestaxesproblem.action.ImportDutySalesTaxCalculator
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class ShoppingBasketItemTests {

    @Test
    fun `Given shopping basket item verify full taxes computation`() {
        val basketItem = givenShoppingBasketItem(count = 3, shelfPrice = BigDecimal("100.0"), basicSalesTax = BigDecimal("30.00"), importDutySalesTax = BigDecimal("15.00"))
        val fullTaxes = basketItem.computeFullTaxes()
        assertEquals(BigDecimal("45.00"), fullTaxes)
    }

    @Test
    fun `Given shopping basket item verify full price computation`() {
        val basketItem = givenShoppingBasketItem(count = 3, shelfPrice = BigDecimal("100.0"), basicSalesTax = BigDecimal("30.00"), importDutySalesTax = BigDecimal("15.00"))
        val fullTaxes = basketItem.computeFullPrice()
        assertEquals(BigDecimal("315.00"), fullTaxes)
    }

    fun givenShoppingBasketItem(
            name: String = "",
            count: Int = 1,
            shelfPrice: BigDecimal = BigDecimal.ZERO,
            basicSalesTax: BigDecimal = BigDecimal.ZERO,
            importDutySalesTax: BigDecimal = BigDecimal.ZERO
    ): ShoppingBasketItem {

        val mockedBasicSalesTaxCalculator = mock<BasicSalesTaxCalculator> {
            on { computeBasicSalesTaxFromShelfPrice(any()) } doReturn basicSalesTax
        }

        val mockedImportDutySalesTaxCalculator = mock<ImportDutySalesTaxCalculator> {
            on { computeImportDutySalesTaxFromShelfPrice(any()) } doReturn importDutySalesTax
        }

        return ShoppingBasketItem(
                name = name,
                count = count,
                shelfPrice = shelfPrice,
                basicSalesTaxCalculator = mockedBasicSalesTaxCalculator,
                importDutySalesTaxCalculator = mockedImportDutySalesTaxCalculator
        )
    }

}