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

package org.ghilardi.salestaxesproblem.action.impl

import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FixedRateBasicSalesTaxCalculatorTests {
    @Test
    fun `Given positive tax rate and positive shelfPrice verify basic sales tax computation`() {
        val basicTaxCalculator = givenFixedRateBasicSalesTaxCalculator(taxRate = BigDecimal("10"))
        val taxes = basicTaxCalculator.computeBasicSalesTaxFromShelfPrice(shelfPrice = BigDecimal("110.00"))
        assertEquals(BigDecimal("10.00"), taxes)
    }

    @Test
    fun `Given tax rate of 0% and positive shelfPrice verify basic sales tax to be 0`() {
        val basicTaxCalculator = givenFixedRateBasicSalesTaxCalculator(taxRate = BigDecimal.ZERO)
        val taxes = basicTaxCalculator.computeBasicSalesTaxFromShelfPrice(shelfPrice = BigDecimal("10"))
        assertEquals(BigDecimal("0.00"), taxes)
    }

    @Test
    fun `Given positive tax rate and zero shelfPrice verify basic sales tax to be 0`() {
        val basicTaxCalculator = givenFixedRateBasicSalesTaxCalculator(taxRate = BigDecimal("10"))
        val taxes = basicTaxCalculator.computeBasicSalesTaxFromShelfPrice(shelfPrice = BigDecimal("0.00"))
        assertEquals(BigDecimal("0.00"), taxes)
    }

    @Test
    fun `Given tax rate of 0% and zero shelfPrice verify basic sales tax to be 0`() {
        val basicTaxCalculator = givenFixedRateBasicSalesTaxCalculator(taxRate = BigDecimal.ZERO)
        val taxes = basicTaxCalculator.computeBasicSalesTaxFromShelfPrice(shelfPrice = BigDecimal("0.00"))
        assertEquals(BigDecimal("0.00"), taxes)
    }

    @Test
    fun `Given negative tax rate calculator construction should fail`() {
        assertFailsWith(IllegalArgumentException::class) {
            givenFixedRateBasicSalesTaxCalculator(taxRate = BigDecimal("-1.00"))
        }
    }

    @Test
    fun `Given negative shelfPrice basic sales tax computation should fail`() {
        val basicTaxCalculator = givenFixedRateBasicSalesTaxCalculator(taxRate = BigDecimal("10"))
        assertFailsWith(IllegalArgumentException::class) {
            basicTaxCalculator.computeBasicSalesTaxFromShelfPrice(shelfPrice = BigDecimal("-10.00"))
        }
    }

    private fun givenFixedRateBasicSalesTaxCalculator(
        taxRate: BigDecimal,
        roundingStep: BigDecimal = BigDecimal("0.05")
    ) = FixedRateBasicSalesTaxCalculator(
                    taxRate = taxRate,
                    roundingOperation = NearestStepRoundingOperation(step = roundingStep)
            )
}
