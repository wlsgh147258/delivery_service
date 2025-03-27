package delivery.ui;


import delivery.jdbc.DBConnectionManager;
import delivery.main.Main;
import delivery.menu.domain.Menu;
import delivery.restaurants.domain.Restaurants;
import delivery.restaurants.repository.RestaurantsRepository;
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
        System.out.println("### 1. 대기중인 주문 확인하기");
        System.out.println("### 2. 조리 완료 체크");
        System.out.println("### 3. 신규 식당 추가");
        System.out.println("### 4. 식당 정보 검색");
        System.out.println("### 5. 식당 정보 수정/삭제");
        System.out.println("### 6. 첫 화면으로 가기");
        System.out.println();
    }

    public static void restaurantUpdateScreen() {
        System.out.println("\n========= 식당 정보 수정 시스템 =========");
        System.out.println("### 1. 메뉴 관리");
        System.out.println("### 2. 식당명 수정");
        System.out.println("### 3. 식당 카테고리 수정");
        System.out.println("### 4. 식당 영업 시간 수정");
        System.out.println("### 5. 식당 전화 번호 수정");
        System.out.println("### 6. 식당 주소 수정");
        System.out.println("### 7. 식당 상세 정보 수정");
        System.out.println("### 8. 식당 정보 삭제");
        System.out.println();
    }

    public static void userManagementScreen(){
        System.out.println("\n========= 사용자 관리 시스템 =========");
        System.out.println("### 1. 사용자 정보 검색");
        System.out.println("### 2. 사용자 정보 수정/탈퇴");
        System.out.println("### 3. 사용자 등급");
        System.out.println("### 4. 리뷰 서비스");
        System.out.println("### 5. 첫 화면으로 가기");
        System.out.println();
    }

    public static void riderManagementScreen(){
        System.out.println("\n========= 라이더 관리 시스템 =========");
        System.out.println("### 1. 대기 중인 주문 확인");
        System.out.println("### 2. 배달 완료 체크");
        System.out.println("### 3. 라이더 정보 검색");
        System.out.println("### 4. 라이더 정보 수정/탈퇴");
        System.out.println("### 5. 첫 화면으로 가기");
        System.out.println();
    }

    public static int reviewManagementScreenForMaster(RestaurantsRepository restaurantsRepository) {
        try {

            List<Restaurants> restaurants = restaurantsRepository.searchRestaurantByOwner(Main.user.getUserNum());
            System.out.println("\n========= 점주 리뷰 시스템 진입 =========");
            if(!restaurants.isEmpty()) {
                System.out.printf("\n========= 운영중인 가게 목록 (%d)개 ========= \n", restaurants.size());
                for (Restaurants restaurant : restaurants) {
                    System.out.println(restaurant);
                }
                System.out.println("0. 처음화면으로 나가기.");
                System.out.println("\n========= 리뷰를 확인할 업장 코드를 입력하세요. =========");
                int restaurantNum = inputInteger(">>> ");
                if (restaurantNum == 0) {
                    return -1;
                }
                if (restaurants.stream().noneMatch(rest -> rest.getStore_num() == restaurantNum)) {
                    System.out.println("잘못된 업장 코드를 입력하셨습니다. 처음 화면으로 나갑니다.");
                    return -1;
                }
                return restaurantNum;

            } else {
                System.out.println("운영중인 가게가 없습니다. 처음 화면으로 나갑니다.");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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
        System.out.println("### 3. 주문 내역 확인하기");
        System.out.println("### 4. 첫 화면으로 가기");
    }
    public static void MenuManagementScreen(){
        System.out.println("\n========= 식당 메뉴 관리 시스템 =========");
        System.out.println("### 1. 메뉴 추가");
        System.out.println("### 2. 메뉴 정보 검색");
        System.out.println("### 3. 메뉴 정보 수정/삭제");
        System.out.println("### 4. 이전 화면으로 돌아가기");
        System.out.println();
    }

    public static void menuUpdateScreen() {
        System.out.println("\n========= 식당 메뉴 수정 시스템 =========");
        System.out.println("### 1. 메뉴 이름");
        System.out.println("### 2. 메뉴 카테고리 수정");
        System.out.println("### 3. 메뉴 가격 수정");
        System.out.println("### 4. 메뉴 삭제");
        System.out.println();
    }

    public static void userUpdateScreen() {
        System.out.println("\n========= 사용자 정보 수정 시스템 =========");
        System.out.println("### 1. 회원 이름 수정");
        System.out.println("### 2. 회원 아이디 수정");
        System.out.println("### 3. 회원 비밀번호 수정");
        System.out.println("### 4. 회원 주소 수정");
        System.out.println("### 5. 회원 전화번호 수정");
        System.out.println("### 6. 회원 탈퇴 수정");
        System.out.println();
    }

    public static String findUserType(String id, String pw) {
        String userType = "";
        List<User> searchList = new ArrayList<>();
        String sql = "SELECT * FROM users_info WHERE user_id = ? AND user_password = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("user_num"),
                        rs.getString("user_name"),
                        rs.getString("user_id"),
                        rs.getString("user_password"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("user_type"),
                        Grade.valueOf(rs.getString("user_grade")),
                        rs.getString("active"));

                searchList.add(user);
            }


            if (!searchList.isEmpty()) {
                for (User users : searchList) {
                    userType = users.getUserType();
                }
            }
            return userType;

        } catch (Exception e) {
            System.out.println("아이디/비밀번호가 틀렸습니다.");
            e.printStackTrace();
        }
        return userType;
    }

}