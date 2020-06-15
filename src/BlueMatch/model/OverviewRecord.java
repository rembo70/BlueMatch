package BlueMatch.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class OverviewRecord {
    private SimpleStringProperty refbroker;
    private SimpleStringProperty functie;
    private SimpleStringProperty refcontact;
    private SimpleStringProperty vraagurenweek;
    private SimpleStringProperty locatie;
    private SimpleStringProperty startdatum;
    private SimpleStringProperty datumaanvraag;
    private SimpleStringProperty opmerking;
    private SimpleStringProperty refklant;
    private SimpleStringProperty linkaanvraag;
    private SimpleStringProperty tariefaanvraag;
    private SimpleStringProperty statusklant;
    private SimpleStringProperty medewerker;
    private SimpleIntegerProperty idaanvraag;
    private SimpleStringProperty statusaanbod;
    private SimpleStringProperty opmerkingaanbod;
    private SimpleStringProperty tariefaanbod;
    private SimpleStringProperty urenperweekaanbod;
    private SimpleIntegerProperty idaanbod;


    public OverviewRecord() {
        this.refbroker = new SimpleStringProperty();
        this.functie = new SimpleStringProperty();
        this.refcontact = new SimpleStringProperty();
        this.statusklant = new SimpleStringProperty();
        this.medewerker = new SimpleStringProperty();
        this.idaanvraag = new SimpleIntegerProperty();
        this.statusaanbod = new SimpleStringProperty();
        this.opmerkingaanbod = new SimpleStringProperty();
        this.vraagurenweek = new SimpleStringProperty();
        this.locatie = new SimpleStringProperty();
        this.startdatum = new SimpleStringProperty();
        this.datumaanvraag = new SimpleStringProperty();
        this.opmerking = new SimpleStringProperty();
        this.refklant = new SimpleStringProperty();
        this.linkaanvraag = new SimpleStringProperty();
        this.tariefaanvraag = new SimpleStringProperty();
        this.tariefaanbod = new SimpleStringProperty();
        this.urenperweekaanbod = new SimpleStringProperty();
        this.idaanbod = new SimpleIntegerProperty();

    }

    public int getIdaanbod() {
        return idaanbod.get();
    }

    public SimpleIntegerProperty idaanbodProperty() {
        return idaanbod;
    }

    public void setIdaanbod(int idaanbod) {
        this.idaanbod.set(idaanbod);
    }

    public String getUrenperweekaanbod() {
        return urenperweekaanbod.get();
    }

    public SimpleStringProperty urenperweekaanbodProperty() {
        return urenperweekaanbod;
    }

    public void setUrenperweekaanbod(String urenperweekaanbod) {
        this.urenperweekaanbod.set(urenperweekaanbod);
    }

    public String getRefbroker() {
        return refbroker.get();
    }

    public SimpleStringProperty refbrokerProperty() {
        return refbroker;
    }

    public void setRefbroker(String refbroker) {
        this.refbroker.set(refbroker);
    }

    public String getFunctie() {
        return functie.get();
    }

    public SimpleStringProperty functieProperty() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie.set(functie);
    }

    public String getRefcontact() {
        return refcontact.get();
    }

    public SimpleStringProperty refcontactProperty() {
        return refcontact;
    }

    public void setRefcontact(String refcontact) {
        this.refcontact.set(refcontact);
    }

    public String getVraagurenweek() {
        return vraagurenweek.get();
    }

    public SimpleStringProperty vraagurenweekProperty() {
        return vraagurenweek;
    }

    public void setVraagurenweek(String vraagurenweek) {
        this.vraagurenweek.set(vraagurenweek);
    }

    public String getLocatie() {
        return locatie.get();
    }

    public SimpleStringProperty locatieProperty() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie.set(locatie);
    }

    public String getStartdatum() {
        return startdatum.get();
    }

    public SimpleStringProperty startdatumProperty() {
        return startdatum;
    }

    public void setStartdatum(String startdatum) {
        this.startdatum.set(startdatum);
    }

    public String getDatumaanvraag() {
        return datumaanvraag.get();
    }

    public SimpleStringProperty datumaanvraagProperty() {
        return datumaanvraag;
    }

    public void setDatumaanvraag(String datumaanvraag) {
        this.datumaanvraag.set(datumaanvraag);
    }

    public String getOpmerking() {
        return opmerking.get();
    }

    public SimpleStringProperty opmerkingProperty() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking.set(opmerking);
    }

    public String getRefklant() {
        return refklant.get();
    }

    public SimpleStringProperty refklantProperty() {
        return refklant;
    }

    public void setRefklant(String refklant) {
        this.refklant.set(refklant);
    }

    public String getLinkaanvraag() {
        return linkaanvraag.get();
    }

    public SimpleStringProperty linkaanvraagProperty() {
        return linkaanvraag;
    }

    public void setLinkaanvraag(String linkaanvraag) {
        this.linkaanvraag.set(linkaanvraag);
    }

    public String getTariefaanvraag() {
        return tariefaanvraag.get();
    }

    public SimpleStringProperty tariefaanvraagProperty() {
        return tariefaanvraag;
    }

    public void setTariefaanvraag(String tariefaanvraag) {
        this.tariefaanvraag.set(tariefaanvraag);
    }

    public String getStatusklant() {
        return statusklant.get();
    }

    public SimpleStringProperty statusklantProperty() {
        return statusklant;
    }

    public void setStatusklant(String statusklant) {
        this.statusklant.set(statusklant);
    }

    public String getMedewerker() {
        return medewerker.get();
    }

    public SimpleStringProperty medewerkerProperty() {
        return medewerker;
    }

    public void setMedewerker(String medewerker) {
        this.medewerker.set(medewerker);
    }

    public int getIdaanvraag() {
        return idaanvraag.get();
    }

    public SimpleIntegerProperty idaanvraagProperty() {
        return idaanvraag;
    }

    public void setIdaanvraag(int idaanvraag) {
        this.idaanvraag.set(idaanvraag);
    }

    public String getStatusaanbod() {
        return statusaanbod.get();
    }

    public SimpleStringProperty statusaanbodProperty() {
        return statusaanbod;
    }

    public void setStatusaanbod(String statusaanbod) {
        this.statusaanbod.set(statusaanbod);
    }

//    public String getStatusaanvraag() {
//        return statusaanvraag.get();
//    }
//
//    public SimpleStringProperty statusaanvraagProperty() {
//        return statusaanvraag;
//    }
//
//    public void setStatusaanvraag(String statusaanvraag) {
//        this.statusaanvraag.set(statusaanvraag);
//    }

    public String getOpmerkingaanbod() {
        return opmerkingaanbod.get();
    }

    public SimpleStringProperty opmerkingaanbodProperty() {
        return opmerkingaanbod;
    }

    public void setOpmerkingaanbod(String opmerkingaanbod) {
        this.opmerkingaanbod.set(opmerkingaanbod);
    }

    public String getTariefaanbod() {
        return tariefaanbod.get();
    }

    public SimpleStringProperty tariefaanbodProperty() {
        return tariefaanbod;
    }

    public void setTariefaanbod(String tariefaanbod) {
        this.tariefaanbod.set(tariefaanbod);
    }
}

