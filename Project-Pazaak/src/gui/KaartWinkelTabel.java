/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Casper
 */
public class KaartWinkelTabel extends GridPane {
    //Attributen
    private DomeinController dc;
    private ResourceBundle r;
    private Parent parent;
    
    private List<Label> lblTabelTitels;
    private List<Label> lblTypes;
    private List<ComboBox> cbWaardeSelecties;
    private List<TextArea> txAreaOmschrijvingen;
    private List<Label> lblPrijzen;
    
    
    public KaartWinkelTabel(Parent parent, DomeinController dc, ResourceBundle r) {
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
        lblTypes.add(new Label("x +/- y"));
        
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
        List<Integer> prijzen = dc.geefPrijzenKaarten();
        
        for(int i = 0; i< prijzen.size(); i++) {
            lblPrijzen.add(new Label(prijzen.get(i) + ""));
        }
       
        for(int i = 0; i<lblTabelTitels.size(); i++) {
            this.add(lblTabelTitels.get(i), i, 0);
        }
        
        for(int i = 0; i<lblTypes.size(); i++) {
            this.add(lblTypes.get(i), 0, i+1);
           
            cbWaardeSelecties.add(new ComboBox());
            this.add(cbWaardeSelecties.get(i), 1, i+1);
            
            TextArea omschrijving = txAreaOmschrijvingen.get(i);
            omschrijving.setEditable(true);
            omschrijving.setPrefRowCount(2);
            omschrijving.setWrapText(true);
            this.add(omschrijving, 2, i+1);
            
            this.add(lblPrijzen.get(i), 3, i+1);
            
        }
        
        
        
        

    }
    
    
}
