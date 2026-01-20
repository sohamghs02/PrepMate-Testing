package api.payloads;

public class SubjectRequest {
    private String name,description,icon;
    public SubjectRequest(String name, String description, String icon){
        this.name = name;
        this.description = description;
        this.icon = icon;
    }
}
