package BlueMatch.model;

public class Log {
    private int idlog;
    private int idaanbodlog;
    private String timestamplog;
    private String userid;
    private String oldstatus;
    private String newstatus;

    public int getIdlog() {
        return idlog;
    }

    public void setIdlog(int idlog) {
        this.idlog = idlog;
    }

    public int getIdaanbodlog() {
        return idaanbodlog;
    }

    public void setIdaanbodlog(int idaanbodlog) {
        this.idaanbodlog = idaanbodlog;
    }

    public String getTimestamplog() {
        return timestamplog;
    }

    public void setTimestamplog(String timestamplog) {
        this.timestamplog = timestamplog;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOldstatus() {
        return oldstatus;
    }

    public void setOldstatus(String oldstatus) {
        this.oldstatus = oldstatus;
    }

    public String getNewstatus() {
        return newstatus;
    }

    public void setNewstatus(String newstatus) {
        this.newstatus = newstatus;
    }
}
