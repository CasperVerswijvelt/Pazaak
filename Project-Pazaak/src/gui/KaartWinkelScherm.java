/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Casper
 */
public class KaartWinkelScherm extends GridPane {

    private Hoofdmenu parent;

    //Attributen
    private DomeinController dc;
    private ResourceBundle r;

    private List<Label> lblTabelTitels;
    private List<Label> lblTypes;
    private List<ComboBox> cbWaardeSelecties;
    private List<TextArea> txAreaOmschrijvingen;
    private List<Label> lblPrijzen;
    private List<Button> btnKoop;

    private Label lblSpelerSelectie;
    private ComboBox cbSpelerSpelectie;
    private Label lblKrediet;
    private Button btnCancel;
    private Label lblError;

    KaartWinkelScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {

        this.parent = parent;
        this.dc = dc;
        this.r = r;
        buildGui();
    }

    private void buildGui() {

        this.setPadding(new Insets(20, 20, 20, 20));

        //Spelerselectie
        lblSpelerSelectie = new Label(r.getString("PLAYER"));
        ObservableList<String> lijst = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
        cbSpelerSpelectie = new ComboBox(lijst);
        cbSpelerSpelectie.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                selecteerSpeler();
            }
        });
        lblKrediet = new Label();
        btnCancel = new Button("Cancel");
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukCancel(event);
            }
        });
        lblError = new Label();
        lblError.setTextFill(Color.RED);

        lblSpelerSelectie.setAlignment(Pos.CENTER_RIGHT);
        this.add(lblSpelerSelectie, 0, 0);
        this.add(cbSpelerSpelectie, 1, 0);
        this.add(lblKrediet, 2, 0);
        
        this.add(btnCancel, 3, 0,2,1);
        this.add(lblError, 2,9);

        //Tabel
        lblTabelTitels = new ArrayList<>();

        lblTabelTitels.add(new Label(r.getString("TYPE")));
        lblTabelTitels.add(new Label(r.getString("VALUE")));
        lblTabelTitels.add(new Label(r.getString("DESCRIPTION")));
        lblTabelTitels.add(new Label(r.getString("PRICE")));

        lblTypes = new ArrayList<>();

        lblTypes.add(new Label("+"));
        lblTypes.add(new Label("-"));
        lblTypes.add(new Label("+/-"));
        lblTypes.add(new Label("T"));
        lblTypes.add(new Label("D"));
        lblTypes.add(new Label("x&y"));
        lblTypes.add(new Label("x+/-y"));

        cbWaardeSelecties = new ArrayList<>();

        txAreaOmschrijvingen = new ArrayList<>();

        txAreaOmschrijvingen.add(new TextArea(r.getString("+DESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("-DESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("*DESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("TDESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("DDESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("WDESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("CDESCRIPTION")));

        lblPrijzen = new ArrayList<>();
        btnKoop = new ArrayList<>();

        List<Integer> prijzen = dc.geefPrijzenKaarten();

        for (int i = 0; i < prijzen.size(); i++) {
            lblPrijzen.add(new Label(prijzen.get(i) + ""));
        }

        for (int i = 0; i < lblTabelTitels.size(); i++) {
            this.add(lblTabelTitels.get(i), i, 1);
        }

        for (int i = 0; i < lblTypes.size(); i++) {
            this.add(lblTypes.get(i), 0, i + 2);

            ComboBox cb = new ComboBox();
            if (i == 3 || i == 4 || i == 6) {
                cb.setVisible(false);
            }
            cb.setDisable(true);
            cbWaardeSelecties.add(cb);
            this.add(cbWaardeSelecties.get(i), 1, i + 2);

            TextArea omschrijving = txAreaOmschrijvingen.get(i);
            omschrijving.setEditable(false);
            omschrijving.setPrefRowCount(1);
            omschrijving.setWrapText(true);
            this.add(omschrijving, 2, i + 2);

            this.add(lblPrijzen.get(i), 3, i + 2);

            btnKoop.add(new Button(r.getString("BUY")));
            this.add(btnKoop.get(i), 4, i + 2);

            this.setVgap(20);
            this.setHgap(20);

        }
        for (Button btn : btnKoop) {
            btn.setDisable(true);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    drukKoop(btnKoop.indexOf(btn));
                }

            });
        }

    }

    void selecteerSpeler() {

        Object geselecteerd = cbSpelerSpelectie.getSelectionModel().getSelectedItem();
        if (geselecteerd == null) {

        } else {
            
            String[] info;
            try{
                info = dc.geefSpelerInfo(geselecteerd.toString());
            }catch (PlayerDoesntExistException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Player does not exist in database anymore (vertaal mij)");
                return;
            }

            lblKrediet.setText(r.getString("CREDITS") + " :" + info[1]);

            String[][] aankoopBareKaarten = dc.geefNogNietGekochteKaarten(geselecteerd.toString());

            if (aankoopBareKaarten.length != 0) {
                this.setDisable(false);
            }

            for (ComboBox cb : cbWaardeSelecties) {
                cb.getItems().clear();
            }

            for (String[] kaart : aankoopBareKaarten) {
                char type = kaart[0].charAt(0);
                int index = -1;
                switch (type) {
                    case '+':
                        index = 0;
                        break;
                    case '-':
                        index = 1;
                        break;
                    case '*':
                        index = 2;
                        break;
                    case 'T':
                        index = 3;
                        break;
                    case 'D':
                        index = 4;
                        break;
                    case 'W':
                        index = 5;
                        break;
                    case 'C':
                        index = 6;
                        break;
                }
                cbWaardeSelecties.get(index).getItems().add(kaart[1]);
            }

            for (ComboBox cb : cbWaardeSelecties) {
                int index = cbWaardeSelecties.indexOf(cb);
                if (cb.getItems().isEmpty()) {

                    lblTypes.get(index).setDisable(true);
                    btnKoop.get(index).setDisable(true);
                    cb.setDisable(true);
                    txAreaOmschrijvingen.get(index).setDisable(true);
                    lblPrijzen.get(index).setDisable(true);
                } else {
                    lblTypes.get(index).setDisable(false);
                    btnKoop.get(index).setDisable(Integer.parseInt(lblPrijzen.get(index).getText()) > Integer.parseInt(info[1]));
                    cb.setVisible(index != 3 && index != 4 && index != 6);
                    cb.setDisable(false);
                    txAreaOmschrijvingen.get(index).setDisable(false);
                    lblPrijzen.get(index).setDisable(false);
                }
            }
        }

    }

    private void drukCancel(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);

    }

    private void drukKoop(int index) {

        try {
            if (cbSpelerSpelectie.getSelectionModel().getSelectedIndex() == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Select a player (vertaal mij)");
                alert.show();
                return;
            }
            String spelerNaam = cbSpelerSpelectie.getSelectionModel().getSelectedItem().toString();
            String[] kaart = new String[3];
            switch (index) {
                case 0:
                    kaart = new String[]{"+", geefGeselecteerdValue(index), lblPrijzen.get(index).getText()};
                    break;
                case 1:
                    kaart = new String[]{"-", geefGeselecteerdValue(index), lblPrijzen.get(index).getText()};
                    break;
                case 2:
                    kaart = new String[]{"*", geefGeselecteerdValue(index), lblPrijzen.get(index).getText()};
                    break;
                case 3:
                    kaart = new String[]{"T", "1", lblPrijzen.get(index).getText()};
                    break;
                case 4:
                    kaart = new String[]{"D", "0", lblPrijzen.get(index).getText()};
                    break;
                case 5:
                    kaart = new String[]{"W", geefGeselecteerdValue(index), lblPrijzen.get(index).getText()};
                    break;
                case 6:
                    kaart = new String[]{"C", "1", lblPrijzen.get(index).getText()};
                    break;

            }
            dc.koopKaart(spelerNaam, kaart);
            selecteerSpeler();
            
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setContentText(String.format(r.getString("CARDBOUGHT"), kaart[0]+kaart[1], kaart[2]));
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.showAndWait();
            
            
        } catch (InsufficientBalanceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(r.getString("INSUFFICIENTBALANCE"));
            alert.show();
        } catch (PlayerDoesntExistException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(r.getString("INVALIDPLAYER"));
            alert.show();
        } catch (CardAlreadyBoughtException e) {
            lblError.setText("Card already bought (vertaal mij)");
        }
        catch (IllegalArgumentException e) {
            lblError.setText("Enter a value (nog niet vertaald)");
        }

    }

    private String geefGeselecteerdValue(int index) {
        if (cbWaardeSelecties.get(index).getSelectionModel().getSelectedIndex() == -1) {

            throw new IllegalArgumentException();
        }
        return cbWaardeSelecties.get(index).getSelectionModel().getSelectedItem().toString();
    }
}
