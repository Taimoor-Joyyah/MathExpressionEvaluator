module com.company.operation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.company.operation to javafx.fxml;
    exports com.company.operation;
}