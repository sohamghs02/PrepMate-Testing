package api.payloads;

public class QuestionRequest {
    private String subject_id, subtopic_id, question_text, answer_text;
    public QuestionRequest(String subject_id, String subtopic_id, String question_text, String answer_text) {
        setSubject_id(subject_id);
        setSubtopic_id(subtopic_id);
        setQuestion_text(question_text);
        setAnswer_text(answer_text);
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubtopic_id() {
        return subtopic_id;
    }

    public void setSubtopic_id(String subtopic_id) {
        this.subtopic_id = subtopic_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }
}
