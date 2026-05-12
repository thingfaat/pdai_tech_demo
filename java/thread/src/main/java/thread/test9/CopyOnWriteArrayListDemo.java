package thread.test9;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

class PutThread extends Thread {
    private CopyOnWriteArrayList<Integer> cowl;

    public PutThread(CopyOnWriteArrayList<Integer> cowl) {
        this.cowl = cowl;
    }

    @Override
    public void run() {
        try {
            for (int i = 100; i < 110; i++) {
                cowl.add(i);
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> cowal = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            cowal.add(i);
        }
        PutThread p1 = new PutThread(cowal);
        p1.start();
        Iterator<Integer> iterator = cowal.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }
        System.out.println();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        iterator = cowal.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }
}
