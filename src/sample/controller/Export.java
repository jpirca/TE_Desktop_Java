package sample.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Invoice;
import sample.model.Package;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Export {
    public static void ToCSV(ObservableList<Package> packageList) {
        //ObservableList<Package> packageList = FXCollections.observableArrayList();
        String filename = System.getProperty("user.home") + "/Downloads/package.csv";

        try {
            FileWriter fw = new FileWriter(filename);
            //packageList = PackagesDB.getAllPackage();
            for(Package pck:packageList)
            {
                fw.append(pck.getPackageId().toString());
                fw.append(";");
                fw.append(pck.getPkgName());
                fw.append(";");
                fw.append(pck.getPkgStartDate().toString());
                fw.append(";");
                fw.append(pck.getPkgEndDate().toString());
                fw.append(";");
                fw.append(pck.getPkgDesc());
                fw.append(";");
                fw.append(Double.toString(pck.getPkgBasePrice()));
                fw.append(";");
                fw.append(Double.toString(pck.getPkgAgencyCommission()));
                fw.append('\n');

            }


            fw.flush();
            fw.close();

            AlertBox.display("Success!","CSV File is created successfully.", "OK");
        } catch (Exception e) {
            AlertBox.display("Error","Error creating the file.", "Try again");
            e.printStackTrace();
        }
    }

    public static void InvoiceToPDF(List<Invoice> toinvoice) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat money = new DecimalFormat("##.00");
        String filename = System.getProperty("user.home") + "/Downloads/invoice.pdf";
        String logopath = System.getProperty("user.dir") + "/src/images/company_logo.png";
        Double amountDue = 0.00;
        for (Invoice record : toinvoice) {
            amountDue += record.getPkgBasePrice() + record.getBasePrice();
        }

        try {
            Image img = Image.getInstance(logopath);
            img.setWidthPercentage(40);
            img.setScaleToFitHeight(true);
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Logo
            document.add(img);

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Name: " + toinvoice.get(0).getCustFirstName() + " " + toinvoice.get(0).getCustLastName()));
            document.add(new Paragraph("Account No: " + toinvoice.get(0).getCustomerId()));

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);            
            table.setWidthPercentage(100);
            table.setWidths(new float[]{180, 215, 180, 250, 490, 180, 150});

            PdfPCell h1 = new PdfPCell(new Phrase("Booking No."));
            table.addCell(h1);
            PdfPCell h2 = new PdfPCell(new Phrase("Booking Date"));
            table.addCell(h2);
            PdfPCell h3 = new PdfPCell(new Phrase("Traveler Count"));
            table.addCell(h3);
            PdfPCell h4 = new PdfPCell(new Phrase("Package Name"));
            table.addCell(h4);
            PdfPCell h5 = new PdfPCell(new Phrase("Description"));
            table.addCell(h5);
            PdfPCell h6 = new PdfPCell(new Phrase("Package Price"));
            table.addCell(h6);
            PdfPCell h7 = new PdfPCell(new Phrase("Base Price"));
            table.addCell(h7);
            table.setHeaderRows(1);

            for (Invoice record : toinvoice) {
                table.addCell(record.getBookingNo());
                table.addCell(dateFormat.format(record.getBookingDate()));
                table.addCell(Integer.toString(record.getTravelerCount()));
                table.addCell(record.getPkgName());
                table.addCell(record.getDescription());
                table.addCell(money.format((Double)record.getPkgBasePrice()));
                table.addCell(money.format((Double)record.getBasePrice()));
            }

            document.add(table);

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Total amount due: " + "$ " + money.format(amountDue)));

            document.close();

            AlertBox.display("Success!","Invoice created successfully.", "OK");
        } catch (Exception e) {
            AlertBox.display("Error","Error creating invoice.", "OK");
            e.printStackTrace();
        }
    }

}
