package interpret;

public class ForTestNotProduct {
    public static String test = "test";
    private static String forConstructorTest = "initial";
    public String instanceField;

    public ForTestNotProduct() {
        this.instanceField = "instance";
    }
    public ForTestNotProduct(String forTest) {
        this.forConstructorTest = forTest;
    }

    public void Hello() {
        System.out.println("Hello");
    }
}
