package api.payloads;

public class ProgressRequest {
    private String questionId, userId, subjectId;
    public ProgressRequest(String questionId, String userId, String subjectId) {
        setQuestionId(questionId);
        setUserId(userId);
        setSubjectId(subjectId);
    }

    public ProgressRequest(String userId, String subjectId) {
        setUserId(userId);
        setSubjectId(subjectId);
    }
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
