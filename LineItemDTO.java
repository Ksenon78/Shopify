package com.store.customer.DTO;

import java.math.BigDecimal;
import java.util.UUID;

public class LineItemDTO {

    private UUID lineItemId;
    private UUID orderId;
    private UUID productId;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private UUID shopId;

    public UUID getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(UUID lineItemId) {
        this.lineItemId = lineItemId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public UUID getShopId() {
        return shopId;
    }

    public void setShopId(UUID shopId) {
        this.shopId = shopId;
    }
}
