package sample.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Package extends RecursiveTreeObject<Package> {
    public SimpleStringProperty packageId;
    public SimpleStringProperty pkgName;
    public SimpleStringProperty pkgStartDate;
    public SimpleStringProperty pkgEndDate;
    public SimpleStringProperty pkgDesc;
    public SimpleDoubleProperty pkgBasePrice;
    public SimpleDoubleProperty pkgAgencyCommission;

    public Package(String packageId, String pkgName, String pkgStartDate, String pkgEndDate, String pkgDesc, double pkgBasePrice, double pkgAgencyCommission) {
        this.packageId = new SimpleStringProperty(packageId);
        this.pkgName = new SimpleStringProperty(pkgName);
        this.pkgStartDate = new SimpleStringProperty(pkgStartDate);
        this.pkgEndDate = new SimpleStringProperty(pkgEndDate);
        this.pkgDesc = new SimpleStringProperty(pkgDesc);
        this.pkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        this.pkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);

    }


}
