package jvm.test6;

/**
 * 测试对象实例化过程
 * <p>
 * 给对象属性赋值的操作：
 * 1、属性的默认初始化
 * 2、显式初始化：
 * 3、代码块中初始化
 * 4、构造器中初始化
 */
public class Customer {
    int id = 100;
    String name;
    Account acct;

    {
        name = "匿名客户";
    }

    public Customer() {
        this.acct = new Account();
    }

    public static void main(String[] args) {
        final var customer = new Customer();
    }
}
class Account {
}