package BlueMatch.model;

import javafx.beans.property.SimpleStringProperty;

public class Aanvraag {
    private int idaanvraag;
    private SimpleStringProperty refbroker;
    private SimpleStringProperty functie;
    private SimpleStringProperty refcontact;
    private SimpleStringProperty vraagurenweek;
    private SimpleStringProperty statusklant;
    private SimpleStringProperty datumaanvraag;
    private SimpleStringProperty locatie;
    private SimpleStringProperty startdatum;
    private SimpleStringProperty opmerking;
    private SimpleStringProperty refklant;
    private SimpleStringProperty linkaanvraag;
    private SimpleStringProperty tariefaanvraag;


    public Aanvraag() {
        this.refbroker = new SimpleStringProperty();
        this.functie = new SimpleStringProperty();
        this.refcontact = new SimpleStringProperty();
        this.vraagurenweek = new SimpleStringProperty();
        this.statusklant = new SimpleStringProperty();
        this.datumaanvraag = new SimpleStringProperty();
        this.locatie = new SimpleStringProperty();
        this.startdatum = new SimpleStringProperty();
        this.opmerking = new SimpleStringProperty();
        this.refklant = new SimpleStringProperty();
        this.linkaanvraag = new SimpleStringProperty();
        this.tariefaanvraag = new SimpleStringProperty();

    }

    public int getIdaanvraag() {
        return idaanvraag;
    }

    public void setIdaanvraag(int idaanvraag) {
        this.idaanvraag = idaanvraag;
    }

    public String getRefbroker() {
        return refbroker.get();
    }

    public void setRefbroker(String refbroker) {
        this.refbroker.set(refbroker);
    }

    public String getFunctie() {
        return functie.get();
    }

    public void setFunctie(String functie) {
        this.functie.set(functie);
    }

    public String getRefcontact() {
        return refcontact.get();
    }

    public void setRefcontact(String refcontact) {
        this.refcontact.set(refcontact);
    }

    public String getVraagurenweek() {
        return vraagurenweek.get();
    }

    public void setVraagurenweek(String vraagurenweek) {
        this.vraagurenweek.set(vraagurenweek);
    }

    public String getStatusklant() {
        return statusklant.get();
    }

    public void setStatusklant(String statusklant) {
        this.statusklant.set(statusklant);
    }

    public String getDatumaanvraag() {
        return datumaanvraag.get();
    }

    public void setDatumaanvraag(String datumaanvraag) {
        this.datumaanvraag.set(datumaanvraag);
    }

    public String getLocatie() {
        return locatie.get();
    }

    public void setLocatie(String locatie) {
        this.locatie.set(locatie);
    }

    public String getStartdatum() {
        return startdatum.get();
    }

    public void setStartdatum(String startdatum) {
        this.startdatum.set(startdatum);
    }

    public String getOpmerking() {
        return opmerking.get();
    }

    public void setOpmerking(String opmerking) {
        this.opmerking.set(opmerking);
    }

    public String getRefklant() {
        return refklant.get();
    }

    public void setRefklant(String refklant) {
        this.refklant.set(refklant);
    }

    public String getLinkaanvraag() {
        return linkaanvraag.get();
    }

    public void setLinkaanvraag(String linkaanvraag) {
        this.linkaanvraag.set(linkaanvraag);
    }

    public String getTariefaanvraag() {
        return tariefaanvraag.get();
    }

    public void setTariefaanvraag(String tariefaanvraag) {
        this.tariefaanvraag.set(tariefaanvraag);
    }


}
