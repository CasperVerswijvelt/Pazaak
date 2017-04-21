/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Casper
 */
public class KaartWinkelTabelPaneel extends GridPane {

    //Attributen
    private DomeinController dc;
    private ResourceBundle r;
    private KaartWinkelScherm parent;

    private List<Label> lblTabelTitels;
    private List<Label> lblTypes;
    private List<ComboBox> cbWaardeSelecties;
    private List<TextArea> txAreaOmschrijvingen;
    private List<Label> lblPrijzen;
    private List<Button> btnKoop;

    public KaartWinkelTabelPaneel(KaartWinkelScherm parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        buildGUI();

    }

    private void buildGUI() {
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
            this.add(lblTabelTitels.get(i), i, 0);
        }

        for (int i = 0; i < lblTypes.size(); i++) {
            this.add(lblTypes.get(i), 0, i + 1);

            ComboBox cb = new ComboBox();
            if (i == 3 || i == 4 || i == 6) {
                cb.setVisible(false);
            }
            cb.setPrefWidth(62);
            cbWaardeSelecties.add(cb);
            this.add(cbWaardeSelecties.get(i), 1, i + 1);

            TextArea omschrijving = txAreaOmschrijvingen.get(i);
            omschrijving.setEditable(false);
            omschrijving.setPrefRowCount(1);
            omschrijving.setWrapText(true);
            this.add(omschrijving, 2, i + 1);

            this.add(lblPrijzen.get(i), 3, i + 1);

            btnKoop.add(new Button(r.getString("BUY")));
            this.add(btnKoop.get(i), 4, i + 1);

            this.setVgap(20);
            this.setHgap(20);

        }

    }

    void selecteerSpeler(String geselecteerd) {
        String[][] aankoopBareKaarten = dc.geefNogNietGekochteKaarten(geselecteerd);

        if (aankoopBareKaarten.length != 0) {
            this.setDisable(false);
        }

        for (ComboBox cb : cbWaardeSelecties) {
            cb.getItems().clear();
        }

        for (String[] kaart : aankoopBareKaarten) {
            char type = kaart[0].charAt(0);

            switch (type) {
                case '+':
                    cbWaardeSelecties.get(0).getItems().add(kaart[1]);
                    break;
                case '-':
                    cbWaardeSelecties.get(1).getItems().add(kaart[1]);
                    break;
                case '*':
                    cbWaardeSelecties.get(2).getItems().add(kaart[1]);
                    break;
                case 'T':
                    cbWaardeSelecties.get(3).getItems().add(kaart[1]);
                    break;
                case 'D':
                    cbWaardeSelecties.get(4).getItems().add(kaart[1]);
                    break;
                case 'W':
                    cbWaardeSelecties.get(5).getItems().add(kaart[1]);
                    break;
                case 'C':
                    cbWaardeSelecties.get(6).getItems().add(kaart[1]);
                    break;
            }
        }

        for (ComboBox cb : cbWaardeSelecties) {
            int index = cbWaardeSelecties.indexOf(cb);
            if (cb.getItems().isEmpty()) {

                lblTypes.get(index).setDisable(true);
                btnKoop.get(index).setDisable(true);
                cb.setVisible(false);
                txAreaOmschrijvingen.get(index).setDisable(true);
                lblPrijzen.get(index).setDisable(true);
            } else {
                lblTypes.get(index).setDisable(false);
                btnKoop.get(index).setDisable(Integer.parseInt(lblPrijzen.get(index).getText()) > Integer.parseInt(dc.geefSpelerInfo(geselecteerd)[1]));
                cb.setVisible(index != 3 && index != 4 && index != 6);
                txAreaOmschrijvingen.get(index).setDisable(false);
                lblPrijzen.get(index).setDisable(false);
            }
        }
    }

}
