import java.util.Scanner;

public class UserInterface {

    public String UserInterface() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Mata in en text");
        String text = scan.nextLine();
        scan.close();
        return text;

    }

    public void printText(String text) {
        System.out.println(text);
    }

    public void printText(Double num) {
        System.out.println(num);
    }

}
