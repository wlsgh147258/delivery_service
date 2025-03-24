package delivery.ui;

import java.util.Scanner;

public class AppUi {

    private static Scanner sc = new Scanner(System.in);

    public static String inputString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }


}
