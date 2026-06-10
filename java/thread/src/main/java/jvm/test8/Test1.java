package jvm.test8;

/**
 * 成员变量的赋值过程：
 * 1、默认初始化 2、显式初始化/代码块中初始化 3、 构造器中初始化 4、有了对象之后可以通过“对象.属性”或“对象.方法”的方式对成员变量进行赋值
 */
class Father {
    int x = 10;

    public Father() {
        this.print();
        x = 20;
    }

    public void print() {
        System.out.println("father.x = " + x);
    }
}

class Son extends Father {
    int x = 30;

    public Son() {
        this.print();
        x = 40;
    }

    public void print() {
        System.out.println("son.x = " + x);
    }
}

public class Test1 {
    public static void main(String[] args) {
        Father f = new Son();
        System.out.println(f.x);
    }
}
