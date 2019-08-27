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

import org.ghilardi.salestaxesproblem.action.impl.BasketItemTypeFromConfigurationExtractor
import org.ghilardi.salestaxesproblem.command.ProduceReceiptCommand
import org.ghilardi.salestaxesproblem.factory.action.BasicSalesTaxCalculatorFactory
import org.ghilardi.salestaxesproblem.factory.action.ImportDutySalesTaxCalculatorFactory
import org.ghilardi.salestaxesproblem.factory.action.ItemReceiptSerializerFactory
import org.ghilardi.salestaxesproblem.factory.entity.ShoppingBasketEntityFactory
import org.ghilardi.salestaxesproblem.factory.entity.ShoppingBasketItemEntityFactory
import org.ghilardi.salestaxesproblem.model.STPConfiguration
import org.ghilardi.salestaxesproblem.parser.BasketItemParser
import org.ghilardi.salestaxesproblem.parser.BasketParser

class STPContext(
        val stpConfiguration: STPConfiguration,

        val basicSalesTaxCalculatorFactory: BasicSalesTaxCalculatorFactory,
        val importDutySalesTaxCalculatorFactory: ImportDutySalesTaxCalculatorFactory,
        val itemReceiptSerializerFactory: ItemReceiptSerializerFactory,

        val shoppingBasketItemEntityFactory: ShoppingBasketItemEntityFactory,

        val basketEntityFactory: ShoppingBasketEntityFactory,

        val basketItemTypeExtractor: BasketItemTypeFromConfigurationExtractor,
        val basketItemParser: BasketItemParser,
        val basketParser: BasketParser,

        val produceReceiptCommand: ProduceReceiptCommand
)