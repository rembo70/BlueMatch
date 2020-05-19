package BlueMatch.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;


public class OverviewRecord {
    private SimpleStringProperty refbroker;
    private SimpleStringProperty functie;
    private SimpleStringProperty statusklant;
    private SimpleStringProperty medewerker;


    public OverviewRecord() {
        this.refbroker = new SimpleStringProperty();
        this.functie = new SimpleStringProperty();
        this.statusklant = new SimpleStringProperty();
        this.medewerker = new SimpleStringProperty();

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

    public String getStatusKlant() {
        return statusklant.get();
    }

    public SimpleStringProperty statusklantProperty() {
        return statusklant;
    }

    public void setStatusKlant(String statusklant) {
        this.statusklant.set(statusklant);
    }

    public String getMedewerker() {
        return medewerker.get();
    }

    public SimpleStringProperty mederwekerProperty() {
        return medewerker;
    }

    public void setMedewerker(String medewerker) {
        this.medewerker.set(medewerker);
    }


}

