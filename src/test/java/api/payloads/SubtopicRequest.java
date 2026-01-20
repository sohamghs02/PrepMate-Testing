package api.payloads;

public class SubtopicRequest {
    private String subject_id;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private String name;
    public SubtopicRequest(String subject_id, String name){
        setSubject_id(subject_id);
        setName(name);
    }
    public String getSubject_id() {
        return subject_id;
    }
    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}
