package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    public SimpleIntegerProperty SupplierId;
    public SimpleStringProperty SupName;

    public Supplier(int supplierId, String supName) {
        SupplierId = new SimpleIntegerProperty(supplierId);
        SupName = new SimpleStringProperty(supName);
    }

    public int getSupplierId() {
        return SupplierId.get();
    }
    public void setSupplierId(int supplierId) {
        this.SupplierId.set(supplierId);
    }

    public String getSupName() {
        return SupName.get();
    }

    public void setSupName(String supName) {
        this.SupName.set(supName);
    }

    @Override
    public String toString() {
        return this.getSupplierId() + " -> " + this.getSupName();
    }
}
