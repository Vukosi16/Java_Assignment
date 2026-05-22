public class Staff extends User{
    private final String staffId;

     public Staff(String id, String name){
        super(name, "Staff");
        this.staffId = id;
    }

    public String getStaffId(){
         return staffId;
    }

}
