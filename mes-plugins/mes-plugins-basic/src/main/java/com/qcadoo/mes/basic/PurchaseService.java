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
package com.qcadoo.mes.basic;

import static com.qcadoo.mes.basic.constants.ProductFields.UNIT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.mes.basic.constants.BasicConstants;
import com.qcadoo.mes.basic.constants.PurchaseFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

@Service
public class PurchaseService {


    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private ProductService productService;

    /**
     * Returns purchase entity
     * 
     * @param purchaseId
     *            purchaseId
     * 
     * @return purchase
     */
    @Transactional
    public Entity getPurchase(final Long purchaseId) {
        return getPurchaseDD().get(purchaseId);
    }
    
    /**
     * Returns product entity
     * 
     * @param purchase
     *            purchase
     * 
     * @return product
     */
    @Transactional
    public Entity getProduct(final Entity purchase) {
        return purchase.getBelongsToField(PurchaseFields.PRODUCT);
    }
    
    private DataDefinition getPurchaseDD() {
        return dataDefinitionService.get(BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_COMPANY);
    }
    public void fillUnit(final DataDefinition purchaseDD, final Entity purchase) {
        if (purchase.getField(UNIT) == null) {
            purchase.setField(UNIT, getProduct(purchase).getField(UNIT));
        }
    }
}
