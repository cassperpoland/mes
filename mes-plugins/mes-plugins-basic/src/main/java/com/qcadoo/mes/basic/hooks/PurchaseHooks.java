/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 1.3
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.mes.basic.hooks;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.mes.basic.PurchaseService;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;

import static com.qcadoo.mes.basic.constants.PurchaseFields.*;
@Service
public class PurchaseHooks {

    @Autowired
    private PurchaseService purchaseService;

	public boolean checkIfQuantityIsCorrect(final DataDefinition purchaseDD,
			final Entity purchase) {
		if (purchase.getId() != null) {

			if (((Integer) purchase.getField(QUANTITY)).compareTo(0) == -1) {
				purchase.addError(purchaseDD.getField(QUANTITY),
						"basic.purchase.price.priceIsNegative");
				return false;
			}
		}
		return true;
	}

    public boolean checkIfPriceIsCorrect(final DataDefinition purchaseDD, final Entity purchase) {
		if (purchase.getId() != null) {

			if (purchase.getDecimalField(PRICE).compareTo(BigDecimal.ZERO)<0) {
				purchase.addError(purchaseDD.getField(PRICE),
						"basic.purchase.quantity.quantityIsNegative");
				return false;
			}
		}
		return true;
    }    
    
    public boolean checkIfNoPurchaseWithSameProductAndPriceExist(final DataDefinition purchaseDD, final Entity purchase) {
        List<Entity> purchases = purchaseDD.find().add(SearchRestrictions.belongsTo(PRODUCT, purchase)).add(SearchRestrictions.eq(PRICE, purchase.getField(PRICE))).list().getEntities();
        if (purchases == null||purchases.size()==0) {
            return true;
        }else {
            return false;
    	}
        }
    
}
