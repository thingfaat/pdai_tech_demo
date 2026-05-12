package thread.test9;

class MyThread extends Thread {
    @Override
    public void run() {
        synchronized (this) {
            System.out.println("before notify");
            notify();
            System.out.println("after notify");
        }
    }
}

public class WaitAndNotifyDemo {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        synchronized (myThread) {
            try {
                myThread.start();
                // 主线程睡眠
                Thread.sleep(3000);
                System.out.println("before wait");
                myThread.wait();
                System.out.println("after wait");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
