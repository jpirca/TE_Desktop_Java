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
import java.text.SimpleDateFormat;
import java.util.List;

public class Export {
    public static void ToCSV() {
        ObservableList<Package> packageList = FXCollections.observableArrayList();
        String filename = System.getProperty("user.home") + "/Downloads/package.csv";


        //String filename ="C:\\Users\\801219\\Documents\\Java\\test.csv";
        try {
            FileWriter fw = new FileWriter(filename);
            packageList = PackagesDB.getAllPackage();
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




//            Connection conn = DBHelper.getConnection();
//            String query = "SELECT BookingId, BookingDate, BookingNo FROM bookings";
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                fw.append(Integer.toString(rs.getInt(1)));
//                fw.append(',');
//                fw.append(rs.getString(2));
//                /*fw.append(',');
//                fw.append(rs.getString(3));
//                fw.append(',');
//                fw.append(rs.getString(4));*/
//                fw.append('\n');
//            }
//
            fw.flush();
            fw.close();
            //conn.close();

            AlertBox.display("Success!","CSV File is created successfully.", "OK");
        } catch (Exception e) {
            AlertBox.display("Error","Error creating the file.", "Try again");
            e.printStackTrace();
        }
    }

    public static void InvoiceToPDF(List<Invoice> toinvoice) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String filename = System.getProperty("user.home") + "/Downloads/invoice.pdf";
        String logopath = System.getProperty("user.home") + "/Documents/GitHub/TE_Desktop_Java/src/images/company_logo.png";
        //String filename ="C:\\Users\\801219\\Documents\\Java\\test.pdf";
        Double amountDue = 0.00;
        for (Invoice record : toinvoice) {
            amountDue += record.getPkgBasePrice() + record.getBasePrice();
        }

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Logo
            document.add(Image.getInstance(logopath));

            document.add(new Paragraph(" "));

            Phrase fullname = new Phrase(toinvoice.get(0).getCustFirstName() + " " + toinvoice.get(0).getCustLastName());
            Paragraph accountNo = new Paragraph(Integer.toString(toinvoice.get(0).getCustomerId()));
            //Phrase accountNo = new Phrase(Integer.toString(toinvoice.get(0).getCustomerId()));
            Phrase total = new Phrase(Double.toString(amountDue));

            document.add(new Paragraph("Name: " + fullname));
            document.add(new Paragraph("Account No.: " + accountNo));

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);

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
                table.addCell(Double.toString(record.getPkgBasePrice()));
                table.addCell(Double.toString(record.getBasePrice()));
            }

            document.add(table);

            document.add(new Paragraph("Total amount due: " + total));

            document.close();

            AlertBox.display("Success!","Invoice created successfully.", "OK");
        } catch (Exception e) {
            AlertBox.display("Error","Error creating invoice.", "OK");
            e.printStackTrace();
        }
    }

}