import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

public class FileManager {
    private EventManager eventManager;
    private UserManager userManager;

    //using relative path that will create the files on any folder in any system
    private static final String DB_FOLDER = "src/Database/";
    private static final String EVENTS_FILE = DB_FOLDER + "events.txt";
    private static final String PARTICIPANTS_FILE = DB_FOLDER + "participants.txt";
    private static final String WAITLIST_FILE = DB_FOLDER + "waitlist.txt";
    private static final String USERS_FILE = DB_FOLDER + "users.txt";

    public FileManager(EventManager evm, UserManager usm){
        this.eventManager = evm;
        this.userManager = usm;
    }

    //will save all the data to text documents upon program closure
    public void saveAll(){
        saveEvents();
        saveParticipants();
        saveWaitlist();
        saveUsers();
    }

    //will load all events from the text docuemtns to map to the objects
    public void loadAll(){
        initFiles();
        loadEvents();
        loadUsers();
        loadParticipants();
        loadWaitlist();
    }

    private void saveEvents(){
        Map<Integer, Event>events = eventManager.getEvents();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_FILE))){

            for (Integer key:  events.keySet()){
                writer.write(events.get(key).getEventId() + "," + events.get(key).getEventName() + "," + events.get(key).getEventDate() + "," + events.get(key).getEventTime() + "," + events.get(key).getEventLocation()  + "," + events.get(key).getMaxParticipants());
                writer.newLine();
            }
        }catch (IOException e){
            System.out.println("Something went wrong with saving the data");
        }

    }

    private void saveParticipants(){
        Map<Integer, Event>events = eventManager.getEvents();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PARTICIPANTS_FILE))){

            for (Integer key:  events.keySet()){
                ArrayList<Student> participants;
                participants = events.get(key).getParticipants();

                for (Student student: participants){
                    writer.write(events.get(key).getEventId() + "," + student.getStudentId() + "," + student.getName());
                    writer.newLine();
                }

            }
        }catch (IOException e){
            System.out.println("Something went wrong with saving the data");
        }
    }

    private void saveWaitlist() {
        Map<Integer, Event> events = eventManager.getEvents();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(WAITLIST_FILE))){

            for (Integer key:  events.keySet()){
                Queue<Student> waitlist;
                waitlist = events.get(key).getWaitlist();

                for (Student student: waitlist){
                    writer.write(events.get(key).getEventId() + "," + student.getStudentId() + "," + student.getName());
                    writer.newLine();
                }

            }
        }catch (IOException e){
            System.out.println("Something went wrong with saving the data");
        }
    }

    private void saveUsers(){
        Map<String, User> users = userManager.getUsers();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))){
            for (String key: users.keySet()){
                writer.write(key + "," + users.get(key).getName() + "," + users.get(key).getRole());
                writer.newLine();
            }

        } catch (IOException e){
            System.out.println("Something went wrong with saving the data");
        }
    }

    private void loadEvents(){
        String name, date, time, location;
        int id, maxPart;

        try(BufferedReader reader = new BufferedReader(new FileReader(EVENTS_FILE))){
            String line;

            while((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                id = Integer.parseInt(data[0]);
                name = data[1];
                date = data[2];
                time = data[3];
                location = data[4];
                maxPart = Integer.parseInt(data[5]);

                eventManager.loadEvent(id,name, date, time, location, maxPart);
            }
        } catch (IOException e){
            System.out.println("Something went wrong with retrieving the data.");
        }
    }

    private void loadParticipants(){
        int eventId;
        String studentId, name;
        Map<Integer, Event> events = eventManager.getEvents();


        try(BufferedReader reader = new BufferedReader(new FileReader(PARTICIPANTS_FILE))){
            String line;

            while((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                eventId = Integer.parseInt(data[0]);
                studentId = data[1];
                name = data[2];

                Student loadedStudent = new Student(studentId, name);
                Event event = events.get(eventId);
                event.addStudent(loadedStudent);
            }
        } catch (IOException e){
            System.out.println("Something went wrong with retrieving the data.");
        }
    }

    private void loadWaitlist(){
        int eventId;
        String studentId, name;
        Map<Integer, Event> events = eventManager.getEvents();


        try(BufferedReader reader = new BufferedReader(new FileReader(WAITLIST_FILE))){
            String line;

            while((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                eventId = Integer.parseInt(data[0]);
                studentId = data[1];
                name = data[2];

                Student loadedStudent = new Student(studentId, name);
                Event event = events.get(eventId);
                event.addToWaitlist(loadedStudent);
            }
        } catch (IOException e){
            System.out.println("Something went wrong with retrieving the data.");
        }
    }

    private void loadUsers(){
        String userId, name, role;

        try(BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))){
            String line;

            while((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                userId = data[0];
                name = data[1];
                role = data[2];

                userManager.loadUsers(userId, name, role);
            }
        } catch (IOException e){
            System.out.println("Something went wrong with retrieving the data.");
        }
    }

    //initialized the creation of text if not existant on first run
    private void initFiles() {
        String[] files = {EVENTS_FILE, PARTICIPANTS_FILE, WAITLIST_FILE, USERS_FILE};
        for (String filePath : files) {
            File file = new File(filePath);
            try {
                file.getParentFile().mkdirs(); // creates folders if they don't exist
                file.createNewFile(); // creates file if it doesn't exist
            } catch (IOException e) {
                System.out.println("Could not create file: " + filePath);
            }
        }
    }
}
