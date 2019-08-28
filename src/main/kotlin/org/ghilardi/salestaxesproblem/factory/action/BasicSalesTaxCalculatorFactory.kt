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
import org.ghilardi.salestaxesproblem.action.impl.NearestNextStepRoundingOperation
import org.ghilardi.salestaxesproblem.action.impl.TaxExemptSalesTaxCalculator
import org.ghilardi.salestaxesproblem.model.BasketItemType
import org.ghilardi.salestaxesproblem.model.STPConfiguration

class BasicSalesTaxCalculatorFactory(
        private val stpConfiguration: STPConfiguration
) {
    fun createBasicSalesTaxCalculator(itemType: BasketItemType) = when (itemType) {
        BasketItemType.FOOD, BasketItemType.MEDICAL_PRODUCT, BasketItemType.BOOK -> TaxExemptSalesTaxCalculator()
        BasketItemType.OTHER -> FixedRateSalesTaxCalculator(stpConfiguration.basicSalesTaxRate, NearestNextStepRoundingOperation(stpConfiguration.roundingStep))
    }
}