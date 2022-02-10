import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    /*Устанавливаем ограничения для прохождения туннеля*/
    private Semaphore bandwidthTunnel = new Semaphore(MainClass.CARS_COUNT/2);

    public Tunnel() {

        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + getDescription());
                bandwidthTunnel.acquire();
                System.out.println(c.getName() + " начал этап: " + getDescription());
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + getDescription());
                bandwidthTunnel.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}