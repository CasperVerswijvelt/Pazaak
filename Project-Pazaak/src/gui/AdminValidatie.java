/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.DatabaseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Casper
 */
public class AdminValidatie extends VBox {

    private final BorderPaneController parent;
    private final DomeinController dc;
    private final ResourceBundle r;

    private Label lblTitel;
    private TextField txfUser;
    private PasswordField txfPassword;
    private Button btnSubmit;
    private Label lblError;

    public AdminValidatie(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.parent = parent;

        buildGui();
    }

    private void buildGui() {
        lblTitel = new Label("Admin panel validation");
        txfUser = new TextField();
        txfUser.setPromptText(r.getString("NEWADMINUSERNAME"));
        txfUser.setAlignment(Pos.CENTER);
        txfPassword = new PasswordField();
        txfPassword.setPromptText(r.getString("NEWADMINPASSWORD"));
        txfPassword.setAlignment(Pos.CENTER);
        btnSubmit = new Button(r.getString("VALIDATE"));
        lblError = new Label();
        lblError.setTextFill(Color.RED);

        btnSubmit.setOnAction((ActionEvent event) -> {
            valideer();
        });
        txfPassword.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                valideer();
            }
        });

        this.getChildren().addAll(lblTitel, txfUser, txfPassword, btnSubmit, lblError);
        this.setSpacing(20);
        this.setMaxSize(300, 300);
        txfUser.requestFocus();
        txfUser.requestFocus();

    }

    private void valideer() {
        try {
            if (dc.valideerAdmin(txfUser.getText(), txfPassword.getText())) {
                parent.naarAdminPaneel();
            } else {
                lblError.setText(r.getString("INVALIDCREDENTIALS"));
            }
        } catch (DatabaseException e) {
            Alert DBAlert = new Alert(Alert.AlertType.ERROR);
            DBAlert.setTitle("Pazaak");
            DBAlert.setContentText(r.getString("DATABASEERROR"));
        }
    }

}
