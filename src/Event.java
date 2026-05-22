import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Event {
    private final int eventId;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private final int maxParticipants;
    private ArrayList<Student> participants;
    private Queue<Student> waitlist;

    public Event(int id, String name, String date, String time, String location, int maxParticipants){
        this.eventId = id;
        this.eventName = name;
        this.eventDate = date;
        this.eventTime = time;
        this.eventLocation = location;
        this.maxParticipants = maxParticipants;
        this.participants = new ArrayList<Student>();
        this.waitlist = new LinkedList<Student>();
    }

    public int getEventId(){
        return this.eventId;
    }

    public String getEventName(){
        return this.eventName;
    }

    public String getEventDate(){
        return this.eventDate;
    }

    public String getEventTime(){
        return this.eventTime;
    }

    public String getEventLocation(){
        return this.eventLocation;
    }

    public int getMaxParticipants(){
        return this.maxParticipants;
    }

    public Queue<Student> getWaitlist(){
        return this.waitlist;
    }

    public ArrayList<Student> getParticipants(){
        return this.participants;
    }

    public boolean isEventFull(){
        return participants.size() == maxParticipants;
    }

    public boolean isStudentRegistered(Student s){
        for (Student stu : participants){
            if(stu.getStudentId().equals(s.getStudentId())){
                return true;
            }
        }
        return false;
    }

    public void addStudent(Student s){
        participants.add(s);
    }

    public void removeStudent(Student s){
        participants.remove(s);
    }

    public void promoteWaitlist(){
        if (waitlist.isEmpty()){
            System.out.println("Waitlist is empty");
            return;
        }
        Student firstStudent = waitlist.remove();
        participants.add(firstStudent);
    }

    //for understanding, this is a setter right?
    //and also, should all these be optional and only allow for changes where needed
    public void updateEvent(String name, String time , String location){
        this.eventName = name;
        this.eventTime = time;
        this.eventLocation = location;
    }

    public void showEventDetails(){
        System.out.println("Event ID: " + this.eventId);
        System.out.println("Event name: " + this.eventName);
        System.out.println("Event date: " + this.eventDate);
        System.out.println("Event time: " + this.eventTime);
        System.out.println("Event location: " + this.eventLocation);
        System.out.println("Event participants: " + participants.size());
        System.out.println("Event waitlist count: " + waitlist.size());
    }
}
