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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Casper
 */
public class KaartWinkelScherm extends GridPane {

    private final BorderPaneController parent;

    //Attributen
    private final DomeinController dc;
    private final ResourceBundle r;

    private List<Label> lblTabelTitels;
    private List<Label> lblTypes;
    private List<ComboBox> cbWaardeSelecties;
    private List<TextArea> txAreaOmschrijvingen;
    private List<Label> lblPrijzen;
    private List<Button> btnKoop;

    private Label lblSpelerSelectie;
    private ComboBox cbSpelerSpelectie;
    private Label lblKrediet;
    private Label lblError;
    private Alert DBAlert, cardBoughtAlert, insufficientBalanceException, invalidPlayerException;

    KaartWinkelScherm(BorderPaneController parent, DomeinController dc, ResourceBundle r) {

        this.parent = parent;
        this.dc = dc;
        this.r = r;
        buildGui();
    }

    private void buildGui() {

        //Alerts
        DBAlert = new Alert(Alert.AlertType.ERROR);
        DBAlert.setTitle("Pazaak");
        DBAlert.setContentText(r.getString("DATABASEERROR"));

        cardBoughtAlert = new Alert(Alert.AlertType.NONE);
        cardBoughtAlert.setTitle("Pazaak");
        cardBoughtAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);

        insufficientBalanceException = new Alert(Alert.AlertType.ERROR);
        insufficientBalanceException.setTitle("Pazaak");
        insufficientBalanceException.setContentText(r.getString("INSUFFICIENTBALANCE"));

        invalidPlayerException = new Alert(Alert.AlertType.ERROR);
        invalidPlayerException.setTitle("Pazaak");
        invalidPlayerException.setContentText(r.getString("INVALIDPLAYER"));

