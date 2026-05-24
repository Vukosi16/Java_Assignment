public class Main {
    public void main(String[] args){
        EventManager evm = new EventManager();
        UserManager usm = new UserManager();

        Menu m1 = new Menu(evm, usm);

        m1.start();

    }
}
