package oop;

public class TimerMain {
    public static void main(String[] args) {
        Timer timer = new Timer(56);
        timer.setSeconds(120);
        Timer timer2 = new Timer(200);
        timer2.setSeconds(500);

        timer.setSeconds(timer.getSeconds() + 60); // Adding a minute
        timer.setSeconds(timer.getSeconds() - 10); // Subtracting 10 seconds

        System.out.println(timer.getSeconds());
    }
}
