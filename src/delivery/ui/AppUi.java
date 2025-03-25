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
            num = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("# 올바른 입력값이 아닙니다.");
        }
        return num;
    }

    public static void restaurantManagementScreen() {
        System.out.println("\n========= 식당 관리 시스템 =========");
        System.out.println("### 1. 신규 식당 추가");
        System.out.println("### 2. 식당 정보 검색");
        System.out.println("### 3. 식당 정보 수정/삭제");
        System.out.println("### 4. 이전 화면으로 돌아가기");
        System.out.println();
    }

    public static void restaurantUpdateScreen() {
        System.out.println("\n========= 식당 정보 수정 시스템 =========");
        System.out.println("### 1. 식당명 수정");
        System.out.println("### 2. 식당 카테고리 수정");
        System.out.println("### 3. 식당 영업 시간 수정");
        System.out.println("### 4. 식당 전화 번호 수정");
        System.out.println("### 5. 식당 주소 수정");
        System.out.println("### 6. 식당 정보 수정");
        System.out.println("### 7. 식당 정보 삭제");
        System.out.println();
    }

    public static void userManagementScreen() {
        System.out.println("\n========= 회원 관리 시스템 =========");
        System.out.println("### 1. 회원 가입");
        System.out.println("### 2. 회원 검색");
        System.out.println("### 3. 회원 탈퇴");
        System.out.println("### 4. 이전 화면으로 돌아가기");
    }

    public static void reviewManagementScreen() {
        System.out.println("\n========= 리뷰 시스템 =========");
        System.out.println("### 1. 리뷰 입력");
        System.out.println("### 2. 리뷰 검색");
        System.out.println("### 3. 리뷰 삭제");
        System.out.println("### 4. 이전 화면으로 돌아가기");
    }

    public static void orderManagementScreen() {
        System.out.println("\n========= 주문 관리 시스템 =========");
        System.out.println("### 1. 주문 하기");
        System.out.println("### 2. 주문 취소");
        System.out.println("### 3. 이전 화면으로 돌아가기");
    }

    public static void startScreen() {
        System.out.println("\n========= 음식 배달 관리 시스템 =========");
        System.out.println("### 1. 회원 관리 시스템");
        System.out.println("### 2. 주문 관리 시스템");
        System.out.println("### 3. 식당 관리 시스템");
        System.out.println("### 4. 리뷰 관리 시스템");
        System.out.println("### 5. 프로그램 종료");
    }
}