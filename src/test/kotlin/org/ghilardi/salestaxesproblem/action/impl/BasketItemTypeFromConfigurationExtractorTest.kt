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

import org.ghilardi.salestaxesproblem.model.BasketItemType
import org.ghilardi.salestaxesproblem.model.STPConfiguration
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

internal class BasketItemTypeFromConfigurationExtractorTest {

    @Test
    fun `Verify item type extraction from name`() {
        val typeExtractor = givenBasketItemTypeFromConfigurationExtractor()
        val itemType = typeExtractor.itemTypeFromName("chocolate bar")
        assertEquals(BasketItemType.FOOD, itemType)
    }

    @Test
    fun `Verify fallback type if no matching found`() {
        val typeExtractor = givenBasketItemTypeFromConfigurationExtractor()
        val itemType = typeExtractor.itemTypeFromName("strange product")
        assertEquals(BasketItemType.OTHER, itemType)
    }

    private fun givenBasketItemTypeFromConfigurationExtractor(): BasketItemTypeFromConfigurationExtractor {
        val conf = STPConfiguration(
                basicSalesTaxRate = BigDecimal.ZERO,
                importDutySalesTaxRate = BigDecimal.ZERO,
                basketItemTypeMap = mapOf(
                        "chocolate bar" to BasketItemType.FOOD,
                        "book" to BasketItemType.BOOK
                )
        )

        return BasketItemTypeFromConfigurationExtractor(
                stpConfiguration = conf
        )
    }
}