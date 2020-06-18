package BlueMatch.model;

public class Klant {
    private int idklant;
    private String klantnaam;
    private String klantcontactpersoon;
    private String klantcontacttelnr;
    private String klantcontactemail;
    private String klantopmerking;

    public int getKlantID() {
        return idklant;
    }

    public void setIdklant(int idklant) {
        this.idklant = idklant;
    }

    public String getKlantnaam() {
        return klantnaam;
    }

    public void setKlantnaam(String klantnaam) {
        this.klantnaam = klantnaam;
    }

    public String getKlantcontactpersoon() {
        return klantcontactpersoon;
    }

    public void setKlantcontactpersoon(String klantcontactpersoon) {
        this.klantcontactpersoon = klantcontactpersoon;
    }

    public String getKlantcontacttelnr() {
        return klantcontacttelnr;
    }

    public void setKlantcontacttelnr(String klantcontacttelnr) {
        this.klantcontacttelnr = klantcontacttelnr;
    }

    public String getKlantcontactemail() {
        return klantcontactemail;
    }

    public void setKlantcontactemail(String klantcontactemail) {
        this.klantcontactemail = klantcontactemail;
    }

    public String getKlantopmerking() {
        return klantopmerking;
    }

    public void setKlantopmerking(String klantopmerking) {
        this.klantopmerking = klantopmerking;
    }

}
