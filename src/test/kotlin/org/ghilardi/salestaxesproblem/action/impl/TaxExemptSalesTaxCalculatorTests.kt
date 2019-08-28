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

class TaxExemptSalesTaxCalculatorTests {
    @Test
    fun `Given positive netPrice verify tax computation to be 0`() {
        val basicTaxCalculator = TaxExemptSalesTaxCalculator()
        val taxes = basicTaxCalculator.computeSalesTaxFromNetPrice(netPrice = BigDecimal("100.00"))
        assertEquals(BigDecimal("0.00"), taxes)
    }

    @Test
    fun `Given zero netPrice verify tax computation to be 0`() {
        val basicTaxCalculator = TaxExemptSalesTaxCalculator()
        val taxes = basicTaxCalculator.computeSalesTaxFromNetPrice(netPrice = BigDecimal("0.00"))
        assertEquals(BigDecimal("0.00"), taxes)
    }

    @Test
    fun `Given negative netPrice basic sales tax computation should fail`() {
        val basicTaxCalculator = TaxExemptSalesTaxCalculator()
        assertFailsWith(IllegalArgumentException::class) {
            basicTaxCalculator.computeSalesTaxFromNetPrice(netPrice = BigDecimal("-10.00"))
        }
    }
}
