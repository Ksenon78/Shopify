package com.store.customer.handler;

import java.util.UUID;

public class GenerateUUIDs {
    public static void main(String[] args) {



        UUID addressId1 = UUID.randomUUID();
        UUID customerId1 = UUID.randomUUID();
        UUID shopId1 = UUID.randomUUID();
        UUID orderId1 = UUID.randomUUID();
        UUID lineItemId1 = UUID.randomUUID();


        UUID addressId2 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        UUID shopId2 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();
        UUID lineItemId2 = UUID.randomUUID();


        UUID addressId3 = UUID.randomUUID();
        UUID customerId3 = UUID.randomUUID();
        UUID shopId3 = UUID.randomUUID();
        UUID orderId3 = UUID.randomUUID();
        UUID lineItemId3 = UUID.randomUUID();


        System.out.println("Ayla Akçay Address ID 1: " + addressId1.toString());
        System.out.println("Ayla Akçay Customer ID 1: " + customerId1.toString());
        System.out.println("Ayla Akçay Shop ID 1: " + shopId1.toString());
        System.out.println("Ayla Akçay Order ID 1: " + orderId1.toString());
        System.out.println("Ayla Akçay LineItem ID 1: " + lineItemId1.toString());

        System.out.println("Burak Aydın Address ID 2: " + addressId2.toString());
        System.out.println("Burak Aydın Customer ID 2: " + customerId2.toString());
        System.out.println("Burak Aydın Shop ID 2: " + shopId2.toString());
        System.out.println("Burak Aydın Order ID 2: " + orderId2.toString());
        System.out.println("Burak Aydın LineItem ID 2:" + lineItemId2.toString());

        System.out.println("Aiden Marshall Address ID 3: " + addressId3.toString());
        System.out.println("Aiden Marshall Customer ID 3: " + customerId3.toString());
        System.out.println("Aiden Marshall Shop ID 3: " + shopId3.toString());
        System.out.println("Aiden Marshall Order ID 3: " + orderId3.toString());
        System.out.println("Aiden Marshall LineItem ID 3:" + lineItemId3.toString());
    }
}