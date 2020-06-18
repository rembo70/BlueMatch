package BlueMatch.model;

public class Medewerker {
    private int IDmdw;
    private String voornaammdw;
    private String achternaammdw;
    private String urenperweekmdw;
    private String statusmdw;
    private String emailmdw;
    private String opmerkingmdw;

    public int getIDmdw() {
        return IDmdw;
    }

    public void setIDmdw(int IDmdw) {
        this.IDmdw = IDmdw;
    }

    public String getVoornaam() {
        return voornaammdw;
    }

    public void setVoornaam(String voornaam) {
        this.voornaammdw = voornaam;
    }

    public String getAchternaam() {
        return achternaammdw;
    }

    public void setAchternaam(String achternaam) {
        this.achternaammdw = achternaam;
    }

    public String getUrenperweek() {
        return urenperweekmdw;
    }

    public void setUren(String urenperweek) {
        this.urenperweekmdw = urenperweek;
    }

    public String getStatusmdw() {
        return statusmdw;
    }

    public void setStatusmdw(String statusmdw) {
        this.statusmdw = statusmdw;
    }

    public String getEmailmedewerker() {
        return emailmdw;
    }

    public void setEmailmedewerker(String emailmedewerker) {
        this.emailmdw = emailmedewerker;
    }

    public String getOpmerkingmedewerker() {
        return opmerkingmdw;
    }

    public void setOpmerkingmedewerker(String opmerkingmedewerker) {
        this.opmerkingmdw = opmerkingmedewerker;
    }
}
