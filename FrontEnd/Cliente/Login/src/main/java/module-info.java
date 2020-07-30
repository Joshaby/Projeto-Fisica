module Login {
    requires javafx.fxml;
    requires javafx.controls;

    opens br.edu.ifpb to javafx.fxml;
    exports br.edu.ifpb;
}