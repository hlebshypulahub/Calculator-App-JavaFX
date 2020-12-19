package HlebShypula.Calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

class Calculator {
    public static String count(float num1, float num2, String operator) {
        switch (operator) {
            case "×":
                return Float.toString(num1 * num2);
            case "÷":
                if (num2 == 0.0)
                    return "You cannot divide by 0!";
                return Float.toString(num1 / num2);
            case "+":
                return Float.toString(num1 + num2);
            case "-":
                return Float.toString(num1 - num2);
            default:
                break;
        }

        return "0";
    }

    public static void changeFontToError(Label lb) {
        lb.setFont(Font.font(25));
        lb.setTextFill(Color.web("#e40101"));
    }

    public static void changeFontToNormal(Label lb) {
        lb.setFont(Font.font(46));
        lb.setTextFill(Color.web("#303030"));
    }
}

public class CalculatorApp implements Initializable {
    @FXML
    private Label countingWindow;
    private float num1 = 0;
    private float num2 = 0;
    private String result = "";
    private String operator;
    private boolean flag = true;
    private boolean afterError = false;

    @FXML
    public void getNumber(ActionEvent e) {
        if (flag) {
            countingWindow.setText("");
            flag = false;
        }
        if (afterError) {
            Calculator.changeFontToNormal(countingWindow);
            afterError = false;
        }
        String s = ((Button) e.getSource()).getText();
        countingWindow.setText(countingWindow.getText() + s);
    }

    @FXML
    public void getPi(ActionEvent e) {
        if (flag) {
            countingWindow.setText("");
            flag = false;
        }
        countingWindow.setText(Float.toString((float) Math.PI));
    }

    @FXML
    public void backspace(ActionEvent e) {
        String str = countingWindow.getText();
        if (str != null && str.length() > 0) {
            countingWindow.setText(str.substring(0, str.length() - 1));
        }
    }

    @FXML
    public void reset(ActionEvent e) {
        countingWindow.setText("0");
        operator = "";
        result = "0";
    }

    @FXML
    private void gotoGH() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URL("https://github.com/hlebshypulahub").toURI());
    }

    @FXML
    private void gotoFB() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URL("https://www.facebook.com/profile.php?id=100007937072709").toURI());
    }

    @FXML
    public void getOperator(ActionEvent e) {
        String o = ((Button) e.getSource()).getText();
        if (!countingWindow.getText().equals("")) {
            if (!o.equals("=")) {
                operator = o;
                num1 = Float.parseFloat(countingWindow.getText());
                countingWindow.setText("");
            } else {
                num2 = Float.parseFloat(countingWindow.getText());
                result = Calculator.count(num1, num2, operator);
                if (result.matches("-?\\d+(\\.\\d+)?")) {
                    if (Float.parseFloat(result) / Math.floor(Float.parseFloat(result)) == 1)
                        countingWindow.setText(Integer.toString((int) Float.parseFloat(result)));
                    else
                        countingWindow.setText(result);
                } else {
                    Calculator.changeFontToError(countingWindow);
                    countingWindow.setText(result);
                    afterError = true;
                }

                operator = "";
                flag = true;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
