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

    @FXML
    private Label monitor;
    @FXML
    private float num1 = 0;
    private float num2 = 0;
    private String result = "";
    private String operator = "+";
    private boolean flag = true;
    private boolean afterError = false;
    private String sign = "";
    private boolean dot = false;
    private int whichNum = 1;
    boolean eq = false;

    public CalculatorApp() {
    }

    @FXML
    public void getNumber(ActionEvent e) {
        String s = ((Button) e.getSource()).getText();
        if (countingWindow.getText().equals("0")) {
            sign = "";
        }
        if (sign.equals("-")) {
            if (!countingWindow.getText().equals("-0")) {
                countingWindow.setText("-" + countingWindow.getText().substring(1) + s);
            } else {
                countingWindow.setText("-" + s);
            }
            flag = false;
        }
        if (sign.equals("")) {
            if (!countingWindow.getText().equals("0")) {
                countingWindow.setText(countingWindow.getText() + s);
            } else {
                countingWindow.setText(s);
            }
            flag = false;
        }
        if (flag) {
            countingWindow.setText("");
            flag = false;
        }
        if (whichNum == 1) {
            printMonitor(1);
        } else if (whichNum == 2) {
            printMonitor(3);
        }
        if (afterError) {
            Calculator.changeFontToNormal(countingWindow);
            afterError = false;
        }
    }

    @FXML
    public void addDot(ActionEvent e) {
        if (!dot && countingWindow.getText().length() != 0) {
            countingWindow.setText(countingWindow.getText() + ".");
            dot = true;
        }
        if (whichNum == 1) {
            printMonitor(1);
        } else if (whichNum == 2) {
            printMonitor(3);
        }
    }

    @FXML
    public void changeSign(ActionEvent e) {
        if (countingWindow.getText().length() != 0) {
            if (countingWindow.getText().charAt(0) == '-') {
                countingWindow.setText(countingWindow.getText().substring(1));
                sign = "";
            } else {
                countingWindow.setText("-" + countingWindow.getText());
                sign = "-";
            }
        } else {
            countingWindow.setText("-");
            sign = "-";
        }
        if (whichNum == 1) {
            printMonitor(1);
        } else if (whichNum == 2) {
            printMonitor(3);
        }
    }

    @FXML
    public void backspace(ActionEvent e) {
        String str = countingWindow.getText();
        if (str != null && str.length() > 0) {
            if (str.charAt(str.length() - 1) == '.') {
                dot = false;
            }
            countingWindow.setText(str.substring(0, str.length() - 1));
            if (countingWindow.getText().equals("") || countingWindow.getText().equals("-")) {
                countingWindow.setText("0");
                flag = true;
            }
        }
        if (whichNum == 1) {
            printMonitor(1);
        } else if (whichNum == 2) {
            printMonitor(3);
        }
    }

    @FXML
    public void reset(ActionEvent e) {
        countingWindow.setText("0");
        operator = "";
        dot = false;
        sign = "";
        result = "0";
        whichNum = 1;
        printMonitor(0);
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
                if (Float.parseFloat(Float.toString(num1)) / Math.floor(num1) == 1
                        || Float.parseFloat(Float.toString(num1)) == 0) {
                    num1 = (int) Float.parseFloat(countingWindow.getText());
                }
                sign = "";
                dot = false;
                printMonitor(2);
                whichNum = 2;
                countingWindow.setText("");
            } else {
                if (num1 == 0 || operator.equals("")) {
                    result = Calculator.count(num1, num2, "+");
                } else {
                    num2 = Float.parseFloat(countingWindow.getText());
                    result = Calculator.count(num1, num2, operator);
                    if (result.matches("-?\\d+(\\.\\d+)?")) {
                        if (result.contains(".")) {
                            dot = true;
                        }
                        if (Float.parseFloat(result) / Math.floor(Float.parseFloat(result)) == 1
                                || Float.parseFloat(result) == 0)
                            countingWindow.setText(Integer.toString((int) Float.parseFloat(result)));
                        else {
                            countingWindow.setText(result);
                        }
                    } else {
                        Calculator.changeFontToError(countingWindow);
                        countingWindow.setText(result);
                        afterError = true;
                    }
                }

                if (!result.contains(".")) {
                    dot = false;
                }
                printMonitor(5);
                whichNum = 1;
                operator = "";
                flag = true;
            }
        }
    }


    public void printMonitor(int state) {
        switch (state) {
            case 0:
                monitor.setText("");
                break;
            case 1:
                monitor.setText(countingWindow.getText());
                break;
            case 2:
                monitor.setText(countingWindow.getText() + " " + operator);
                break;
            case 3:
                if (!countingWindow.getText().equals("-")) {
                    if (Float.parseFloat(countingWindow.getText()) < 0)
                        monitor.setText(num1 + " " + operator + " (" + countingWindow.getText() + ")");
                    else
                        monitor.setText(num1 + " " + operator + " " + countingWindow.getText());
                }
                break;
            case 4:
                break;
            case 5:
                if (num2 < 0)
                    monitor.setText(num1 + " " + operator + " (" + num2 + ") " + "= " + result);
                else
                    monitor.setText(num1 + " " + operator + " " + num2 + " = " + result);
            default:
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        countingWindow.setText("0");
    }
}
