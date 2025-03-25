package delivery.ui;


import delivery.jdbc.DBConnectionManager;
import delivery.menu.domain.Menu;
import delivery.user.domain.Grade;
import delivery.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static delivery.common.Condition.CATEGORY;
import static delivery.common.Condition.MENU_NAME;
import static delivery.menu.repository.MenuRepository.*;

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
            System.out.println("# 올바른 정수 입력값이 아닙니다!");
        } finally {
            sc.nextLine(); // try에서도(엔터 입력값), catch에서도(쓰레기 문자열 수거) nextLine()이 동작해야 함.
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

    public static void userManagementScreen(){
        System.out.println("\n========= 사용자 관리 시스템 =========");
        System.out.println("### 1. 사용자 추가");
        System.out.println("### 2. 사용자 정보 검색");
        System.out.println("### 3. 사용자 정보 수정/삭제");
        System.out.println("### 4. 첫 화면으로 가기");
        System.out.println();
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
    public static void MenuManagementScreen(){
        System.out.println("\n========= 식당 관리 시스템 =========");
        System.out.println("### 1. 메뉴 추가");
        System.out.println("### 2. 메뉴 정보 검색");
        System.out.println("### 3. 메뉴 정보 수정/삭제");
        System.out.println("### 4. 첫 화면으로 가기");
        System.out.println();
    }

}
