import java.util.*;

public class EventManager {
    private Map<Integer, Event> events;
    private int nextEvent;

    public EventManager(){
         this.events = new HashMap<>();
         nextEvent = 101;
    }

    public void createEvent(String name, String date, String time, String location,  int maxPart){
        Event newEvent = new Event(
                nextEvent, name, date, time, location, maxPart
        );

        this.events.put(nextEvent,newEvent);
        System.out.println("Event created");
        nextEvent++;
    }

    //will load in all the events from the text file
    public void loadEvent(int id,String name, String date, String time, String location,  int maxPart){
        Event newEvent = new Event(
                id, name, date, time, location, maxPart
        );

        this.events.put(id, newEvent);
        if (id >= nextEvent) {
            nextEvent = id + 1;
        }
    }

    public void updateEvent(int id, String name, String time, String location){
        if(events.containsKey(id)){
            events.get(id).updateEvent(name, time, location);
            System.out.println("Event updated");
        }else{
            System.out.println("No event found");
        }
    }

    public void cancelEvent(int id){
        if(events.containsKey(id)){
            events.remove(id);
            System.out.println("Event canceled.");
        }else{
            System.out.println("Event not found.");
        }
    }

    public void viewAllEvents(){
        if(!(events.isEmpty())){
            for(Integer key: events.keySet()){
                System.out.println(key + " " + events.get(key).getEventName());
            }
        }else{
            System.out.println("There are no events.");
        }
    }

    public Map<Integer, Event> getEvents(){
        return events;
    }

    public void viewEventsWithParticipantsAndWaitlist(){
        if(!(events.isEmpty())){
            for(Integer key: events.keySet()){
                System.out.println(key + " " + events.get(key).getEventName());
                System.out.println("Participants");
                ArrayList<Student> Pstudents = events.get(key).getParticipants();
                for(Student stu: Pstudents){
                    System.out.println(stu.getStudentId() + " - " + stu.getName());
                }
                System.out.println();
                System.out.println("Waitlisted students.");
                Queue<Student> Wstudents = events.get(key).getWaitlist();
                for(Student stu: Wstudents){
                    System.out.println(stu.getStudentId() + " - " + stu.getName());
                }
                System.out.println();
            }
        }else{
            System.out.println("There are no events.");
        }
    }

    public void registerStudent(Student s, int eventId){
        if (!(events.get(eventId).isStudentRegistered(s)) && !(events.get(eventId).isStudentWaitlisted(s))){
            if (events.get(eventId).isEventFull()){
                events.get(eventId).addToWaitlist(s);
                System.out.println("Student added to waitlist.");
            } else{
                events.get(eventId).addStudent(s);
                System.out.println("Student is registered.");
            }
        }else {
            System.out.println("Student is already registered");
        }

    }

    public void checkRegistrationStatus(Student s, int eventId){
        if (events.get(eventId).isStudentRegistered(s)){
            System.out.println("Student is registered");
        } else if (events.get(eventId).isStudentWaitlisted(s)) {
            System.out.println("Student is waitlisted");
        }else {
            System.out.println("Student isn't registered");
        }
    }

    public void searchEvents(String query){
        boolean found = false;

        for (Event event: events.values()){
            if (event.getEventDate().contains(query)){
                found = true;
                System.out.println("Event found.");
                event.showEventDetails();
            } else if (event.getEventName().contains(query)) {
                found = true;
                System.out.println("Event found.");
                event.showEventDetails();
            }
        }
        if (!found) System.out.println("Event not found");
    }

    public void sortEvents(String criteria){
        ArrayList<Event> listOfEvents = new ArrayList<>();

        for (Event event: events.values()){
            listOfEvents.add(event);
        }

        if(criteria.equals("name")){
            listOfEvents.sort(Comparator.comparing(Event::getEventName));
            System.out.println("Events sorted by name");

            for (Event e : listOfEvents){
                System.out.println(e.getEventName());
            }
        } else if (criteria.equals("date")) {
            listOfEvents.sort(Comparator.comparing(Event::getEventDate));
            System.out.println("Events sorted by date");

            for (Event e : listOfEvents){
                System.out.println(e.getEventName());
            }
        }else {
            System.out.println("Select a proper sort criterion");
        }
    }

    public void cancelRegistration(Student s, int eventId){
        if (events.get(eventId).isStudentRegistered(s)){
            events.get(eventId).removeStudent(s);

            WaitlistPromotionThread t1 = new WaitlistPromotionThread(events.get(eventId));
            t1.start();
            System.out.println("Student has been deregistered.");
        } else if (events.get(eventId).isStudentWaitlisted(s)) {
            events.get(eventId).removeFromWaitlist(s);
            System.out.println("Student has been removed from waitlist.");
        }else {
            System.out.println("Student does not exist");
        }
    }
}
