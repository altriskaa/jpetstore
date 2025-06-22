/*
 *    Copyright 2010-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.jpetstore.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperTestContext.class)
@Transactional
class ItemMapperTest {

  // [REFACTOR (java:S1192)] 22/06/25 - "Define a constant instead of duplicating this literal multiple times. String literals should not be duplicated" [M]
  private static final String PRODUCT_ID_FI_SW_01 = "FI-SW-01";
  private static final String ITEM_ID_EST_1 = "EST-1";
  private static final String PRICE_16_50 = "16.50";
  private static final String PRICE_10_00 = "10.00";
  private static final String PRODUCT_NAME_ANGELFISH = "Angelfish";
  private static final String PRODUCT_DESCRIPTION_AUS =
          "<image src=\"../images/fish1.gif\">Salt Water fish from Australia";


  @Autowired
  private ItemMapper mapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void getItemListByProduct() {
    // given
    String productId = PRODUCT_ID_FI_SW_01;

    // when
    List<Item> items = mapper.getItemListByProduct(productId);

    // then
    items.sort(Comparator.comparing(Item::getItemId));
    assertThat(items).hasSize(2);
    assertThat(items.get(0).getItemId()).isEqualTo(ITEM_ID_EST_1);
    assertThat(items.get(0).getListPrice()).isEqualTo(new BigDecimal(PRICE_16_50));
    assertThat(items.get(0).getUnitCost()).isEqualTo(new BigDecimal(PRICE_10_00));
    assertThat(items.get(0).getSupplierId()).isEqualTo(1);
    assertThat(items.get(0).getStatus()).isEqualTo("P");
    assertThat(items.get(0).getAttribute1()).isEqualTo("Large");
    assertThat(items.get(0).getAttribute2()).isNull();
    assertThat(items.get(0).getAttribute3()).isNull();
    assertThat(items.get(0).getAttribute4()).isNull();
    assertThat(items.get(0).getAttribute5()).isNull();
    assertThat(items.get(0).getProduct().getProductId()).isEqualTo(PRODUCT_ID_FI_SW_01);
    assertThat(items.get(0).getProduct().getName()).isEqualTo(PRODUCT_NAME_ANGELFISH);
    assertThat(items.get(0).getProduct().getDescription())
        .isEqualTo(PRODUCT_DESCRIPTION_AUS);
    assertThat(items.get(0).getProduct().getCategoryId()).isEqualTo("FISH");
    assertThat(items.get(1).getItemId()).isEqualTo("EST-2");
    assertThat(items.get(1).getListPrice()).isEqualTo(new BigDecimal(PRICE_16_50));
    assertThat(items.get(1).getUnitCost()).isEqualTo(new BigDecimal(PRICE_10_00));
    assertThat(items.get(1).getSupplierId()).isEqualTo(1);
    assertThat(items.get(1).getStatus()).isEqualTo("P");
    assertThat(items.get(1).getAttribute1()).isEqualTo("Small");
    assertThat(items.get(1).getAttribute2()).isNull();
    assertThat(items.get(1).getAttribute3()).isNull();
    assertThat(items.get(1).getAttribute4()).isNull();
    assertThat(items.get(1).getAttribute5()).isNull();
    assertThat(items.get(1).getProduct().getProductId()).isEqualTo(PRODUCT_ID_FI_SW_01);
    assertThat(items.get(1).getProduct().getName()).isEqualTo(PRODUCT_NAME_ANGELFISH);
    assertThat(items.get(1).getProduct().getDescription())
        .isEqualTo(PRODUCT_DESCRIPTION_AUS);
    assertThat(items.get(1).getProduct().getCategoryId()).isEqualTo("FISH");
  }

  @Test
  void getItem() {
    // given
    String itemId = ITEM_ID_EST_1;

    // when
    Item item = mapper.getItem(itemId);

    // then
    assertThat(item.getItemId()).isEqualTo(ITEM_ID_EST_1);
    assertThat(item.getListPrice()).isEqualTo(new BigDecimal(PRICE_16_50));
    assertThat(item.getUnitCost()).isEqualTo(new BigDecimal(PRICE_10_00));
    assertThat(item.getSupplierId()).isEqualTo(1);
    assertThat(item.getStatus()).isEqualTo("P");
    assertThat(item.getAttribute1()).isEqualTo("Large");
    assertThat(item.getAttribute2()).isNull();
    assertThat(item.getAttribute3()).isNull();
    assertThat(item.getAttribute4()).isNull();
    assertThat(item.getAttribute5()).isNull();
    assertThat(item.getProduct().getProductId()).isEqualTo(PRODUCT_ID_FI_SW_01);
    assertThat(item.getProduct().getName()).isEqualTo(PRODUCT_NAME_ANGELFISH);
    assertThat(item.getProduct().getDescription())
        .isEqualTo(PRODUCT_DESCRIPTION_AUS);
    assertThat(item.getProduct().getCategoryId()).isEqualTo("FISH");
  }

  @Test
  void getInventoryQuantity() {
    // given
    String itemId = ITEM_ID_EST_1;

    // when
    int quantity = mapper.getInventoryQuantity(itemId);

    // then
    assertThat(quantity).isEqualTo(10000);

  }

  @Test
  void updateInventoryQuantity() {
    // given
    String itemId = ITEM_ID_EST_1;
    Map<String, Object> params = new HashMap<>();
    params.put("itemId", itemId);
    params.put("increment", 10);

    // when
    mapper.updateInventoryQuantity(params);

    // then
    Integer quantity = jdbcTemplate.queryForObject("SELECT QTY FROM inventory WHERE itemid = ?", Integer.class, itemId);
    assertThat(quantity).isEqualTo(9990);

  }

}
