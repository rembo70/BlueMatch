package BlueMatch.model;

public class Broker {
    private String brokernaam;
    private String contactpersoon;
    private String telbroker;
    private String emailbroker;
    private String opmerkingbroker;

    public String getBrokernaam() {
        return brokernaam;
    }

    public void setBrokernaam(String brokernaam) {
        this.brokernaam = brokernaam;
    }

    public String getContactpersoon() {
        return contactpersoon;
    }

    public void setContactpersoon(String contactpersoon) {
        this.contactpersoon = contactpersoon;
    }

    public String getTelbroker() {
        return telbroker;
    }

    public void setTelbroker(String telbroker) {
        this.telbroker = telbroker;
    }

    public String getEmailbroker() {
        return emailbroker;
    }

    public void setEmailbroker(String emailbroker) {
        this.emailbroker = emailbroker;
    }

    public String getOpmerkingbroker() {
        return opmerkingbroker;
    }

    public void setOpmerkingbroker(String opmerkingbroker) {
        this.opmerkingbroker = opmerkingbroker;
    }
}
