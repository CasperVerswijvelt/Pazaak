package domein;

import exceptions.InsufficientBalanceException;
import exceptions.PlayerDoesntExistException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OfflineSpelerEnKaartRepository implements ISpelerEnKaartRepository {


    private List<Kaart> startStapel = Arrays.asList(
            new Kaart(1,'+'),
            new Kaart(2,'+'),
            new Kaart(3,'+'),
            new Kaart(1,'-'),
            new Kaart(2,'-'),
            new Kaart(3,'-'),
            new Kaart(2,'*'));
    private List<Kaart> alleKaarten = Arrays.asList(
            new Kaart(1,'+',5),
            new Kaart(2,'+',5),
            new Kaart(3,'+',5),
            new Kaart(4,'+',5),
            new Kaart(5,'+',5),
            new Kaart(6,'+',5),
            new Kaart(1,'-',5),
            new Kaart(2,'-',5),
            new Kaart(3,'-', 5),
            new Kaart(4,'-',5),
            new Kaart(5,'-',5),
            new Kaart(6,'-', 5),
            new Kaart(1,'*',10),
            new Kaart(2,'*',10),
            new Kaart(3,'*',10),
            new Kaart(4,'*',10),
            new Kaart(5,'*',10),
            new Kaart(6,'*',10),
            new Kaart(2,'T',10),
            new Kaart(1,'D',10),
            new Kaart(1,'W',10),
            new Kaart(2,'W',10),
            new Kaart(1,'C',10));
    private List<Speler> spelers = new ArrayList<>(Arrays.asList(
            new Speler("You",1998,100, new ArrayList<>(startStapel)),
            new Speler("Computer",1998,100, new ArrayList<>(startStapel))));
    @Override
    public void maakNieuweSpelerAan(String naam, int geboorteDatum) {
        spelers.add(new Speler(naam,geboorteDatum,100,new ArrayList<>(startStapel)));
    }

    @Override
    public boolean bestaat(String naam) {
        return spelers.stream().anyMatch(speler -> speler.getNaam().equalsIgnoreCase(naam));
    }

    @Override
    public List<String> geefSpelersLijst() {
        return spelers.stream().map(speler -> speler.getNaam()).collect(Collectors.toList());
    }

    @Override
    public String[] geefSpelerInfo(String naam) {
        if (bestaat(naam)) {
            Speler speler = spelers.stream().filter(s -> s.getNaam().equalsIgnoreCase(naam)).findFirst().get();

            String info[] = new String[3];

            info[0] = speler.getNaam();
            info[1] = speler.getKrediet() + "";
            info[2] = speler.getGeboorteJaar() + "";

            return info;
        } else {
            throw new PlayerDoesntExistException();
        }

    }

    @Override
    public Speler geefSpeler(String naam) {
        return spelers.stream().filter(speler -> speler.getNaam().equalsIgnoreCase(naam)).findFirst().orElse(null);
    }

    @Override
    public List<Kaart> geefStartStapel(String naam) {
        if (bestaat(naam)) {
            return geefSpeler(naam).geefStartStapel();
        } else {
            throw new PlayerDoesntExistException();
        }
    }

    @Override
    public void slaKredietOp(Speler speler) {
        // Not necessary in offline mode
    }

    @Override
    public List<Kaart> geefNogNietGekochteKaarten(String naam) {
        if (bestaat(naam)) {
            List<Kaart> kaarten = new ArrayList<>(alleKaarten);
             geefSpeler(naam).geefStartStapel().forEach(kaart -> kaarten.remove(kaart));
             return kaarten;
        } else {
            throw new PlayerDoesntExistException();
        }
    }

    @Override
    public void koopKaart(String naam, Kaart kaart) {
        if (bestaat(naam)) {
            Speler speler = geefSpeler(naam);
            if(speler.getKrediet() >= kaart.getPrijs()) {
                speler.geefStartStapel().add(kaart);
                speler.setKrediet(speler.getKrediet() - kaart.getPrijs());
            } else {
                throw new InsufficientBalanceException();
            }
        } else {
            throw new PlayerDoesntExistException();
        }
    }

    @Override
    public List<Kaart> geefAangekochteKaarten(String naam) {
        if (bestaat(naam)) {
            return geefSpeler(naam).geefStartStapel();
        } else {
            throw new PlayerDoesntExistException();
        }
    }

    @Override
    public List<Integer> geefPrijzenKaarten() {
        return alleKaarten.stream().map(kaart -> kaart.getPrijs()).collect(Collectors.toList()); // FOUT
    }

    // Admin methods: admin not implemented in offline mode
    @Override
    public void veranderSpeler(String spelerNaam, String nieuweNaam, int nieuweGebDat, int nieuwKrediet) {

    }

    @Override
    public boolean valideerAdmin(String user, String password) {
        return false;
    }

    @Override
    public void verwijderSpeler(String naam) {

    }

    @Override
    public void maakNieuweAdmin(String bestaandeAdminNaam, String bestaandeAdminPass, String nieuweAdminNaam, String nieuweAdminPass) {

    }

    @Override
    public void voegStartstapelkaartToe(String naam, Kaart kaart) {

    }

    @Override
    public void neemStartstapelkaartWeg(String naam, Kaart kaart) {

    }
}
