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

import org.ghilardi.salestaxesproblem.action.impl.FixedRateBasicSalesTaxCalculator
import org.ghilardi.salestaxesproblem.action.impl.TaxExemptBasicSalesTaxCalculator
import org.ghilardi.salestaxesproblem.model.BasketItemType
import org.ghilardi.salestaxesproblem.model.STPConfiguration
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Test
import java.math.BigDecimal

internal class BasicSalesTaxCalculatorFactoryTest {
    @Test
    fun `verify creation of BasicSalesTaxCalculator for type 'other'`() {
        val factory = givenBasketItemTypeFromConfigurationExtractor()
        val calculator = factory.createBasicSalesTaxCalculator(BasketItemType.OTHER)
        assertThat(calculator, instanceOf(FixedRateBasicSalesTaxCalculator::class.java))
    }

    @Test
    fun `verify creation of BasicSalesTaxCalculator for type 'food'`() {
        val factory = givenBasketItemTypeFromConfigurationExtractor()
        val calculator = factory.createBasicSalesTaxCalculator(BasketItemType.FOOD)
        assertThat(calculator, instanceOf(TaxExemptBasicSalesTaxCalculator::class.java))
    }

    @Test
    fun `verify creation of BasicSalesTaxCalculator for type 'book'`() {
        val factory = givenBasketItemTypeFromConfigurationExtractor()
        val calculator = factory.createBasicSalesTaxCalculator(BasketItemType.BOOK)
        assertThat(calculator, instanceOf(TaxExemptBasicSalesTaxCalculator::class.java))
    }

    @Test
    fun `verify creation of BasicSalesTaxCalculator for type 'medical product'`() {
        val factory = givenBasketItemTypeFromConfigurationExtractor()
        val calculator = factory.createBasicSalesTaxCalculator(BasketItemType.MEDICAL_PRODUCT)
        assertThat(calculator, instanceOf(TaxExemptBasicSalesTaxCalculator::class.java))
    }

    private fun givenBasketItemTypeFromConfigurationExtractor(): BasicSalesTaxCalculatorFactory {
        val conf = STPConfiguration(
                basicSalesTaxRate = BigDecimal.ZERO,
                importDutySalesTaxRate = BigDecimal.ZERO,
                roundingStep = BigDecimal(".05"),
                basketItemTypeMap = mapOf()
        )

        return BasicSalesTaxCalculatorFactory(
                stpConfiguration = conf
        )
    }
}