        //Spelerselectie
        lblSpelerSelectie = new Label(r.getString("PLAYER"));
        cbSpelerSpelectie = new ComboBox();
        laadSpelersInComboBox(); //Spelers ophalen en in ComboBox plaatsen
        cbSpelerSpelectie.valueProperty().addListener(new ChangeListener<String>() { //Speler wordt geslecteerd wanneer een selectie word aangeduid in combobox
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                selecteerSpeler();
            }
        });

        //Labels
        lblKrediet = new Label();
        lblError = new Label();
        lblError.setTextFill(Color.RED);

        // spelerselectie, krediet, cancel button, en error label toevoegen aan grid
        add(lblSpelerSelectie, 0, 0);
        add(cbSpelerSpelectie, 1, 0);
        add(lblKrediet, 2, 0);

        add(lblError, 2, 9);

        ////Tabel////
        //Tabeltitels labels
        lblTabelTitels = new ArrayList<>();

        lblTabelTitels.add(new Label(r.getString("TYPE")));
        lblTabelTitels.add(new Label(r.getString("VALUE")));
        lblTabelTitels.add(new Label(r.getString("DESCRIPTION")));
        lblTabelTitels.add(new Label(r.getString("PRICE")));

        //Type kaarten labels
        lblTypes = new ArrayList<>();

        lblTypes.add(new Label("+"));
        lblTypes.add(new Label("-"));
        lblTypes.add(new Label("+/-"));
        lblTypes.add(new Label("T"));
        lblTypes.add(new Label("D"));
        lblTypes.add(new Label("x&y"));
        lblTypes.add(new Label("1+/-2"));

        //Waardeselectie combobox
        cbWaardeSelecties = new ArrayList<>();

        //Omschrijvingen textareas
        txAreaOmschrijvingen = new ArrayList<>();

        txAreaOmschrijvingen.add(new TextArea(r.getString("+DESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("-DESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("*DESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("TDESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("DDESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("WDESCRIPTION")));
        txAreaOmschrijvingen.add(new TextArea(r.getString("CDESCRIPTION")));

        //prijzen labels
        lblPrijzen = new ArrayList<>();

        //Koopbuttons
        btnKoop = new ArrayList<>();

        //Lijst prijzen kaarttypes ophalen
        List<Integer> prijzen;
        try {
            prijzen = dc.geefPrijzenKaarten();
        } catch (DatabaseException e) {
            DBAlert.show();
            return;
        }

        //Tabeltitels aan grid toevoegen
        for (int i = 0; i < lblTabelTitels.size(); i++) {
            add(lblTabelTitels.get(i), i, 1);
        }

        //KaartTypeLabels, comboboxes, omschrijvingen, prijzenlabels, koopbuttons aan grid toevoegen
        for (int i = 0; i < lblTypes.size(); i++) {

            //Alle kaarttypelabels toevoegen aan grid
            add(lblTypes.get(i), 0, i + 2);

            //ComboBoxen initializeren, standaard disablen, en aan lijst toevoegen
            ComboBox cb = new ComboBox();
            cb.setVisible(i != 3 && i != 4 && i != 6);
            cb.setDisable(true);
            cbWaardeSelecties.add(cb);
            //Alle comboboxen toevoegen aan grid
            add(cbWaardeSelecties.get(i), 1, i + 2);

            //Elke omschrijving textarea uneditable maken
            TextArea omschrijving = txAreaOmschrijvingen.get(i);
            omschrijving.setEditable(false);
            omschrijving.setPrefRowCount(1);
            omschrijving.setWrapText(true);
            //Alle omschrijvingen toevoegen aan grid
            add(omschrijving, 2, i + 2);

            //Alle lblprijzen de juiste waarde geven
            lblPrijzen.add(new Label(prijzen.get(i) + ""));
            //Alle lblprijzen toevoegen aan grid
            add(lblPrijzen.get(i), 3, i + 2);

            //Alle koopbuttons de juiste vertaalde tekst geven
            Button button = new Button(r.getString("BUY"));
            button.getStyleClass().add("button-TaalSelectie");
            btnKoop.add(button);
            //Alle koopbuttons toevoegen aan grid
            add(btnKoop.get(i), 4, i + 2);

            //Grid spacing aanpassen
            setVgap(20);
            setHgap(20);

        }
        for (Button btn : btnKoop) {
            btn.setDisable(true);
            btn.setOnAction((ActionEvent event) -> {
                drukKoop(btnKoop.indexOf(btn));
            });
        }

    }

    void selecteerSpeler() {

        Object geselecteerd = cbSpelerSpelectie.getSelectionModel().getSelectedItem();
        if (geselecteerd != null) {

            String[] info;
            try {
                info = dc.geefSpelerInfo(geselecteerd.toString());
            } catch (PlayerDoesntExistException e) {
                invalidPlayerException.show();
                return;
            }

            lblKrediet.setText(r.getString("CREDITS") + " :" + info[1]);

            String[][] aankoopBareKaarten = dc.geefNogNietGekochteKaarten(geselecteerd.toString());

            if (aankoopBareKaarten.length != 0) {
                lblError.setText(null);
                this.setDisable(false);
            }else{
                lblError.setText(r.getString("NOLIFE"));
            }

            for (ComboBox cb : cbWaardeSelecties) {
                cb.getItems().clear();
            }

            for (String[] kaart : aankoopBareKaarten) {
                String[] kaartLayout = Utilities.veranderNaarMooieLayout(kaart);
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
                cbWaardeSelecties.get(index).getItems().add(kaartLayout[1]);
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
                    cb.getSelectionModel().select(0);
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
        parent.naarMenu();

    }

    private void drukKoop(int index) {

        try {
            if (cbSpelerSpelectie.getSelectionModel().getSelectedIndex() == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(r.getString("SELECTAPLAYER"));
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
                    kaart = new String[]{"T", geefGeselecteerdValue(index), lblPrijzen.get(index).getText()};
                    break;
                case 4:
                    kaart = new String[]{"D", geefGeselecteerdValue(index), lblPrijzen.get(index).getText()};
                    break;
                case 5:
                    kaart = new String[]{"W", geefGeselecteerdValue(index), lblPrijzen.get(index).getText()};
                    break;
                case 6:
                    kaart = new String[]{"C", geefGeselecteerdValue(index), lblPrijzen.get(index).getText()};
                    break;

            }
            String[] kaartGewoneLayout = veranderNaarGewoonKaartFormaat(kaart);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pazaak");
            alert.setContentText(r.getString("ZEKER"));
            alert.setHeaderText(null);
            alert.setTitle(r.getString("BUYCARDOPTION"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                dc.koopKaart(spelerNaam, kaartGewoneLayout);
                String[] kaartMooieLayout = Utilities.veranderNaarMooieLayout(kaartGewoneLayout);
                cardBoughtAlert.setContentText(String.format(r.getString("CARDBOUGHT"), kaartMooieLayout[0] + kaartMooieLayout[1], kaartMooieLayout[2]));
                cardBoughtAlert.show();
                selecteerSpeler();
            }

        } catch (DatabaseException e) {
            DBAlert.show();
        }catch (InsufficientBalanceException e) {
            insufficientBalanceException.show();
        } catch (PlayerDoesntExistException e) {
            laadSpelersInComboBox();
            invalidPlayerException.show();
        } catch (CardAlreadyBoughtException e) {
            lblError.setText(r.getString("CARDALREADYBOUGHT"));
            selecteerSpeler();
        } catch (IllegalArgumentException e) {
            lblError.setText(r.getString("ENTERAVALUE"));
        }

    }

    private String geefGeselecteerdValue(int index) {
        if (cbWaardeSelecties.get(index).getSelectionModel().getSelectedIndex() == -1) {
            throw new IllegalArgumentException();
        }
        return cbWaardeSelecties.get(index).getSelectionModel().getSelectedItem().toString();
    }

    private void laadSpelersInComboBox() {
        cbSpelerSpelectie.getItems().clear();
        cbSpelerSpelectie.setValue("---");
        ObservableList<String> lijst;
        try {
            lijst = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
        } catch (DatabaseException e) {
            DBAlert.show();
            throw new DatabaseException(e);
        }

        cbSpelerSpelectie.setMaxWidth(100);

        cbSpelerSpelectie.setItems(lijst);

    }

    void selecteerSpeler(String speler) {
        cbSpelerSpelectie.setValue(speler);
        selecteerSpeler();
    }
    
    public String[] veranderNaarGewoonKaartFormaat(String[] kaart) {
        String[] returnKaart = new String[3];
        System.arraycopy(kaart, 0, returnKaart, 0, 3);
        if (returnKaart[1].contains("&")) {
            returnKaart[0] = "W";
            returnKaart[1] = "2&4".equals(kaart[1])?"1":"2";
        } else if (kaart[0].equals("1+/-2") || kaart[0].equals("C")) {
            returnKaart[0] = "C";
            returnKaart[1] = "1";
        } else if (kaart[0].equals("T")) {
            returnKaart[1] = "1";
        } else if (kaart[0].equals("D")) {
            returnKaart[1] = "0";
        } else if (kaart[0].equals("+/-")) {
            returnKaart[0] = "*";
        }
        return returnKaart;
    }
}
