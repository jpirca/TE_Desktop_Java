package sample.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.model.Package;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainProgram implements Initializable {

    @FXML
    private JFXTreeTableView<Package> tvPackages;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Call method to print the package table
        printPackageTable();

        tvPackages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Package>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Package>> observable, TreeItem<Package> oldValue, TreeItem<Package> newValue) {
                if(tvPackages.getSelectionModel().getSelectedItem() != null){
                    TreeTableView.TreeTableViewSelectionModel<Package> selecModel = tvPackages.getSelectionModel();
                    ObservableList selectCell = selecModel.getSelectedCells();
                    TablePosition tp = (TablePosition) selectCell.get(0);
                    Package pck = (Package) tp.getTableColumn().getCellData(newValue);
                    System.out.println("Selected value" + pck.pkgName);


                }
            }
        });

    }

    private void printPackageTable() {
        ObservableList<Package> packageList = FXCollections.observableArrayList();
        packageList = PackagesDB.getAllPackage();

        // Column: ID
        JFXTreeTableColumn<Package,String> tiPckId = new JFXTreeTableColumn("ID");
        tiPckId.setPrefWidth(30.00);
        tiPckId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Package, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Package, String> param) {
                return param.getValue().getValue().packageId; // :D
            }
        });



        // Column NAME
        JFXTreeTableColumn<Package,String> tiName = new JFXTreeTableColumn("Name");
        tiName.setPrefWidth(140.00);

        tiName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Package, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Package, String> param) {
                return param.getValue().getValue().pkgName;
            }
        });

//        // Column START DATE
        JFXTreeTableColumn<Package,String> tiStartD = new JFXTreeTableColumn("Start Date");
        tiStartD.setPrefWidth(90.00);

        tiStartD.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Package, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Package, String> param) {
                return param.getValue().getValue().pkgStartDate;
            }
        });
//
//        // Column END DATE
        JFXTreeTableColumn<Package,String> tiEndD = new JFXTreeTableColumn("End Date");
        tiEndD.setPrefWidth(90.00);

        tiEndD.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Package, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Package, String> param) {
                return param.getValue().getValue().pkgEndDate;
            }
        });
//
//        // Column DESCRIPTION
        JFXTreeTableColumn<Package,String> tiDesc = new JFXTreeTableColumn("Description");
        tiDesc.setPrefWidth(280.00);

        tiDesc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Package, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Package, String> param) {
                return param.getValue().getValue().pkgDesc;
            }
        });
//
//        // Column PRICE
        JFXTreeTableColumn<Package,String> tiBaseP = new JFXTreeTableColumn("Base Price");
        tiBaseP.setPrefWidth(100.00);

        tiBaseP.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Package, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Package, String> param) {
                return param.getValue().getValue().pkgBasePrice.asString();
            }
        });
//
//        // Column COMMISSION
        JFXTreeTableColumn<Package,String> tiCommi = new JFXTreeTableColumn("Commission");
        tiCommi.setPrefWidth(100.00);

        tiCommi.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Package, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Package, String> param) {
                return param.getValue().getValue().pkgAgencyCommission.asString();
            }
        });

//        ObservableList<Package> packageListNew = FXCollections.observableArrayList();
//        packageListNew.add(new Package(1,"Package 1", new Date(),new Date(), "Description 1", 2000.00, 100.00));
//        packageListNew.add(new Package(2,"Package 2", new Date(),new Date(), "Description 1", 2000.00, 100.00));


        final TreeItem<Package> root = new RecursiveTreeItem<Package>(packageList, RecursiveTreeObject::getChildren);
        //tvPackages.getColumns().setAll(tiName,tiStartD,tiEndD,tiDesc,tiBaseP,tiCommi);
        tvPackages.getColumns().setAll(tiPckId, tiName,tiDesc,tiStartD,tiEndD,tiBaseP,tiCommi);
        tvPackages.setRoot(root);
        tvPackages.setShowRoot(false);


    }


}
