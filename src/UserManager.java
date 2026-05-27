import java.util.*;

public class UserManager {
    private Map<String, User> users;
    private int nextStudentId;
    private int nextStaffId;

    public UserManager(){
        this.users = new HashMap<>();
        this.nextStudentId = 101;
        this.nextStaffId = 101;
    }

    public void registerUser(String name, String role){
        if (role.toLowerCase().equals("student")){
            User createdStudent = new Student("S" + nextStudentId, name);
            users.put("S" + nextStudentId, createdStudent);
            System.out.println("Student created!\nID number is S" + nextStudentId);
            nextStudentId++;
        }else if (role.toLowerCase().equals("staff")){
            User createdStaff = new Staff("ST" + nextStaffId, name);
            users.put("ST" + nextStaffId, createdStaff);
            System.out.println("Staff user created!\nID number is ST" + nextStaffId);
            nextStaffId++;
        } else{
            System.out.println("Please select a Student or Staff role!");
        }
    }

    public User loginUser(String id){
        if (users.containsKey(id)){
            return users.get(id);
        }else {
            System.out.println("User not found.");
            return null;
        }
    }

    public void loadUsers(String id, String name, String role){
        if (role.equalsIgnoreCase("student")){
            User loadedStudent = new Student(id, name);
            users.put(id, loadedStudent);

            int loadedId = Integer.parseInt(id.substring(1));
            if (loadedId >= nextStudentId) {
                nextStudentId = loadedId + 1;
            }
        } else if (role.equalsIgnoreCase("staff")){
            User loadedStaff = new Staff(id, name);
            users.put(id, loadedStaff);

            int loadedId = Integer.parseInt(id.substring(2));
            if (loadedId >= nextStaffId) {
                nextStaffId = loadedId + 1;
            }
        }
    }

    public Map<String, User> getUsers(){
        return this.users;
    }
}
