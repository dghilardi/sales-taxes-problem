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
import org.ghilardi.salestaxesproblem.action.BasketItemTypeExtractor
import org.ghilardi.salestaxesproblem.model.BasketItemImportState
import org.ghilardi.salestaxesproblem.model.BasketItemType
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

internal class BasketItemParserTest {

    @Test
    fun `Given non imported item name verify item name extraction`() {
        val parser = givenBasketItemParser()
        val extractedName = parser.formatName("chocolate bar")
        assertEquals("chocolate bar", extractedName)
    }

    @Test
    fun `Given imported item name (format 1) verify item name extraction`() {
        val parser = givenBasketItemParser()
        val extractedName = parser.formatName("imported bottle of perfume")
        assertEquals("bottle of perfume", extractedName)
    }

    @Test
    fun `Given imported item name (format 2) verify item name extraction`() {
        val parser = givenBasketItemParser()
        val extractedName = parser.formatName("box of imported chocolates")
        assertEquals("box of chocolates", extractedName)
    }

    @Test
    fun `Given non imported item name verify imported state extraction`() {
        val parser = givenBasketItemParser()
        val importState = parser.extractImportState("chocolate bar")
        assertEquals(BasketItemImportState.NOT_IMPORTED, importState)
    }

    @Test
    fun `Given imported item name (format 1) verify imported state extraction`() {
        val parser = givenBasketItemParser()
        val importState = parser.extractImportState("imported bottle of perfume")
        assertEquals(BasketItemImportState.IMPORTED, importState)
    }

    @Test
    fun `Given imported item name (format 2) verify imported state extraction`() {
        val parser = givenBasketItemParser()
        val importState = parser.extractImportState("box of imported chocolates")
        assertEquals(BasketItemImportState.IMPORTED, importState)
    }

    @Test
    fun `Given input entry for non imported item verify properties extraction`() {
        val parser = givenBasketItemParser()
        val data = parser.parse("1 packet of headache pills at 9.75")

        assertEquals(1, data.count)
        assertEquals("packet of headache pills", data.name)
        assertEquals(BigDecimal("9.75"), data.netPrice)
    }

    @Test
    fun `Given input entry for imported item (format 1) verify properties extraction`() {
        val parser = givenBasketItemParser()
        val data = parser.parse("1 imported bottle of perfume at 47.50")

        assertEquals(1, data.count)
        assertEquals("bottle of perfume", data.name)
        assertEquals(BigDecimal("47.50"), data.netPrice)
    }

    @Test
    fun `Given input entry for imported item (format 2) verify properties extraction`() {
        val parser = givenBasketItemParser()
        val data = parser.parse("3 box of imported chocolates at 11.25")

        assertEquals(3, data.count)
        assertEquals("box of chocolates", data.name)
        assertEquals(BigDecimal("11.25"), data.netPrice)
    }

    private fun givenBasketItemParser(
            itemType: BasketItemType = BasketItemType.OTHER
    ): BasketItemParser {
        val mockedBasketItemTypeExtractor = mock<BasketItemTypeExtractor> {
            on { itemTypeFromName(any()) } doReturn itemType
        }

        return BasketItemParser(
                basketItemTypeExtractor = mockedBasketItemTypeExtractor
        )
    }
}