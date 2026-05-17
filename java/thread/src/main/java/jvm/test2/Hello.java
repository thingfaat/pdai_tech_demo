package jvm.test2;

public class Hello {
    public void say(String name) {
        System.out.println("[Before] say(" + name + ")");
        System.out.println("Hello, " + name);
        System.out.println("[After] say");
    }
}
