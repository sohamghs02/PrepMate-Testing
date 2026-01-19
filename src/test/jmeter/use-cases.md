# Use Case Model List (UCML)

---

## UC-01: Successful Login

Actor: Admin / User
Frequency: 100%

### Preconditions
- Actor account exists
- Actor provides valid email and password

### Main Flow
- Actor submits valid credentials
- System authenticates Actor
- System returns authentication token

---

## UC-02: Interview Experiences

**Actor:** User / Admin  
**Frequency:** 80%  

### Preconditions
- Actor is authenticated (valid JWT token available)
- Actor has sufficient privileges to manage interviews

### Main Flow
- if Actor == Admin
  - views published and unpublished interviews
  - else only published interviews
- Actor creates a new interview
- Actor updates interview information
- Actor retrieves interview details by ID
- Actor deletes the interview

---

## UC-03: Manage Subtopics

**Actor:** Admin / User
**Frequency** 20%

### Preconditions
- Actor is authenticated (valid jwt token available)
- Actor has sufficient privileges to manage subtopics under each subject

### Main Flow
- Actor views each subtopic using their Id.
- Actor creates new subtopics under each subject

---

## UC-04: Manage Questions

**Actor:** Admin / User
**Frequency:** 30%

### Preconditions
- Actor is authenticated (valid JWT token available)
- Actor has sufficient privileges to manage questions under subtopics of each subject

### Main Flow
- Actor views questions via subtopic
- Actor creates a new question
- Actor updates question information
- Actor retrieves question details by ID
- Actor deletes the question

**Mapped JMeter Test:** `PrepMatePerfTest.jmx`
