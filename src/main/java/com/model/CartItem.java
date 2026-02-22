
package com.model;

public class CartItem {

    private int id;          // cart table primary key
    private int userId;      
    private int productId;
    private int quantity;

    // DEFAULT CONSTRUCTOR (REQUIRED for DAO)
    public CartItem() {
    }

    // FULL CONSTRUCTOR
    public CartItem(int id, int userId, int productId, int quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // CONSTRUCTOR WITHOUT ID (for adding new cart item)
    public CartItem(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
