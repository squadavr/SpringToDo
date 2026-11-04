package ua.squadavr.entity;


public class Record {
    private int id;
    private final String title;
    private RecordStatus status;
    public Record(int id,String title, RecordStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public Record(String title, int id){
        this.id = id;
        this.title = title;
        this.status = RecordStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }
}
