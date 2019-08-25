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

import org.ghilardi.salestaxesproblem.action.BasicSalesTaxCalculator
import java.math.BigDecimal

class FixedRateBasicSalesTaxCalculator (
        private val taxRate: BigDecimal,
        private val roundingOperation: NearestStepRoundingOperation
): BasicSalesTaxCalculator {

    init {
        require(taxRate >= BigDecimal.ZERO) { "Basic sales tax rate must be a non negative number. Given value was $taxRate" }
    }

    override fun computeBasicSalesTaxFromShelfPrice(shelfPrice: BigDecimal): BigDecimal {
        require(shelfPrice >= BigDecimal.ZERO) { "Shelf price must be a non negative number. Given value was $shelfPrice" }
        val taxes = shelfPrice * taxRate / (BigDecimal(100) + taxRate)
        return roundingOperation.round(taxes)
    }
}