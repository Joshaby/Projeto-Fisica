module Login {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.rmi;

    opens br.edu.ifpb to javafx.fxml;
    exports br.edu.ifpb;
}