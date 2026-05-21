public abstract class User {
    protected String name;
    protected String role;

    protected User(String name, String role){
        this.name = name;
        this.role = role;
    }

    protected String getName(){
        return this.name;
    }

    protected String getRole(){
        return this.role;
    }
}
