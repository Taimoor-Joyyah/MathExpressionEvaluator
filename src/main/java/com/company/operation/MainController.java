package com.company.operation;


import com.company.operation.operation.exception.InCompleteException;
import com.company.operation.operation.exception.InvalidExpressionException;
import com.company.operation.operation.Operation;
import com.company.operation.operation.OperationParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainController {
    @FXML
    public TextArea inputTextField;
    @FXML
    private TextField outTextField;
    @FXML
    private Text warningTextField;
    @FXML
    private CheckBox autoEvaluateCheckBox;
    @FXML
    private Button enterButton;
    @FXML
    private Button openBracketButton;
    @FXML
    private Button periodButton;
    @FXML
    private Button closeBracketButton;
    @FXML
    private Button spaceButton;
    @FXML
    private Button button_2;
    @FXML
    private Button button_1;
    @FXML
    private Button button_4;
    @FXML
    private Button button_3;
    @FXML
    private Button button_0;
    @FXML
    private Button adderButton;
    @FXML
    private Button backButton;
    @FXML
    private Button subtractorButton;
    @FXML
    private Button button_9;
    @FXML
    private Button multiplierButton;
    @FXML
    private Button button_6;
    @FXML
    private Button button_5;
    @FXML
    private Button button_8;
    @FXML
    private Button dividerButton;
    @FXML
    private Button button_7;
    @FXML
    private Button calculateButton;

    public void initialize() {
        warningTextField.setText("");
        inputTextField.setOnKeyTyped(keyEvent -> updated());
        autoEvaluateCheckBox.setOnAction(actionEvent -> calculateButton.setDisable(autoEvaluateCheckBox.isSelected()));
        calculateButton.setOnAction(actionEvent -> updateResult());

        button_0.setOnAction(actionEvent -> appendInputText('0'));
        button_1.setOnAction(actionEvent -> appendInputText('1'));
        button_2.setOnAction(actionEvent -> appendInputText('2'));
        button_3.setOnAction(actionEvent -> appendInputText('3'));
        button_4.setOnAction(actionEvent -> appendInputText('4'));
        button_5.setOnAction(actionEvent -> appendInputText('5'));
        button_6.setOnAction(actionEvent -> appendInputText('6'));
        button_7.setOnAction(actionEvent -> appendInputText('7'));
        button_8.setOnAction(actionEvent -> appendInputText('8'));
        button_9.setOnAction(actionEvent -> appendInputText('9'));

        dividerButton.setOnAction(actionEvent -> appendInputText('/'));
        multiplierButton.setOnAction(actionEvent -> appendInputText('*'));
        adderButton.setOnAction(actionEvent -> appendInputText('+'));
        subtractorButton.setOnAction(actionEvent -> appendInputText('-'));
        openBracketButton.setOnAction(actionEvent -> appendInputText('('));
        closeBracketButton.setOnAction(actionEvent -> appendInputText(')'));
        periodButton.setOnAction(actionEvent -> appendInputText('.'));
        spaceButton.setOnAction(actionEvent -> appendInputText(' '));
        enterButton.setOnAction(actionEvent -> appendInputText('\n'));
        backButton.setOnAction(actionEvent -> backSpaceAction());
    }

    private void appendInputText(char character) {
        inputTextField.setText(inputTextField.getText() + character);
        updated();
    }

    private void backSpaceAction() {
        String text = inputTextField.getText();
        inputTextField.setText(text.substring(0, text.length() - 1));
        updated();
    }

    private void updated() {
        if (autoEvaluateCheckBox.isSelected())
            updateResult();
    }

    private void updateResult() {
        Operation operation = null;

        try {
            operation = OperationParser.StringToOperation(inputTextField.getText());
        } catch (InvalidExpressionException e) {
            warningTextField.setText(e.getMessage());
        } catch (InCompleteException e) {
            if (!autoEvaluateCheckBox.isSelected())
                warningTextField.setText(e.getMessage());
        }

        if (operation != null) {
            warningTextField.setText("");
            outTextField.setText(Double.toString(operation.calculate()));
        }
    }
}
