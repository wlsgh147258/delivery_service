package delivery.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppUi {

    private static Scanner sc = new Scanner(System.in);

    public static String inputString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    public static int inputInteger(String message) {

        System.out.print(message);
        int num = 0;
        try {
            num = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("# 올바른 입력값이 아닙니다.");
        } finally {
            sc.nextLine();
        }
        return num;
    }

    public static void restaurantManagementScreen(){
        System.out.println("\n========= 식당 관리 시스템 =========");
        System.out.println("### 1. 신규 식당 추가");
        System.out.println("### 2. 식당 정보 검색");
        System.out.println("### 3. 식당 정보 수정/삭제");
        System.out.println("### 4. 첫 화면으로 가기");
        System.out.println();
    }

    public static void restaurantUpdateScreen(){
        System.out.println("\n========= 식당 정보 수정 시스템 =========");
        System.out.println("### 1. 식당명 수정");
        System.out.println("### 2. 식당 카테고리 수정");
        System.out.println("### 3. 식당 영업 시간 수정");
        System.out.println("### 4. 식당 전화 번호 수정");
        System.out.println("### 5. 식당 주소 수정");
        System.out.println("### 6. 식당 상세 정보 수정");
        System.out.println("### 7. 식당 메뉴 수정");
        System.out.println("### 8. 식당 정보 삭제");
        System.out.println();
    }

    public static void MenuManagementScreen(){
        System.out.println("\n========= 식당 메뉴 관리 시스템 =========");
        System.out.println("### 1. 신규 메뉴 추가");
        System.out.println("### 2. 식당 메뉴 검색");
        System.out.println("### 3. 식당 메뉴 삭제");
        System.out.println("### 4. 첫 화면으로 가기");
        System.out.println();
    }



}
