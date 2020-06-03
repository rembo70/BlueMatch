package BlueMatch.model;

public class Medewerker {
    private String voornaam;
    private String achternaam;
    private String urenperweek;
    private String statusmdw;

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getUrenperweek() {
        return urenperweek;
    }

    public void setUren(String urenperweek) {
        this.urenperweek = urenperweek;
    }

    public String getStatusmdw() {
        return statusmdw;
    }

    public void setStatusmdw(String statusmdw) {
        this.statusmdw = statusmdw;
    }
}
