import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainClass {

    public static final int CARS_COUNT = 4;
    public static final AtomicInteger finishCount = new AtomicInteger(0);
    private static ExecutorService executorService = Executors.newFixedThreadPool(CARS_COUNT);



    public static void main(String[] args) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

       /*Машины выезжают на старт*/
        CyclicBarrier startGO = new CyclicBarrier(CARS_COUNT + 1, () -> {

                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        });

        /*Счетчик финиша*/

        CountDownLatch finishEND = new CountDownLatch(CARS_COUNT);

        /*Полоса препятствий*/
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        /*Создаем участников*/
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), startGO, finishEND, finishCount);
        }

        /*Запускаем гонку*/
        for (int i = 0; i < cars.length; i++) {

          executorService.execute(cars[i]); }

        try {
            startGO.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        try {
            finishEND.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            executorService.shutdown();
        }

    }
}
