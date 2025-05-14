package be.virtualmem.logic;

public class SystemClock {
    public static final SystemClock instance = new SystemClock();
    private int time;

    private SystemClock() {
        time = 0;
    }

    public static SystemClock getInstance() {
        return instance;
    }

    public int getTime() {
        return time;
    }

    public void tick() {
        time++;
    }
}
