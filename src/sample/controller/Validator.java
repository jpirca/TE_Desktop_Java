package sample.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

public class Validator {
    public static boolean isFilled(JFXTextField field, String msg)
    {
        boolean result = false;
        if (field.getText().isEmpty() || field.getText() == null)
        {
            AlertBox.display("Input Error","The field \"" + msg + "\" cannot be empty.","OK");
            field.requestFocus();
            result = false;
        }
        else
        {
            result = true;
        }
        return result;
    }

    public static boolean isFilled(JFXPasswordField field, String msg)
    {
        boolean result = false;
        if (field.getText().isEmpty() || field.getText() == null)
        {
            AlertBox.display("Input Error","The field \"" + msg + "\" cannot be empty.","OK");
            field.requestFocus();
            result = false;
        }
        else
        {
            result = true;
        }
        return result;
    }

    public static boolean compareDates(JFXDatePicker minDate, String msg1, JFXDatePicker maxDate, String msg2)
    {
        boolean result = false;
        if(minDate.getValue().isAfter(maxDate.getValue()))
        {
            AlertBox.display("Input Error","The field " + msg1 + " have to be smaller than " + msg2,"OK");
            minDate.requestFocus();
            result = false;
        }
        else
        {
            result = true;
        }
        return result;
    }

    public static boolean isDouble(JFXTextField field, String msg)
    {
        boolean result = false;
        try {
            double num = Double.parseDouble(field.getText());
            result = true;
        } catch (NumberFormatException e) {
            AlertBox.display("Input Error","El field " + msg + " have to be numeric (Ex. 200.50)","OK");
            field.requestFocus();
            result = false;
        }
        return result;
    }

    public  static boolean validateEqualPass(String oldPass, String newPass)
    {
        boolean result = false;

        if(oldPass.equals(newPass))
        {
            result = true;
        }
        else
        {
            AlertBox.display("Error","New Password and Confirmation Password must be the same","OK");
            result = false;
        }
        return result;
    }
}
