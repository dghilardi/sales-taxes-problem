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

package org.ghilardi.salestaxesproblem.factory.action

import org.ghilardi.salestaxesproblem.action.impl.FixedRateSalesTaxCalculator
import org.ghilardi.salestaxesproblem.action.impl.TaxExemptSalesTaxCalculator
import org.ghilardi.salestaxesproblem.model.BasketItemImportState
import org.ghilardi.salestaxesproblem.model.STPConfiguration
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

internal class ImportDutySalesTaxCalculatorFactoryTest {
    @Test
    fun `verify creation of ImportDutySalesTaxCalculator for imported products`() {
        val factory = givenImportDutySalesTaxCalculatorFactory()
        val calculator = factory.createImportDutySalesTaxCalculator(BasketItemImportState.IMPORTED)
        Assert.assertThat(calculator, CoreMatchers.instanceOf(FixedRateSalesTaxCalculator::class.java))
    }

    @Test
    fun `verify creation of ImportDutySalesTaxCalculator for type non imported products`() {
        val factory = givenImportDutySalesTaxCalculatorFactory()
        val calculator = factory.createImportDutySalesTaxCalculator(BasketItemImportState.NOT_IMPORTED)
        Assert.assertThat(calculator, CoreMatchers.instanceOf(TaxExemptSalesTaxCalculator::class.java))
    }

    private fun givenImportDutySalesTaxCalculatorFactory(): ImportDutySalesTaxCalculatorFactory {
        val conf = STPConfiguration(
                basicSalesTaxRate = BigDecimal.ZERO,
                importDutySalesTaxRate = BigDecimal.ZERO,
                roundingStep = BigDecimal(".05"),
                basketItemTypeMap = mapOf()
        )

        return ImportDutySalesTaxCalculatorFactory(
                stpConfiguration = conf
        )
    }
}