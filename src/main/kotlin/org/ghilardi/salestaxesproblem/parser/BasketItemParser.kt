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

import org.ghilardi.salestaxesproblem.action.BasketItemTypeExtractor
import org.ghilardi.salestaxesproblem.model.BasketItemImportState
import org.ghilardi.salestaxesproblem.model.ShoppingBasketItemData


class BasketItemParser(
        private val basketItemTypeExtractor: BasketItemTypeExtractor
) {

    companion object {
        private val BASKET_ITEM_REGEX = Regex("^(\\d+)\\s+(.+)\\s+at\\s+([\\d.]+)\$")
        private val IMPORTED_BASKET_ITEM_REGEX = Regex("(^|(?<=\\s))imported ")
    }

    fun parse(entry: String): ShoppingBasketItemData {

        val matchResult = BASKET_ITEM_REGEX.find(entry)
        val (count, fullName, netPrice) = matchResult?.destructured
                ?: error("Error during entry deserialization from string: '$entry'")

        val productName = formatName(fullName)
        val itemType = basketItemTypeExtractor.itemTypeFromName(productName)
        val importState = extractImportState(fullName)

        return ShoppingBasketItemData(
                count = count.toInt(),
                name = productName,
                netPrice = netPrice.toBigDecimal(),
                type = itemType,
                importState = importState
                )
    }

    fun formatName(fullName: String) =
            fullName.replace(regex = IMPORTED_BASKET_ITEM_REGEX, replacement = "")

    fun extractImportState(fullName: String) =
            if (fullName.contains(regex = IMPORTED_BASKET_ITEM_REGEX)) {
                BasketItemImportState.IMPORTED
            } else {
                BasketItemImportState.NOT_IMPORTED
            }
}