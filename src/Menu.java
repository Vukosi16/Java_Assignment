import java.util.Scanner;

public class Menu {
    private EventManager eventManager;
    private UserManager userManager;
    private User currentUser;
    private Scanner sc;

    public Menu(EventManager evm, UserManager usm){
        this.eventManager = evm;
        this.userManager = usm;
        this.currentUser = null;
        this.sc = new Scanner(System.in);
    }

    public void start(){
        String regOrLoginFlag;

        System.out.println("Welcome to the Campus management system.\nPlease chose whether you are registering or logging in.");

        do{
            System.out.print("Type Register or Login: ");
            regOrLoginFlag = sc.nextLine();

            if (regOrLoginFlag.equalsIgnoreCase("login")){
                handleLogin();
            } else if (regOrLoginFlag.equalsIgnoreCase("register")) {
                handleRegister();
            }else{
                System.out.println("Please type login or register.");
            }
        }while(currentUser == null);

        if (currentUser.getRole().equalsIgnoreCase("Student")){
            showStudentMenu();
        } else if (currentUser.getRole().equalsIgnoreCase("Staff")) {
            showStaffMenu();
        }

    }

    private void handleRegister(){
        String currentUserName;
        String currentUserRole;

        System.out.println("\nHello.\nTo register a user please enter your name and the role you wish to assume.");
        System.out.print("Name: ");
        currentUserName = sc.nextLine();
        System.out.print("\nRole: Chose between a student and a staff member: ");
        currentUserRole = sc.nextLine();

        userManager.registerUser(currentUserName, currentUserRole);
        System.out.println("Please login using your new user ID.");
    }

    public void handleLogin(){
        String currentUserId;

        System.out.println("\nWelcome back.\nPlease enter your ID to find your user account: ");
        currentUserId = sc.nextLine();

        currentUser = userManager.loginUser(currentUserId);
    }

    private void showStudentMenu(){

        System.out.println("Welcome " + currentUser.getName() + ". Navigate the menu and select your path.\nMenu options: ");
        int menuSelectionFlag;

        do{

            int eventID;

            System.out.println("\n1: View all events.\n2: Register for an event.\n3: Cancel registration.\n4: View registration status.\n5: Search for an event\n6: Logout\n");
            System.out.print("Please pick an option from the menu above: ");
            menuSelectionFlag = sc.nextInt();
            sc.nextLine();


            switch (menuSelectionFlag) {
                case 1:
                    eventManager.viewAllEvents();
                    break;
                case 2:
                    System.out.println("Which event do you want to register for: ");
                    eventManager.viewAllEvents();
                    System.out.print("Event: ");
                    eventID = sc.nextInt();
                    sc.nextLine();

                    eventManager.registerStudent((Student) currentUser, eventID);
                    break;
                case 3:
                    System.out.println("Which event do you want to deregister from:");
                    eventManager.viewAllEvents();
                    System.out.println("Event: ");
                    eventID = sc.nextInt();
                    sc.nextLine();

                    eventManager.cancelRegistration((Student) currentUser, eventID);
                    break;
                case 4:
                    System.out.println("Which event do you want to view registration status:");
                    eventManager.viewAllEvents();
                    System.out.println("Event: ");
                    eventID = sc.nextInt();
                    sc.nextLine();

                    eventManager.checkRegistrationStatus((Student) currentUser, eventID);
                    break;
                case 5:
                    String query;

                    System.out.println("Which event are you looking for: ");
                    System.out.println("Use the event name or date to search");
                    query = sc.nextLine();
                    eventManager.searchEvents(query);
                    break;
                case 6:
                    System.out.println("Logging out.");
                    break;
                default:
                    System.out.println("please select an option from the menu.");
                    break;
            }

        } while (menuSelectionFlag != 6);

    }

    private void showStaffMenu(){
        int menuSelectionFlag;

        System.out.println("Welcome " + currentUser.getName() + ". Navigate the menu and select your path.\nMenu options: ");
        do{
            String name, date, time, location;
            int maxParti;
            int eventID;

            System.out.println("\n1: Create event.\n2: Update event.\n3: Cancel event.\n4: View all events with participants and waitlists.\n5: Search for an event\n6: Sort events\n7: logout\n");
            System.out.print("Please pick an option from the menu above: ");
            menuSelectionFlag = sc.nextInt();
            sc.nextLine();

            switch (menuSelectionFlag){
                case 1:
                    System.out.println("To create an event I need the event name, date, time, location and the maximum amount of participants.");
                    System.out.print("Name: ");
                    name = sc.nextLine();
                    System.out.print("\nDate: ");
                    date = sc.nextLine();
                    System.out.print("\nTime: ");
                    time = sc.nextLine();
                    System.out.print("\nLocation: ");
                    location = sc.nextLine();
                    System.out.print("\nMax Participants in event: ");
                    maxParti = sc.nextInt();
                    sc.nextLine();

                    eventManager.createEvent(name, date, time, location, maxParti);
                    break;
                case 2:
                    System.out.println("To update an event you need to input the event id, name, time and location .");
                    System.out.println("Here are all the events: ");
                    eventManager.viewAllEvents();
                    System.out.print("Enter the event ID: ");
                    eventID = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\nName: ");
                    name = sc.nextLine();
                    System.out.print("\nTime: ");
                    time = sc.nextLine();
                    System.out.print("\nLocation: ");
                    location = sc.nextLine();

                    eventManager.updateEvent(eventID, name, time, location);
                    break;
                case 3:
                    System.out.println("to cancel an event you need to input the event ID.");
                    System.out.println("Here are all the events: ");
                    eventManager.viewAllEvents();
                    System.out.print("Enter the event ID: ");
                    eventID = sc.nextInt();
                    sc.nextLine();

                    eventManager.cancelEvent(eventID);
                    break;
                case 4:
                    System.out.println("View all events with their participants and waitlists: ");
                    eventManager.viewEventsWithParticipantsAndWaitlist();
                    break;
                case 5:
                    String query;

                    System.out.println("Which event are you looking for: ");
                    System.out.println("Use the event name or date to search");
                    query = sc.nextLine();
                    eventManager.searchEvents(query);
                    break;
                case 6:
                    String criteria;
                    System.out.println("Choose whether to sort by the event name or date");
                    criteria = sc.nextLine();

                    eventManager.sortEvents(criteria);
                    break;
                case 7:
                    System.out.println("Logging out.");
                    break;
                default:
                    System.out.println("Please select a menu item from 1 to 7.");
                    break;
            }

        } while(menuSelectionFlag != 7);

    }
}

