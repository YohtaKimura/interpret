package interpret;

public class ForTestNotProduct {
    public static String test = "test";
    private static String forConstructorTest = "initial";

    public ForTestNotProduct() {
    }
    public ForTestNotProduct(String forTest) {
        this.forConstructorTest = forTest;
    }

    public void Hello() {
        System.out.println("Hello");
    }
}
