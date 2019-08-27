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

package org.ghilardi.salestaxesproblem.context

import org.ghilardi.salestaxesproblem.action.BasketItemTypeExtractor
import org.ghilardi.salestaxesproblem.action.impl.BasketItemTypeFromConfigurationExtractor
import org.ghilardi.salestaxesproblem.command.ProduceReceiptCommand
import org.ghilardi.salestaxesproblem.factory.action.BasicSalesTaxCalculatorFactory
import org.ghilardi.salestaxesproblem.factory.action.ImportDutySalesTaxCalculatorFactory
import org.ghilardi.salestaxesproblem.factory.action.ItemReceiptSerializerFactory
import org.ghilardi.salestaxesproblem.factory.entity.ShoppingBasketEntityFactory
import org.ghilardi.salestaxesproblem.factory.entity.ShoppingBasketItemEntityFactory
import org.ghilardi.salestaxesproblem.model.BasketItemType
import org.ghilardi.salestaxesproblem.model.STPConfiguration
import org.ghilardi.salestaxesproblem.parser.BasketItemParser
import org.ghilardi.salestaxesproblem.parser.BasketParser
import java.math.BigDecimal

class STPContextBuilder {
    fun buildContext(): STPContext {
        val stpConfiguration = STPConfiguration(
                basicSalesTaxRate = BigDecimal("10.0"),
                importDutySalesTaxRate = BigDecimal("5.0"),
                roundingStep = BigDecimal("0.05"),
                basketItemTypeMap = mapOf(
                        "book" to BasketItemType.BOOK,
                        "music CD" to BasketItemType.OTHER,
                        "chocolate bar" to BasketItemType.FOOD,
                        "bottle of perfume" to BasketItemType.OTHER,
                        "packet of headache pills" to BasketItemType.MEDICAL_PRODUCT,
                        "box of imported chocolates" to BasketItemType.FOOD
                )
        )

        val basicSalesTaxCalculatorFactory = BasicSalesTaxCalculatorFactory(stpConfiguration)
        val importDutySalesTaxCalculatorFactory = ImportDutySalesTaxCalculatorFactory(stpConfiguration)
        val itemReceiptSerializerFactory = ItemReceiptSerializerFactory()

        val shoppingBasketItemEntityFactory = ShoppingBasketItemEntityFactory(
                basicSalesTaxCalculatorFactory = basicSalesTaxCalculatorFactory,
                importDutySalesTaxCalculatorFactory = importDutySalesTaxCalculatorFactory,
                itemReceiptSerializerFactory = itemReceiptSerializerFactory
        )

        val basketEntityFactory = ShoppingBasketEntityFactory(shoppingBasketItemEntityFactory)

        val basketItemTypeExtractor = BasketItemTypeFromConfigurationExtractor(stpConfiguration)
        val basketItemParser = BasketItemParser(basketItemTypeExtractor)
        val basketParser = BasketParser(basketItemParser)

        val produceReceiptCommand = ProduceReceiptCommand(
                basketParser = basketParser,
                basketEntityFactory = basketEntityFactory
        )

        return STPContext(
                stpConfiguration = stpConfiguration,
                basicSalesTaxCalculatorFactory = basicSalesTaxCalculatorFactory,
                importDutySalesTaxCalculatorFactory = importDutySalesTaxCalculatorFactory,
                itemReceiptSerializerFactory = itemReceiptSerializerFactory,
                shoppingBasketItemEntityFactory = shoppingBasketItemEntityFactory,
                basketEntityFactory = basketEntityFactory,
                basketItemTypeExtractor = basketItemTypeExtractor,
                basketItemParser = basketItemParser,
                basketParser = basketParser,
                produceReceiptCommand = produceReceiptCommand
        )
    }
}