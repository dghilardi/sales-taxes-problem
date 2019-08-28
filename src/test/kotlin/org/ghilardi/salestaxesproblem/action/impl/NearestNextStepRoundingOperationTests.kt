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

class NearestNextStepRoundingOperationTests {
    @Test
    fun `Test positive rounding up after mid with 0,05 scale`() {
        val roundingOperation = NearestNextStepRoundingOperation(BigDecimal("0.05"))
        val rounded = roundingOperation.round(BigDecimal("5.344"))
        assertEquals(BigDecimal("5.35"), rounded)
    }

    @Test
    fun `Test positive rounding up before mid with 0,05 scale`() {
        val roundingOperation = NearestNextStepRoundingOperation(BigDecimal("0.05"))
        val rounded = roundingOperation.round(BigDecimal("3.121"))
        assertEquals(BigDecimal("3.15"), rounded)
    }

    @Test
    fun `Test negative rounding up after mid with 0,05 scale`() {
        val roundingOperation = NearestNextStepRoundingOperation(BigDecimal("0.05"))
        val rounded = roundingOperation.round(BigDecimal("-5.344"))
        assertEquals(BigDecimal("-5.35"), rounded)
    }

    @Test
    fun `Test negative rounding up before mid 0,05 scale`() {
        val roundingOperation = NearestNextStepRoundingOperation(BigDecimal("0.05"))
        val rounded = roundingOperation.round(BigDecimal("-3.121"))
        assertEquals(BigDecimal("-3.15"), rounded)
    }

    @Test
    fun `Test rounding exact with 0,05 scale`() {
        val roundingOperation = NearestNextStepRoundingOperation(BigDecimal("0.05"))
        val rounded = roundingOperation.round(BigDecimal("5.550"))
        assertEquals(BigDecimal("5.55"), rounded)
    }

    @Test
    fun `Test negative rounding exact with 0,05 scale`() {
        val roundingOperation = NearestNextStepRoundingOperation(BigDecimal("0.05"))
        val rounded = roundingOperation.round(BigDecimal("-3.150"))
        assertEquals(BigDecimal("-3.15"), rounded)
    }
}