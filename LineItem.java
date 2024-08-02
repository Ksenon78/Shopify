package com.store.customer.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "line_item_table")
public class LineItem {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "line_item_id", columnDefinition = "UUID")
    private UUID lineItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_per_unit")
    private BigDecimal pricePerUnit;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public LineItem() {
    }

    public LineItem(UUID lineItemId, Order order, Product product, Integer quantity, BigDecimal pricePerUnit, Shop shop) {
        this.lineItemId = lineItemId;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.shop = shop;
    }



    public UUID getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(UUID lineItemId) {
        this.lineItemId = lineItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
