import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {

    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private AtomicInteger finishCount;
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier startLine;
    private CountDownLatch finishLine;


    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier startLine, CountDownLatch finishLine, AtomicInteger finishCount) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.startLine = startLine;
        this.finishLine = finishLine;
        this.finishCount = finishCount;
    }
    @Override
    public void run() {
        try {
            /*Подготовка*/
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            /*На старте*/
            this.startLine.await();

            for (int i = 0; i < this.race.getStages().size(); i++) {
                this.race.getStages().get(i).go(this);}

            this.finishLine.countDown();

            int place = finishCount.incrementAndGet();
            if(place == 1) {
                System.out.println(this.name + "ПОБЕДИЛ В ГОНКЕ!!!");
            }

          } catch (Exception e) {
            e.printStackTrace();
        }

        }



}