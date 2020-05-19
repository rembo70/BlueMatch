package BlueMatch.model;

public class Medewerker {
    private String voornaam;
    private String achternaam;
    private Integer uren;
    private String status;

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

    public Integer getUren() {
        return uren;
    }

    public void setUren(Integer uren) {
        this.uren = uren;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
