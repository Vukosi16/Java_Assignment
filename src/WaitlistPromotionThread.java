public class WaitlistPromotionThread extends Thread{
    private Event event;

    public WaitlistPromotionThread(Event event) {
        this.event = event;
    }

    public void run() {
        event.promoteWaitlist();
    }
}
