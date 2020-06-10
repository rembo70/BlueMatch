package BlueMatch.model;

import java.nio.charset.IllegalCharsetNameException;

public class Broker {
    private int idbroker;
    private String brokernaam;
    private String contactpersoon;
    private String telbroker;
    private String emailbroker;
    private String opmerkingbroker;

    public int getIdbroker() {
        return idbroker;
    }

    public void setIdbroker(int idbroker) {
        this.idbroker = idbroker;
    }

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
