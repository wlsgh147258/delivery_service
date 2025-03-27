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

import java.util.ArrayList;
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
                    updateUserData();
                    break;
                case 3:
                    getTotalPrice();
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
        if(userRepository.findUserById(userId) == null) {
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
        else {
            System.out.println("이미 있는 아이디 입니다.");
        }
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

    public void updateUserData() {

        try {
            //회원의 어떤 정보를 수정할지 선택
            userUpdateScreen();
            System.out.println("### 수정할 정보의 번호를 입력하세요.");
            int updateSelection = inputInteger(">>> ");

            switch (updateSelection) {
                case 1, 2, 3, 4, 5:
                    updateProcess(updateSelection, Main.user);
                    break;
                case 6:
                    System.out.println("정말 탈퇴를 하시겠습니까?");
                    System.out.println("탈퇴를 하시려면 '탈퇴' 를 입력해주세요.");

                    String delAnswer = inputString(">>> ");
                    if (delAnswer.equals("탈퇴")) {
                        userRepository.deleteUser(Main.user.getUserNum());
                        System.out.println("유저 " + Main.user.getUserName() + "님 회원탈퇴 완료되었습니다.");
                    } else {
                        System.out.println("회원 탈퇴가 되지 않았습니다.");
                    }
                    break;
                default:
                    System.out.println("### 메뉴를 다시 입력해주세요.");
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 수정 프로세스
    private void updateProcess(int updateSelection, User user) {

        try {
            String column = "";
            String newValue = "";

            switch (updateSelection) {
                case 1:
                    column = "user_name";
                    System.out.printf("회원의 기존 이름: %s >> 새로운 이름: ", Main.user.getUserName());
                    newValue = inputString(" ");
                    break;
                case 2:
                    column = "user_id";
                    System.out.printf("회원의 기존 아이디: %s >> 새로운 아이디: ", Main.user.getUserId());
                    newValue = inputString(" ");
                    break;
                case 3:
                    column = "user_password";
                    System.out.printf("회원의 기존 비밀번호: %s >> 새로운 비밀번호: ", Main.user.getUserPassword());
                    newValue = inputString(" ");
                    break;
                case 4:
                    column = "address";
                    System.out.printf("회원의 기존 주소: %s >> 새로운 주소: ", Main.user.getAddress());
                    newValue = inputString(" ");
                    break;
                case 5:
                    column = "phone_number";
                    System.out.printf("회원의 기존 전화번호: %s >> 새로운 전화번호: ", Main.user.getPhoneNumber());
                    newValue = inputString(" ");
                    break;
                default:
                    System.out.println("### 잘못된 입력입니다.");
                    return;
            }

            // DB 업데이트 실행
            userRepository.updateUserInfo(Main.user.getUserNum(), column, newValue);
            System.out.printf("\n###[ %s ] 회원의 [ %s ] 정보가 성공적으로 수정되었습니다.\n", Main.user.getUserName(), column);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getTotalPrice() {
        User currentUser = Main.getCurrentUser();
        if (currentUser == null) {
            System.out.println("로그인된 유저가 없습니다.");
            return 0;
        }

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

        int totalPrice = 0; // 초기화 위치 변경

        for (Menu menu : orderedMenuList) {
            totalPrice += menu.getPrice();
        }

        currentUser.setTotalPaying(totalPrice);

        System.out.println("--- 회원 정보 ---");
        if (currentUser != null) {
            System.out.println("현재 회원님의 등급: " + currentUser.getUserGrade());
            System.out.println("총 주문 금액: " + currentUser.getTotalPaying() + "원");
        } else {
            System.out.println("로그인된 유저가 없습니다.");
        }
        System.out.println("-----------------");
        return currentUser.getTotalPaying();
    }

}




