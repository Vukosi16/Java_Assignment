import java.io.*;

public class Main {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        UserManager userManager = new UserManager();
        FileManager fileManager = new FileManager(eventManager, userManager);
        Menu menu = new Menu(eventManager, userManager);

        fileManager.loadAll();
        menu.start();
        fileManager.saveAll();

    }
}
