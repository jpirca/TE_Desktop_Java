package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    public SimpleIntegerProperty ProductId;
    public SimpleStringProperty ProdName;

    public Product(int productId, String prodName) {
        ProductId = new SimpleIntegerProperty(productId);
        ProdName = new SimpleStringProperty(prodName);
    }

    public int getProductId() {
        return ProductId.get();
    }

    public void setProductId(int productId) {
        this.ProductId.set(productId);
    }

    public String getProdName() {
        return ProdName.get();
    }

    public void setProdName(String prodName) {
        this.ProdName.set(prodName);
    }

    @Override
    public String toString() {
        return this.getProductId() + " -> " + this.getProdName();
    }
}
