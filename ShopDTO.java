package com.store.customer.DTO;

import java.util.List;
import java.util.UUID;

public class ShopDTO {

    private UUID shopId;
    private String name;
    private List<UUID> customerIds;
    private List<UUID> orderIds;
    private List<UUID> addressIds;
    private List<UUID> productIds;
    private List<UUID> lineItemIds;

    public UUID getShopId() {
        return shopId;
    }

    public void setShopId(UUID shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<UUID> customerIds) {
        this.customerIds = customerIds;
    }

    public List<UUID> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<UUID> orderIds) {
        this.orderIds = orderIds;
    }

    public List<UUID> getAddressIds() {
        return addressIds;
    }

    public void setAddressIds(List<UUID> addressIds) {
        this.addressIds = addressIds;
    }

    public List<UUID> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<UUID> productIds) {
        this.productIds = productIds;
    }

    public List<UUID> getLineItemIds() {
        return lineItemIds;
    }

    public void setLineItemIds(List<UUID> lineItemIds) {
        this.lineItemIds = lineItemIds;
    }
}
