package BlueMatch.model;

public class Aanbod {
    private String refaanvraag;
    private String refmedewerker;
    private String tariefaanbod;
    private int urenperweekaanbod;
    private String status;

    public String getRefaanvraag() {
        return refaanvraag;
    }

    public void setRefaanvraag(String refaanvraag) {
        this.refaanvraag = refaanvraag;
    }

    public String getRefmedewerker() {
        return refmedewerker;
    }

    public void setRefmedewerker(String refmedewerker) {
        this.refmedewerker = refmedewerker;
    }

    public String getTariefaanbod() {
        return tariefaanbod;
    }

    public void setTariefaanbod(String tariefaanbod) {
        this.tariefaanbod = tariefaanbod;
    }

    public int getUrenperweekaanbod() {
        return urenperweekaanbod;
    }

    public void setUrenperweekaanbod(int urenperweekaanbod) {
        this.urenperweekaanbod = urenperweekaanbod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
