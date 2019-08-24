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

class FixedRateImportDutySalesTaxCalculatorTests {
    @Test
    fun `Given positive tax rate and positive shelfPrice verify import duty sales tax computation`() {
        val importDutyTaxCalculator = givenFixedRateImportDutySalesTaxCalculator(taxRate = BigDecimal("5.00"))
        val taxes = importDutyTaxCalculator.computeImportDutySalesTaxFromShelfPrice(shelfPrice = BigDecimal("100.00"))
        assertEquals(BigDecimal("5.00"), taxes)
    }

    @Test
    fun `Given tax rate of 0% and positive shelfPrice verify import duty sales tax to be 0`() {
        val importDutyTaxCalculator = givenFixedRateImportDutySalesTaxCalculator(taxRate = BigDecimal.ZERO)
        val taxes = importDutyTaxCalculator.computeImportDutySalesTaxFromShelfPrice(shelfPrice = BigDecimal("10.00"))
        assertEquals(BigDecimal("0.00"), taxes)
    }

    @Test
    fun `Given positive tax rate and zero shelfPrice verify import duty sales tax to be 0`() {
        val importDutyTaxCalculator = givenFixedRateImportDutySalesTaxCalculator(taxRate = BigDecimal("10"))
        val taxes = importDutyTaxCalculator.computeImportDutySalesTaxFromShelfPrice(shelfPrice = BigDecimal("0.00"))
        assertEquals(BigDecimal("0.00"), taxes)
    }

    @Test
    fun `Given tax rate of 0% and zero shelfPrice verify import duty sales tax to be 0`() {
        val importDutyTaxCalculator = givenFixedRateImportDutySalesTaxCalculator(taxRate = BigDecimal.ZERO)
        val taxes = importDutyTaxCalculator.computeImportDutySalesTaxFromShelfPrice(shelfPrice = BigDecimal("0.00"))
        assertEquals(BigDecimal("0.00"), taxes)
    }

    @Test
    fun `Given negative tax rate calculator construction should fail`() {
        assertFailsWith(IllegalArgumentException::class) {
            givenFixedRateImportDutySalesTaxCalculator(taxRate = BigDecimal("-1.00"))
        }
    }

    @Test
    fun `Given negative shelfPrice import duty sales tax computation should fail`() {
        val importDutyTaxCalculator = givenFixedRateImportDutySalesTaxCalculator(taxRate = BigDecimal("10"))
        assertFailsWith(IllegalArgumentException::class) {
            importDutyTaxCalculator.computeImportDutySalesTaxFromShelfPrice(shelfPrice = BigDecimal("-10.00"))
        }
    }

    private fun givenFixedRateImportDutySalesTaxCalculator(
            taxRate: BigDecimal,
            roundingStep: BigDecimal = BigDecimal("0.05")
    ) = FixedRateImportDutySalesTaxCalculator(
            taxRate = taxRate,
            roundingOperation = NearestStepRoundingOperation(step = roundingStep)
    )
}
