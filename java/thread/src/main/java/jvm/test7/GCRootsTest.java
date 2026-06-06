package jvm.test7;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class GCRootsTest {
    public static void main(String[] args) {
        var numList = new ArrayList<>();
        var date = new Date();

        for (var i = 0; i < 100; i++) {
            numList.add(String.valueOf(i));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("数据添加完毕，请操作：");
        new Scanner(System.in).nextLine();
        numList = null;
        date = null;

        System.out.println("numList data 已置空，请操作");
        new Scanner(System.in).nextLine();
        System.out.println("结束");
    }
}
