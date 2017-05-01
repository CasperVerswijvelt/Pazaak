/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class AdminPanelController extends GridPane {

    private final BorderPaneController parent;
    private final DomeinController dc;
    private final ResourceBundle r;

    private List spelerLijst;
    private String[][] wedstrijdList;
    private String[][] gekochteKaarten;
    private String[][] nietGekochteKaarten;

    @FXML
    private ComboBox<String> cbSpelerSelectie;
    @FXML
    private Button btnSaveSpeler;
    @FXML
    private Button btnDeletePlayer;
    @FXML
    private ComboBox<String> cbSelecteerGame;
    @FXML
    private Button btnDeleteGame;
    @FXML
    private TextField txfSpelerNaam;
    @FXML
    private TextField txfSpelerGeboortedatum;
    @FXML
    private TextField txfKrediet;
    @FXML
    private Label lblPlayerOptionsTitle;
    @FXML
    private Label lblGameOptions;
    @FXML
    private Label lblNewAdmin;
    @FXML
    private TextField txfNewAdminUsername;
    @FXML
    private PasswordField txfNewAdminPassword;
    @FXML
    private Button btnCreateNewAdmin;
    @FXML
    private Label lblError;
    @FXML
    private ComboBox<String> cbRemoveCard;
    @FXML
    private ComboBox<String> cbGiveCard;
    @FXML
    private Button btnRemoveCard;
    @FXML
    private Button btnGiveCard;

    /**
     * Initializes the controller class.
     */
    public AdminPanelController(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        buildGUI();
    }

    private void buildGUI() {
        laadSpelersInComboBox();
        cbSelecteerGame.getSelectionModel().selectFirst();
        cbSelecteerGame.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
            }

        });
        laadGamesInComboBox();
        cbSpelerSelectie.getSelectionModel().selectFirst();
        selecteerSpeler();
        cbSpelerSelectie.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                selecteerSpeler();
            }

        });

    }

    private void laadSpelersInComboBox() {
        try {
            spelerLijst = dc.geefAlleSpelerNamen();
        } catch (DatabaseException e) {
        }
        ObservableList<String> comboLijst = FXCollections.observableArrayList(spelerLijst);

        cbSpelerSelectie.setItems(comboLijst);

    }

    private void laadGamesInComboBox() {
        try {
            wedstrijdList = dc.geefWedstrijdenOverzicht();
        } catch (DatabaseException e) {
        }

        if (wedstrijdList.length == 0) {
            btnDeleteGame.setDisable(true);
            cbSelecteerGame.setDisable(true);
        } else {
            btnDeleteGame.setDisable(false);
            cbSelecteerGame.setDisable(false);
        }

        String[] wedstrijdenStrings = new String[wedstrijdList.length];
        for (int i = 0; i < wedstrijdList.length; i++) {
            wedstrijdenStrings[i] = wedstrijdList[i][0] + " [" + wedstrijdList[i][1] + " " + wedstrijdList[i][2] + " - " + wedstrijdList[i][4] + " " + wedstrijdList[i][3] + "]";
        }
        ObservableList<String> comboLijst = FXCollections.observableArrayList(wedstrijdenStrings);

        cbSelecteerGame.setItems(comboLijst);
        cbSelecteerGame.getSelectionModel().selectFirst();
    }

    public void laadKaartenInComboBoxen() {
        try {
            gekochteKaarten = dc.geefAangekochteKaarten(geselecteerdeSpeler());
            nietGekochteKaarten = dc.geefNogNietGekochteKaarten(geselecteerdeSpeler());
        } catch (DatabaseException e) {
        }
        
        String[] gekochteKaartenStrings = new String[gekochteKaarten.length];
        for (int i = 0; i < gekochteKaartenStrings.length; i++) {
            String[] kaart = Utilities.veranderNaarMooieLayout(gekochteKaarten[i]);
            gekochteKaartenStrings[i] = kaart[0] + kaart [1];
        }
        ObservableList<String> comboLijstGekocht = FXCollections.observableArrayList(gekochteKaartenStrings);
        cbRemoveCard.setItems(comboLijstGekocht);
        cbRemoveCard.getSelectionModel().selectFirst();
        
        String[] nietGekochteKaartenStrings = new String[nietGekochteKaarten.length];
        for (int i = 0; i < nietGekochteKaartenStrings.length; i++) {
            String[] kaart = Utilities.veranderNaarMooieLayout(nietGekochteKaarten[i]);
            nietGekochteKaartenStrings[i] = kaart[0] + kaart [1];
        }
        ObservableList<String> comboLijstNietGekocht = FXCollections.observableArrayList(nietGekochteKaartenStrings);
        cbGiveCard.setItems(comboLijstNietGekocht);
        cbGiveCard.getSelectionModel().selectFirst();
        

    }

    private void selecteerSpeler() {
        try {
            String[] info = dc.geefSpelerInfo(geselecteerdeSpeler());

            txfSpelerNaam.setText(info[0]);
            txfKrediet.setText(info[1]);
            txfSpelerGeboortedatum.setText(info[2]);
            
            laadKaartenInComboBoxen();
        } catch (DatabaseException e) {
            lblError.setText(r.getString("DATABASEERROR"));
        }catch (Exception e) {

        }
    }

    private String geselecteerdeSpeler() {
        if (cbSpelerSelectie.getSelectionModel().getSelectedItem() == null) {

            txfSpelerNaam.clear();
            txfKrediet.clear();
            txfSpelerGeboortedatum.clear();
            throw new IllegalArgumentException();
        }
        return cbSpelerSelectie.getSelectionModel().getSelectedItem();
    }

    private String geselecteerdeWedstrijd() {
        if (cbSelecteerGame.getSelectionModel().getSelectedIndex() == -1) {

            throw new IllegalArgumentException();
        }
        return wedstrijdList[cbSelecteerGame.getSelectionModel().getSelectedIndex()][0];
    }

    @FXML
    private void btnSavePlayerClicked(ActionEvent event) {
        try {
            dc.veranderSpeler(geselecteerdeSpeler(), txfSpelerNaam.getText(), Integer.parseInt(txfSpelerGeboortedatum.getText()), Integer.parseInt(txfKrediet.getText()));
            selecteerSpeler();
            lblError.setText(null);
        } catch (DatabaseException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                lblError.setText(r.getString("PLAYERALREADYEXISTS"));
                selecteerSpeler();
            } else {
                lblError.setText(r.getString("DATABASEERROR"));
            }
        } catch (NumberFormatException e) {
            lblError.setText("Number too large!");
        } 

        laadSpelersInComboBox();
    }

    @FXML
    private void btnDeletePlayerClicked(ActionEvent event) {
        try {
            dc.verwijderSpeler(geselecteerdeSpeler());
            selecteerSpeler();
            lblError.setText(null);
        } catch (Exception e) {
            lblError.setText(r.getString("DATABASEERROR"));
        }

        laadSpelersInComboBox();
    }

    @FXML
    private void btnDeleteGameClicked(ActionEvent event) {
        try {
            dc.verwijderWedstrijd(geselecteerdeWedstrijd());
            lblError.setText(null);
        } catch (DatabaseException e) {
            lblError.setText(r.getString("DATABASEERROR"));
        } catch(IllegalArgumentException e) {
            lblError.setText("Select a game");
        }

        laadGamesInComboBox();
    }

    @FXML
    private void btnCreateNewAdminClicked(ActionEvent event) {

        if (txfNewAdminPassword.getText() != null && txfNewAdminUsername.getText() != null && !txfNewAdminPassword.getText().trim().isEmpty() && !txfNewAdminUsername.getText().trim().isEmpty()) {
            // Create the custom dialog.
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Pazaak");
            dialog.setHeaderText("Validate yourself using an already existing admin user");

            // Set the icon (must be included in the project).
            ImageView img = new ImageView(this.getClass().getResource("lock.png").toString());
            img.setFitWidth(100);
            img.setFitHeight(100);
            dialog.setGraphic(img);

            // Set the button types.
            ButtonType loginButtonType = new ButtonType("Validate", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            // Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField username = new TextField();
            username.setPromptText("Username");
            PasswordField password = new PasswordField();
            password.setPromptText("Password");

            grid.add(new Label("Username:"), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label("Password:"), 0, 1);
            grid.add(password, 1, 1);

            // Enable/Disable login button depending on whether a username was entered.
            Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.setDisable(true);

            // Do some validation (using the Java 8 lambda syntax).
            username.textProperty().addListener((observable, oldValue, newValue) -> {
                loginButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

            // Request focus on the username field by default.
            Platform.runLater(() -> username.requestFocus());

            // Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(usernamePassword -> {
                Boolean validated = dc.valideerAdmin(usernamePassword.getKey(), usernamePassword.getValue());

                try {
                    dc.maakNieuweAdmin(usernamePassword.getKey(), usernamePassword.getValue(), txfNewAdminUsername.getText(), txfNewAdminPassword.getText());
                    txfNewAdminPassword.clear();
                    txfNewAdminUsername.clear();
                    lblError.setText(null);
                } catch (InvalidAdminCredentialsException e) {
                    lblError.setText("Invalid credentials");
                } catch (DatabaseException e) {
                    if (e.getMessage().contains("Duplicate entry")) {
                        lblError.setText("An admin with that name already exists");
                    } else {
                        lblError.setText(r.getString("DATABASEERROR"));
                    }
                }
            });
        } else
            lblError.setText(r.getString("FILLINALLFIELDS"));

    }

    @FXML
    private void btnRemoveCardClicked(ActionEvent event) {
        try{
            dc.neemStartstapelkaartWeg(geselecteerdeSpeler(), gekochteKaarten[cbRemoveCard.getSelectionModel().getSelectedIndex()]);
            laadKaartenInComboBoxen();
            lblError.setText(null);
        } catch (DatabaseException e) {
            lblError.setText(r.getString("DATABASEERROR"));
        }catch(Exception e) {
            
        }
    }

    @FXML
    private void btnGiveCardClicked(ActionEvent event) {
        
        try{
            dc.voegStartstapelkaartToe(geselecteerdeSpeler(), nietGekochteKaarten[cbGiveCard.getSelectionModel().getSelectedIndex()]);
            laadKaartenInComboBoxen();
            lblError.setText(null);
        }catch (DatabaseException e) {
            lblError.setText(r.getString("DATABASEERROR"));
        }catch(Exception e) {
            
        }
    }
}
