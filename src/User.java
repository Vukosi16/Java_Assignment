public abstract class User {
    protected String name;
    protected String role;

    protected User(String name, String role){
        this.name = name;
        this.role = role;
    }

    public String getName(){
        return this.name;
    }

    public String getRole(){
        return this.role;
    }
}
