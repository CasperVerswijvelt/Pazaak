package domein;

public interface IWedstrijdRepository {

    void slaWedstrijdOp(Wedstrijd wedstrijd, String naam);

    /**
     * Returns the game that is saved in the database with given name, if it exists
     * @param naam
     * @return
     */
    Wedstrijd laadWedstrijd(String naam);

    /**
     *Returns a overview of all games saved in the database
     * @return
     */
    String[][] geefWedstrijdenOverzicht();

    /**
     * Removes a game from the database with given name, if it exists
     * @param wedstrijd
     */
    void verwijderWedstrijd(String wedstrijd);
}
