package domein;

import java.util.*;

public class SpelerRespository {

    //Attributen
    private Collection<Speler> spelers;

    //Constructor
    public SpelerRespository() {
        this.spelers = new ArrayList<>();
    }
    /**
     *
     * @param speler
     */
    public void voegToe(Speler speler) {
        if(!bestaat(speler.getNaam()))
            spelers.add(speler);
        else
            throw new IllegalArgumentException("Naam is al in gebruik!");
    }

    /**
	 * 
	 * @param naam
	 */
    private boolean bestaat(String naam) {
        if (spelers == null) {
            return false;
        } else {
            for (Speler speler : spelers) {
                return speler.getNaam().toLowerCase().equals(naam.toLowerCase());
            }
        }
        return false;
    }
}
