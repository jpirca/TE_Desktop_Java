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
import sample.controller.AlertBox;
import sample.controller.DBHelper;
import sample.model.Package;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Export {
    public static void ToCSV() {
        ObservableList<Package> packageList = FXCollections.observableArrayList();
        String filename = System.getProperty("user.home") + "/Downloads/package.csv";

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


            fw.flush();
            fw.close();

            AlertBox.display("Success!","CSV File is created successfully.", "OK");
        } catch (Exception e) {
            AlertBox.display("Error","Error creating the file.", "Try again");
            e.printStackTrace();
        }
    }

    public static void ToPDF() {
        String filename ="C:\\Users\\801219\\Documents\\Java\\test.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Logo
            document.add(Image.getInstance("C:\\Users\\801219\\IdeaProjects\\TE_Desktop_Java\\src\\images\\logo.jpg"));

            document.add(new Paragraph(" "));

            Paragraph para = new Paragraph("Testing PDF export.");
            document.add(para);

            PdfPTable table = new PdfPTable(5);
            PdfPCell c1 = new PdfPCell(new Phrase("Heading 1"));

            /*PdfPTable headerTable=new PdfPTable(5);
            PdfPCell cellValue = new PdfPCell(new Paragraph("Header 1"));
            cellValue.setColspan(1);
            headerTable.addCell(cellValue);
            cellValue = new PdfPCell(new Paragraph("Header 2"));
            headerTable.addCell(cellValue);
            cellValue = new PdfPCell(new Paragraph("Header 3"));
            headerTable.addCell(cellValue);
            cellValue = new PdfPCell(new Paragraph("Header 4"));
            headerTable.addCell(cellValue);

            PdfPTable subHeaderTable = new PdfPTable(3);
            PdfPCell subHeadingCell = new PdfPCell(new Paragraph("Header 5"));
            subHeadingCell.setColspan(3);
            subHeaderTable.addCell(subHeadingCell);
            subHeaderTable.addCell("Sub heading 1");
            subHeaderTable.addCell("Sub heading 2");
            subHeaderTable.addCell("Sub heading 3");

            headerTable.addCell(subHeaderTable);

            document.add(headerTable);*/

            /*Connection conn = DBHelper.getConnection();
            String query = "SELECT BookingId, BookingDate, BookingNo FROM bookings";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(Integer.toString(rs.getInt(1)));
                fw.append(',');
                fw.append(rs.getString(2));
                /*fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append('\n');
            }
            conn.close(); */


            document.close();

            AlertBox.display("Success!","CSV File is created successfully.", "OK");
        } catch (Exception e) {
            AlertBox.display("Error","Error creating the file.", "Try again");
            e.printStackTrace();
        }
    }
}