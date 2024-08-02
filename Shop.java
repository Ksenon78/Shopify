package com.store.customer.entity;


import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shop_table")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shop_id", columnDefinition = "UUID")
    private UUID shopId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "shop")
    private List<Customer> customers;

    @OneToMany(mappedBy = "shop")
    private List<Order> orders;

    @OneToMany(mappedBy = "shop")
    private List<Address> addresses;

    @OneToMany(mappedBy = "shop")
    private List<Product> products;

    @OneToMany(mappedBy = "shop")
    private List<LineItem> lineItems;

    public Shop() {
    }

    public Shop(String name) {
        this.name = name;
    }

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

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
