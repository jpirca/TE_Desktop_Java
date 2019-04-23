package sample.model;

public class ProductSupplier {
    public int ProductSupplierId;
    public int ProductId;
    public String ProdName;
    int SupplierId;
    public String SupName;

    public ProductSupplier(int productSupplierId, int productId, String prodName, int supplierId, String supName) {
        ProductSupplierId = productSupplierId;
        ProductId = productId;
        ProdName = prodName;
        SupplierId = supplierId;
        SupName = supName;
    }

    public int getProductSupplierId() {
        return ProductSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        ProductSupplierId = productSupplierId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public int getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(int supplierId) {
        SupplierId = supplierId;
    }

    public String getSupName() {
        return SupName;
    }

    public void setSupName(String supName) {
        SupName = supName;
    }

    @Override
    public String toString() {
        return ProdName + " --> " + SupName;
    }
}
