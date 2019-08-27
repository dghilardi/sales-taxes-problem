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

package org.ghilardi.salestaxesproblem.parser

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.ghilardi.salestaxesproblem.model.BasketItemImportState
import org.ghilardi.salestaxesproblem.model.BasketItemType
import org.ghilardi.salestaxesproblem.model.ShoppingBasketItemData
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

internal class BasketParserTest {

    @Test
    fun `test multiple line input`() {
        val parser = givenBasketParser()

        val data = parser.parse("""1 imported bottle of perfume at 27.99
            |1 bottle of perfume at 18.99
        """.trimMargin())

        assertEquals(2, data.size)
    }

    @Test
    fun `test single line input`() {
        val parser = givenBasketParser()

        val data = parser.parse("1 imported bottle of perfume at 27.99")

        assertEquals(1, data.size)
    }

    private fun givenBasketParser(
            itemType: BasketItemType = BasketItemType.OTHER
    ): BasketParser {
        val mockedBasketItemParser = mock<BasketItemParser> {
            on { parse(any()) } doReturn ShoppingBasketItemData(
                    name = "MOCK",
                    importState = BasketItemImportState.IMPORTED,
                    type = BasketItemType.OTHER,
                    count = 1,
                    shelfPrice = BigDecimal("10.0")
            )
        }

        return BasketParser(
                basketItemParser = mockedBasketItemParser
        )
    }
}