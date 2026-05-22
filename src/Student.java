public class Student extends User{
    private final String studentId;

    public Student(String id, String name){
        super(name,"Student");
        this.studentId = id;
    }

    public String getStudentId(){
        return this.studentId;
    }

}
