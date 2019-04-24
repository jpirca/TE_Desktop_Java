package sample.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import sample.Controller;
import sample.model.AgentManagement;
import sample.model.Package;
import sample.model.ProductSupplier;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainProgram implements Initializable {

    //Integer agtID = 0;

    ObservableList<Package> packageList = FXCollections.observableArrayList();

    @FXML
    private TableView<Package> tbl_Packages;

    @FXML
    private JFXTreeTableView<Package> tvPackages;

    @FXML
    private ListView<ProductSupplier> lst_ProdSup;

    @FXML
    private ImageView img_btn_package;

    @FXML
    private ImageView img_btn_products;

    @FXML
    private ImageView img_btn_settings;

    @FXML
    private ImageView img_btn_logout;

    @FXML
    private Pane pan_packages;

    @FXML
    private Pane pan_products;

    @FXML
    private Pane pan_settings;

    @FXML
    private JFXTextField txt_PackageName;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXTextField txt_PackageID;

    @FXML
    private JFXTextField txt_PckDesc;

    @FXML
    private JFXTextField txt_PckBasePrice;

    @FXML
    private JFXTextField txt_AgencyCommission;

    @FXML
    private JFXButton btn_InsPackage;

    @FXML
    private JFXButton btn_NewPackage;

    @FXML

    private JFXTextField txt_OldPassword;

    @FXML
    private JFXTextField txt_NewPassword;

    @FXML
    private JFXTextField txt_ConfirmNewPassword;

    @FXML
    private JFXButton btn_UpdatePassword;

    private ListView<ProductSupplier> lst_ProdSupAvail;

    @FXML
    private JFXDatePicker txt_PckStartDate;

    @FXML
    private JFXDatePicker txt_PckEndDate;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Styling buttons
        styleMenuButtons(img_btn_package);
        styleMenuButtons(img_btn_products);
        styleMenuButtons(img_btn_settings);
        styleMenuButtons(img_btn_logout);
        // Call method to print the package table
        printPackageTable();
        fillPackageDetails(0);
        fillProductSupplier(0);
        fillProductSupplierAva(0);
        createTableFilter();
    }

    private void createTableFilter() {
        FilteredList<Package> fl = new FilteredList<>(packageList,e -> true);
        txt_search.setOnKeyReleased(event -> {
            txt_search.textProperty().addListener((observableValue, oldValue, newValue)->{
                fl.setPredicate((Predicate<? super Package>) pack->{
                    if (newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }
                    String lowerCase = newValue.toLowerCase();
                    if(pack.getPackageId().toString().contains(newValue))
                    {
                        return true;
                    }else if (pack.getPkgName().toLowerCase().contains(lowerCase))
                    {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Package> sortedList = new SortedList<>(fl);
            sortedList.comparatorProperty().bind(tbl_Packages.comparatorProperty());
            tbl_Packages.setItems(sortedList);
        });
    }


    private void fillProductSupplierAva(int pos) {
        int pkgId = tbl_Packages.getItems().get(pos).getPackageId();
        List<ProductSupplier> listPS = PackagesDB.getAllProductSupplier(pkgId);

        lst_ProdSupAvail.getItems().clear();

        for (ProductSupplier ps : listPS)
        {
            lst_ProdSupAvail.getItems().add(ps);
        }

    }

    //public void initData(int id) {agtID = id;}

    private void fillProductSupplier(int pos) {
        int pkgId = tbl_Packages.getItems().get(pos).getPackageId();
        List<ProductSupplier> listPS = PackagesDB.getProductSupplierByPkg(pkgId);

        lst_ProdSup.getItems().clear();

        for (ProductSupplier ps : listPS)
        {
            lst_ProdSup.getItems().add(ps);
        }


    }



    private void styleMenuButtons(ImageView menuButton) {
        // set a clip to apply rounded border to the original image.
        Rectangle clip = new Rectangle(menuButton.getFitWidth(), menuButton.getFitHeight());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        menuButton.setClip(clip);
    }

    private void styleMenuButtonsIn(ImageView menuButton) {
        // set a clip to apply rounded border to the original image.
        Rectangle clip = new Rectangle(menuButton.getFitWidth(), menuButton.getFitHeight());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        menuButton.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = menuButton.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        menuButton.setClip(null);

        // apply a shadow effect.
        menuButton.setEffect(new DropShadow(20, Color.BLACK));

        // store the rounded image in the imageView.
        menuButton.setImage(image);
    }

    private void styleMenuButtonsOut(ImageView menuButton) {
        menuButton.setClip(null);
        menuButton.setEffect(null);
    }


    private void printPackageTable() {

        packageList = PackagesDB.getAllPackage();

        // Column: ID
        TableColumn<Package,Integer> colPackageId = new TableColumn<>("ID");
        colPackageId.setPrefWidth(30.00);
        colPackageId.setCellValueFactory(new PropertyValueFactory<Package, Integer>("packageId"));

        // Column NAME
        TableColumn<Package,String> colPckName = new TableColumn<>("Name");
        colPckName.setPrefWidth(140.00);
        colPckName.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgName"));

        // Column START DATE
        TableColumn<Package,String> colPckStartDate = new TableColumn<>("Start Date");
        colPckStartDate.setPrefWidth(90.00);
        colPckStartDate.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgStartDate"));

        // Column END DATE
        TableColumn<Package,String> colPckEndDate = new TableColumn<>("End Date");
        colPckEndDate.setPrefWidth(90.00);
        colPckEndDate.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgEndDate"));

        // Column DESCRIPTION
        TableColumn<Package,String> colPckDesc = new TableColumn<>("Description");
        colPckDesc.setPrefWidth(280.00);
        colPckDesc.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgDesc"));

        // Column PRICE
        TableColumn<Package,Double> colPckPrice = new TableColumn<>("Price");
        colPckPrice.setPrefWidth(100.00);
        colPckPrice.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgBasePrice"));

        // Column COMMISSION
        TableColumn<Package,Double> colPckAgencyCommission = new TableColumn<>("Commission");
        colPckAgencyCommission.setPrefWidth(100.00);
        colPckAgencyCommission.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgAgencyCommission"));

        // Adding list with packages to the table
        tbl_Packages.setItems(packageList);
        // Adding column title to the table
        tbl_Packages.getColumns().addAll(colPackageId,colPckName,colPckStartDate,colPckEndDate,colPckDesc,colPckPrice,colPckAgencyCommission);
    }

    @FXML
    void on_ClickTablePackage(MouseEvent event) {
        int position = tbl_Packages.getSelectionModel().selectedIndexProperty().get();
        fillPackageDetails(position);
        fillProductSupplier(position);
        fillProductSupplierAva(position);
    }

    @FXML
    void on_ClickBtnExport(MouseEvent event) {
        Export.ToCSV();
    }

    @FXML
    void on_ClickAddProdSup(ActionEvent event) {
        ProductSupplier ps = lst_ProdSupAvail.getSelectionModel().getSelectedItem();
        int pkgId = Integer.parseInt(txt_PackageID.getText());

        if (ps==null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"You must select a Product/Suppl;ier to be added");
            alert.showAndWait();
        }
        else
        {
            boolean result = PackagesDB.insertProdSup(ps,pkgId);
            if (result)
            {
                int position = tbl_Packages.getSelectionModel().selectedIndexProperty().get();
                if (position<0)
                {
                    position = 0;
                }
                fillProductSupplier(position);
                fillProductSupplierAva(position);
            }
        }

    }

    @FXML
    void on_ClickDelProdSup(ActionEvent event) {
        ProductSupplier ps = lst_ProdSup.getSelectionModel().getSelectedItem();
        int pkgId = Integer.parseInt(txt_PackageID.getText());

        if (ps==null){
            AlertBox.display("Error selection","You must select a Product/Supplier to be deleted","OK");
        }
        else
        {
            boolean result = PackagesDB.deleteProdSup(ps,pkgId);
            if (result)
            {
                int position = tbl_Packages.getSelectionModel().selectedIndexProperty().get();
                if (position<0)
                {
                    position = 0;
                }
                fillProductSupplier(position);
                fillProductSupplierAva(position);
            }
        }
    }

    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    private void fillPackageDetails(int position) {
        txt_PackageID.setText(String.valueOf(packageList.get(position).getPackageId()));
        txt_PackageName.setText(String.valueOf(packageList.get(position).getPkgName()));
        txt_PckStartDate.setValue(LOCAL_DATE(packageList.get(position).getPkgStartDate().toString()));
        txt_PckEndDate.setValue(LOCAL_DATE(String.valueOf(packageList.get(position).getPkgEndDate())));
        txt_PckDesc.setText(packageList.get(position).getPkgDesc());
        txt_PckBasePrice.setText(String.valueOf(packageList.get(position).getPkgBasePrice()));
        txt_AgencyCommission.setText(String.valueOf(packageList.get(position).getPkgAgencyCommission()));
    }

    @FXML
    void on_MouseEntered_Package(MouseEvent event) {
        styleMenuButtonsIn(img_btn_package);
    }

    @FXML
    void on_MouseExit_Package(MouseEvent event) {
        styleMenuButtonsOut(img_btn_package);
    }

    @FXML
    void on_MouseEntered_Logout(MouseEvent event) {
        styleMenuButtonsIn(img_btn_logout);
    }

    @FXML
    void on_MouseEntered_Products(MouseEvent event) {
        styleMenuButtonsIn(img_btn_products);
    }

    @FXML
    void on_MouseEntered_Settings(MouseEvent event) {
        styleMenuButtonsIn(img_btn_settings);
    }

    @FXML
    void on_MouseExit_Logout(MouseEvent event) {
        styleMenuButtonsOut(img_btn_logout);
    }

    @FXML
    void on_MouseExit_Products(MouseEvent event) {
        styleMenuButtonsOut(img_btn_products);
    }

    @FXML
    void on_MouseExit_Settings(MouseEvent event) {
        styleMenuButtonsOut(img_btn_settings);
    }

    @FXML
    void on_ClickPackage(MouseEvent event) {
        pan_packages.setVisible(true);
        pan_settings.setVisible(false);
        pan_products.setVisible(false);
    }

    @FXML
    void on_ClickProducts(MouseEvent event) {
        pan_products.setVisible(true);
        pan_settings.setVisible(false);
        pan_packages.setVisible(false);
    }

    @FXML
    void on_ClickSettings(MouseEvent event) {
        pan_settings.setVisible(true);
        pan_products.setVisible(false);
        pan_packages.setVisible(false);
    }

    @FXML
    void on_ClickBtnUpdate(ActionEvent event) {
        boolean result = false;
        Package oldPck;

        if ((Validator.isFilled(txt_PackageName,"Package Name")) &&
                (Validator.isFilled(txt_PckDesc,"Package Description")) &&
                (Validator.isFilled(txt_AgencyCommission,"Agency Commission")) &&
                (Validator.isFilled(txt_PckBasePrice,"Base Price")) &&
                (Validator.compareDates(txt_PckStartDate,"Start Date",txt_PckEndDate,"End Date")) &&
                (Validator.isDouble(txt_PckBasePrice,"Base Price")) &&
                (Validator.isDouble(txt_AgencyCommission,"Agency Commission")))
        {
            if (tbl_Packages.getSelectionModel().getSelectedItem() == null) {
                oldPck = tbl_Packages.getItems().get(0);
            } else {
                oldPck = tbl_Packages.getSelectionModel().getSelectedItem();
            }
            Package newPck = null;
            newPck = new Package(Integer.parseInt(txt_PackageID.getText()),
                    txt_PackageName.getText(),
                    oldPck.getPkgStartDate(),
                    oldPck.getPkgEndDate(),
                    txt_PckDesc.getText(),
                    Double.parseDouble(txt_PckBasePrice.getText()),
                    Double.parseDouble(txt_AgencyCommission.getText()));
            result = PackagesDB.updatePackage(newPck, oldPck);

            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The record was updated successfully");
                alert.showAndWait();
                packageList.removeAll();
                tbl_Packages.getColumns().clear();
                printPackageTable();
            }
        }
    }

    @FXML
    void on_ClickBtnInsert(ActionEvent event) {
        if ((Validator.isFilled(txt_PackageName,"Package Name")) &&
                (Validator.isFilled(txt_PckDesc,"Package Description")) &&
                (Validator.isFilled(txt_AgencyCommission,"Agency Commission")) &&
                (Validator.isFilled(txt_PckBasePrice,"Base Price")) &&
                (Validator.compareDates(txt_PckStartDate,"Start Date",txt_PckEndDate,"End Date")) &&
                (Validator.isDouble(txt_PckBasePrice,"Base Price")) &&
                (Validator.isDouble(txt_AgencyCommission,"Agency Commission")))
        {
            Package newPkg = new Package(0,
                    txt_PackageName.getText(),
                    java.sql.Date.valueOf(LOCAL_DATE(txt_PckStartDate.getValue().toString())),
                    java.sql.Date.valueOf(LOCAL_DATE(txt_PckEndDate.getValue().toString())),
                    txt_PckDesc.getText(),
                    Double.parseDouble(txt_PckBasePrice.getText()),
                    Double.parseDouble(txt_AgencyCommission.getText()));
            boolean result = PackagesDB.insertPackage(newPkg);
            if (result)
            {
                AlertBox.display("Insert new Package","The packages was created","OK");
                packageList.removeAll();
                tbl_Packages.getColumns().clear();
                printPackageTable();

                fillProductSupplier(packageList.size()-1);
                fillProductSupplierAva(packageList.size()-1);
            }
        }
    }

    @FXML
    void on_ClickBtnNew(ActionEvent event) {
        clearTextFields();
        btn_InsPackage.setDisable(false);

    }

    private void clearTextFields() {
        txt_PackageID.clear();
        txt_PackageName.clear();
        txt_PckStartDate.setValue(LOCAL_DATE(LocalDate.now().toString()));
        txt_PckEndDate.setValue(LOCAL_DATE(LocalDate.now().toString()));
        txt_PckDesc.clear();
        txt_PckBasePrice.clear();
        txt_AgencyCommission.clear();
    }

    @FXML
    void on_ClickBtnChangePassword(ActionEvent event) {
        String oldPassword = txt_OldPassword.getText();
        String newPassword = txt_NewPassword.getText();
        Integer agentID = Controller.getAgtID();
        try {
            AgentManagement.ChangePassword(agentID, oldPassword, newPassword);
            AlertBox.display("Success", "Password successfully updated.", "OK");
        } catch (SQLException e) {
            AlertBox.display("Error", "Database error", "Call tech support");
            e.printStackTrace();
        }
    }

    void on_ClickBtnLogout(MouseEvent event) {
        System.exit(0);
    }
}
