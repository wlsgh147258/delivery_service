package delivery.user.service;

import static delivery.ui.AppUi.*;

import delivery.common.DeliveryService;
import delivery.main.Main;
import delivery.menu.domain.Menu;
import delivery.menu.repository.MenuRepository;
import delivery.order.domain.Order;
import delivery.order.repository.OrderRepository;
import delivery.review.service.ReviewService;
import delivery.user.domain.Grade;
import delivery.user.domain.User;
import delivery.user.repository.UserRepository;

import java.util.List;

public class UserService implements DeliveryService {
    private final UserRepository userRepository = new UserRepository();
    private final OrderRepository orderRepository = new OrderRepository();
    private final MenuRepository menuRepository = new MenuRepository();

    private final int FIND_BY_NUM = 1;
    private final int FIND_BY_NAME = 2;
    private final int FIND_BY_ID = 3;
    private final int FIND_ALL = 4;

    public void start() {
        while (true) {
            userManagementScreen();
            int selection = inputInteger(">>> ");
            switch (selection) {
                case 1:
                    showFoundUserData();
                    break;
                case 2:
                    deleteUserData();
                    break;
                case 3:
                    showUserGrade();
                    break;
                case 4:
                    (new ReviewService()).start();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("### 메뉴를 다시 입력하세요.");
            }
        }
    }

    private String inputUserType() {
        System.out.println("사용자 유형");
        System.out.println("1. 고객");
        System.out.println("2. 라이더");
        System.out.println("3. 점주");
        while (true) {
            int userTypeNum = inputInteger(">>> ");
            if (userTypeNum == 1) {
                return "고객";
            } else if (userTypeNum == 2) {
                return "라이더";
            } else if (userTypeNum == 3) {
                return "점주";
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        }
    }

    public void insertUserData() {
        System.out.println("===== 회원가입을 진행합니다.=====");

        String userName = inputString("회원 이름: ");
        String userId = inputString("회원 아이디: ");
        String userPassword = inputString("회원 비밀번호: ");
        String userAddress = inputString("주소: ");
        String phoneNumber = inputString("전화번호: ");
        String userType = inputUserType();
        Grade userGrade = Grade.BRONZE;
        String active = "Y";

        userRepository.addUser(
                new User(
                        -1,
                        userName,
                        userId,
                        userPassword,
                        userAddress,
                        phoneNumber,
                        userType,
                        userGrade,
                        active
                )
        );
        System.out.printf("## [%s] 님의 [%s] 계정이 정상적으로 생성되었습니다.\n", userName, userId);
    }

    private List<User> findUserData() {
        System.out.println("유저를 검색합니다.");
        System.out.println("[1. 회원 번호로 검색 | 2. 회원 이름으로 검색 | 3. 아이디로 검색 | 4. 전체검색]");

        int selection = inputInteger(">>> ");
        int condition = FIND_ALL;
        String keyword = "";

        switch (selection) {
            case 1:
                condition = FIND_BY_NUM;
                System.out.println("회원 번호로 검색합니다.");
                keyword = inputString("검색어: ");
                break;
            case 2:
                condition = FIND_BY_NAME;
                System.out.println("이름으로 검색합니다.");
                keyword = inputString("검색어: ");
                break;
            case 3:
                condition = FIND_BY_ID;
                System.out.println("아이디로 검색합니다.");
                keyword = inputString("검색어: ");
                break;
            case 4:
                System.out.println("전체 유저를 검색합니다.");
                break;
            default:
        }

        try {
            return userRepository.findUsers(condition, keyword);
        } catch (Exception e) {
            System.out.println("검색 중 오류가 발생했습니다: " + e.getMessage());
            return List.of(); // 빈 리스트 반환 또는 예외 처리
        }
    }

    private void showFoundUserData() {
        List<User> users = findUserData();
        int count = users.size();
        if (count > 0) {
            System.out.printf("\n========== 검색 결과 %d개 ==========\n", count);
            for (User user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("\n## 검색된 회원이 없습니다.");
        }
    }

    public void deleteUserData() {
        System.out.println("정말 탈퇴를 하시겠습니까?");
        System.out.println("탈퇴를 하시려면 '탈퇴' 를 입력해주세요.");

        String delAnswer = inputString(">>> ");
        if (delAnswer.equals("탈퇴")) {
            userRepository.deleteUser(Main.user.getUserNum());
            System.out.println("유저 " + Main.user.getUserName() + "님 회원탈퇴 완료되었습니다.");
        }

    }

    public void showUserGrade(){
        System.out.println("--- 회원 정보 ---");
        System.out.println("현재 회원님의 등급: " + Main.user.getUserGrade());
        System.out.println("총 주문 금액: " + Main.user.getTotalPaying() + "원");
        System.out.println("-----------------");
    }

    public int getTotalPrice() {
        User currentUser = Main.getCurrentUser();
        if (currentUser == null) {
            System.out.println("로그인된 유저가 없습니다.");
            return 0;
        }

        // 1. MenuRepository에서 로그인한 유저가 주문한 메뉴 목록을 가져옵니다.
        List<Menu> orderedMenuList;
        try {
            orderedMenuList = menuRepository.findOrderedMenusByUserNum(currentUser.getUserNum());
        } catch (Exception e) {
            System.err.println("주문 내역 조회 중 오류 발생: " + e.getMessage());
            return 0;
        }

        if (orderedMenuList == null || orderedMenuList.isEmpty()) {
            System.out.println("주문 내역이 없습니다.");
            return 0;
        }

        int totalPrice = 0;

        // 2. 받아온 menuList에 있는 모든 가격을 합합니다.
        for (Menu menu : orderedMenuList) {
            totalPrice += menu.getPrice();
        }

        // 3. totalPrice를 계산하고 grade를 업데이트합니다.
        currentUser.setTotalPaying(totalPrice);

        System.out.println("총 주문 금액: " + currentUser.getTotalPaying() + "원");
        return Main.getCurrentUser().getTotalPaying();
    }
}