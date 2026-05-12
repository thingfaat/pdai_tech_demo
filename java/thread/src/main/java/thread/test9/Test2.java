package thread.test9;

import java.util.concurrent.locks.LockSupport;

public class Test2 {
    public static void main(String[] args) {
        MyThread1 myThread1 = new MyThread1(Thread.currentThread());
        myThread1.start();
        System.out.println("before park");
        LockSupport.park("Test2");
        System.out.println("after park");
    }
}

class MyThread1 extends Thread {
    private Object object;

    public MyThread1(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        System.out.println("before unpark");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("blocker info " + LockSupport.getBlocker((Thread) object));
        LockSupport.unpark((Thread) object);
        // 休眠500ms 保证先执行park中的setBlocker
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("blocker info " + LockSupport.getBlocker((Thread) object));
        System.out.println("after unpark");
    }
}
