package domein;

import java.util.ArrayList;

public class OfflineWedstrijdRepository implements IWedstrijdRepository {
    @Override
    public void slaWedstrijdOp(Wedstrijd wedstrijd, String naam) {

    }

    @Override
    public Wedstrijd laadWedstrijd(String naam) {
        return new Wedstrijd(new Speler("You",1998,9999, new ArrayList<>()),
                new Speler("Computer",1998,9999, new ArrayList<>()));
    }

    @Override
    public String[][] geefWedstrijdenOverzicht() {
        return new String[][] {
                {"Game","You","0","Computer", "0"}
        };
    }

    @Override
    public void verwijderWedstrijd(String wedstrijd) {

    }
}
