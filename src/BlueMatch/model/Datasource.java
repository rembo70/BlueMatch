package BlueMatch.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
    public static final String DATABASENAME = "jdbc:sqlite:C:\\Users\\Remo\\Bluematch.db";
    public static final String COLUMN_IDAANBOD = "idaanbod";
    public static final String TABLE_AANBOD = "Aanbod";
    public static final String COLUMN_REFAANVRAAG = "refaanvraag";
    public static final String COLUMN_REFMEDEWERKER = "refmedewerker";
    public static final String COLUMN_TARIEFAANBOD = "tariefaanbod";
    public static final String COLUMN_URENPERWEEKAANBOD = "urenperweerkaanbod";
    public static final String COLUMN_STATUSAANBOD = "statusaanb";
    public static final int INDEX_IDAANBOD = 1;
    public static final int INDEX_REFAANVRAAG = 2;
    public static final int INDEX_REFMEDEWERKER = 3;
    public static final int INDEX_TARIEFAANBOD = 4;
    public static final int INDEX_URENPERWEEKAANBOD = 5;
    public static final int INDEX_STATUSAANBOD = 6;


    public static final String TABLE_AANVRAAG = "Aanvraag";
    public static final String COLUMN_IDAANVRAAG = "idaanvraag";
    public static final String COLUMN_REFBROKER = "refbroker";
    public static final String COLUMN_FUNCTIE = "functie";
    public static final String COLUMN_REFCONTACT = "refcontact";
    public static final String COLUMN_VRAAGURENWEEK = "vraagurenweek";
    public static final String COLUMN_STATUSKLANT = "statusklant";
    public static final String COLUMN_DATUMAANVRAAG = "datumaanvraag";
    public static final String COLUMN_LOCATIE = "locatie";
    public static final String COLUMN_STARTDATUM = "startdatum";
    public static final String COLUMN_OPMERKING = "opmerking";
    public static final String COLUMN_REFKLANT = "refklant";
    public static final String COLUMN_LINKAANVRAAG = "linkaanvraag";
    public static final String COLUMN_TARIEFAANVRAAG = "tariefaanvraag";


    public static final int INDEX_IDAANVRAAG = 1;
    public static final int INDEX_REFBROKER = 2;
    public static final int INDEX_FUNCTIE = 3;
    public static final int INDEX_REFCONTACT = 4;
    public static final int INDEX_VRAAGURENWEEK = 5;
    public static final int INDEX_STATUSKLANT = 6;
    public static final int INDEX_DATUMAANVRAAG = 7;
    public static final int INDEX_LOCATIE = 8;
    public static final int INDEX_STARTDATUM = 9;
    public static final int INDEX_OPMERKING = 10;
    public static final int INDEX_REFKLANT = 11;
    public static final int INDEX_LINKAANVRAAG = 12;
    public static final int INDEX_TARIEFAANVRAAG = 13;

    public static final String TABLE_MEDEWERKER = "Medewerker";
    public static final String COLUMN_IDMDW = "idmdw";
    public static final String COLUMN_VOORNAAM = "voornaam";
    public static final String COLUMN_ACHTERNAAM = "achternaam";
    public static final String COLUMN_URENPERWEEK = "urenperweek";
    public static final String COLUMN_STATUS = "status";
    public static final int INDEX_IDMDW = 1;
    public static final int INDEX_VOORNAAM = 2;
    public static final int INDEX_ACHTERNAAM = 3;
    public static final int INDEX_URENPERWEEK = 4;
    public static final int INDEX_STATUS = 5;

    public static final String InsertAanvraag = "INSERT INTO " + TABLE_AANVRAAG + '(' + COLUMN_REFBROKER
            + "," + COLUMN_FUNCTIE + "," + COLUMN_REFCONTACT + "," + COLUMN_VRAAGURENWEEK + "," + COLUMN_STATUSKLANT
            + "," + COLUMN_DATUMAANVRAAG + "," + COLUMN_LOCATIE + "," + COLUMN_STARTDATUM + "," + COLUMN_OPMERKING
            + "," + COLUMN_REFKLANT + "," + COLUMN_LINKAANVRAAG + "," + COLUMN_TARIEFAANVRAAG +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    //public static final String InsertAanvraag = "INSERT INTO " + TABLE_AANVRAAG + " VALUES (' ',?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String QUERYSTRINGMAIN = "SELECT aanvraag.refbroker, aanvraag.functie, aanvraag.statusklant, aanbod.refmedewerker from aanvraag LEFT JOIN aanbod ON aanvraag.idaanvraag=Aanbod.refaanvraag";
    //public static final String QUERYSTRINGMAIN = "SELECT aanvraag.refbroker, aanvraag.functie, aanvraag.statusklant, aanbod.refmedewerker from aanvraag JOIN aanbod ON aanvraag.idaanvraag=Aanbod.refaanvraag";
    //public static final String InsertAanvraag = "INSERT INTO " + TABLE_AANVRAAG + " VALUES (' ',?,?,?,?,?,?,?,?,?,?,?,?)";


    private Connection conn;
    private PreparedStatement insertIntoAanvraag;


    private static Datasource instance = new Datasource();

    private Datasource() {

    }

    public static Datasource getInstance() {
        return instance;
        //Datasource.getInstance().methodname()...
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(DATABASENAME);
            insertIntoAanvraag = conn.prepareStatement(InsertAanvraag);
            return true;

        } catch (SQLException e) {
            System.out.println("Could not connect to database" + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
                insertIntoAanvraag.close();
            }

        } catch (SQLException e) {
            System.out.println("Could not close connection" + e.getMessage());
        }
    }


    public int aanvraagToevoegen(Aanvraag aanvraag) throws SQLException {
        System.out.println(aanvraag.getRefklant() + "testte");
        //insertIntoAanvraag = conn.prepareStatement(InsertAanvraag);
        insertIntoAanvraag.setString(1, aanvraag.getRefbroker());
        insertIntoAanvraag.setString(2, aanvraag.getFunctie());
        insertIntoAanvraag.setString(3, aanvraag.getRefcontact());
        insertIntoAanvraag.setString(4, aanvraag.getVraagurenweek());
        insertIntoAanvraag.setString(5, aanvraag.getStatusklant());
        insertIntoAanvraag.setString(6, aanvraag.getDatumaanvraag());
        insertIntoAanvraag.setString(7, aanvraag.getLocatie());
        insertIntoAanvraag.setString(8, aanvraag.getStartdatum());
        insertIntoAanvraag.setString(9, aanvraag.getOpmerking());
        insertIntoAanvraag.setString(10, aanvraag.getRefklant());
        insertIntoAanvraag.setString(11, aanvraag.getLinkaanvraag());
        insertIntoAanvraag.setString(12, aanvraag.getTariefaanvraag());
        insertIntoAanvraag.executeUpdate();
        System.out.println("change committed");
        conn.setAutoCommit(true);
        return 1;
    }

    public List<Aanvraag> queryAanvraag() {

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_AANVRAAG);) {
            List<Aanvraag> aanvragen = new ArrayList<>();
            while (results.next()) {
                Aanvraag aanvraag = new Aanvraag();
                aanvraag.setRefbroker(results.getString(INDEX_REFBROKER));
                aanvraag.setFunctie(results.getString(INDEX_FUNCTIE));
                aanvraag.setRefcontact(results.getString(INDEX_REFCONTACT));
                aanvraag.setVraagurenweek(results.getString(INDEX_VRAAGURENWEEK));
                aanvraag.setStatusklant(results.getString(INDEX_STATUSKLANT));
                aanvraag.setDatumaanvraag(results.getString(INDEX_DATUMAANVRAAG));
                aanvraag.setLocatie(results.getString(INDEX_LOCATIE));
                aanvraag.setStartdatum(results.getString(INDEX_STARTDATUM));
                aanvraag.setOpmerking(results.getString(INDEX_OPMERKING));
                aanvraag.setRefklant(results.getString(INDEX_REFKLANT));
                aanvraag.setLinkaanvraag(results.getString(INDEX_LINKAANVRAAG));
                aanvraag.setTariefaanvraag(results.getString(INDEX_TARIEFAANVRAAG));

                aanvragen.add(aanvraag);
            }
            return aanvragen;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }


    public List<OverviewRecord> queryMain() {

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(QUERYSTRINGMAIN);) {

            List<OverviewRecord> overviewlist = new ArrayList<>();
            while (results.next()) {
                OverviewRecord overviewrecord = new OverviewRecord();
                overviewrecord.setRefbroker(results.getString(1));
                overviewrecord.setFunctie(results.getString(2));
                overviewrecord.setStatusKlant(results.getString(3));
                overviewrecord.setMedewerker(results.getString(4));

//                    aanvraag.setStatusklant(results.getString(INDEX_STATUSKLANT));
//                    aanvraag.setDatumaanvraag(results.getString(INDEX_DATUMAANVRAAG));
//                    aanvraag.setLocatie(results.getString(INDEX_LOCATIE));
//                    aanvraag.setStartdatum(results.getString(INDEX_STARTDATUM));
//                    aanvraag.setOpmerking(results.getString(INDEX_OPMERKING));
//                    aanvraag.setRefklant(results.getString(INDEX_REFKLANT));
//                    aanvraag.setLinkaanvraag(results.getString(INDEX_LINKAANVRAAG));
//                    aanvraag.setTariefaanvraag(results.getString(INDEX_TARIEFAANVRAAG));

                overviewlist.add(overviewrecord);
            }
            return overviewlist;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
}



