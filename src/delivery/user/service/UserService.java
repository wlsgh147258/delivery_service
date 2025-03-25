package delivery.user.service;

import static delivery.ui.AppUi.*;

import delivery.user.domain.Grade;
import delivery.user.domain.User;
import delivery.user.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

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
                    insertUserData();
                    break;
                case 2:
                    showFoundUserData();
                    break;
                case 3:
                    deleteUserData();
                    break;
                case 4:
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
        while(true) {
            int userTypeNum = inputInteger(">>> ");
            if(userTypeNum == 1) {
                return "고객";
            } else if(userTypeNum == 2) {
                return "라이더";
            } else if(userTypeNum == 3) {
                return "점주";
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        }
    }

    private void insertUserData() {
        System.out.println("===== 회원가입을 진행합니다. =====");
        String userName = inputString("주문번호: ");
        String userId = inputString("평점: ");
        String userPassword = inputString("내용: ");
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
        System.out.printf("## [%s] 님의 [%s] 계정이 정상적으로 생성되었습니다.", userName, userId);
    }

    private List<User> findUserData() {
        System.out.println("유저를 검색합니다.");
        int selection = inputInteger(">>> ");
        int condition = FIND_ALL;

        switch (selection) {
            case 1: // 회원 번호
                condition = FIND_BY_ID;
                System.out.println("회원 번호로 검색합니다.");
                break;
            case 2: // 이름
                condition = FIND_BY_NAME;
                System.out.println("이름으로 검색합니다.");
                break;
            case 3: // 아이디
                condition = FIND_BY_ID;
                System.out.println("아이디로 검색합니다.");
                break;
            case 4: // 전체
                System.out.println("전체 유저를 검색합니다.");
            default:
        }

        String keyword = "";
        if(condition != FIND_ALL) {
            keyword = inputString("검색어: ");
        }

        return userRepository.findUsers(condition, keyword);
    }
    private void showFoundUserData() {
        List<User> users = findUserData();
        int count = users.size();
        if(count > 0) {
            System.out.printf("\n========== 검색 결과 %d개 ==========", count);
            for(User user: users) {
                System.out.println(user);
            }
        } else {
            System.out.println("\n## 검색된 리뷰가 없습니다.");
        }
    }

    private void deleteUserData() {
        System.out.println("탈퇴를 위한 유저 검색을 시작합니다.");
        List<User> users = findUserData();

        if(users.size() > 0) {
            System.out.println("탈퇴할 유저 번호를 입력하세요.");
            int delUserNum = inputInteger(">>> ");

            if(users.stream().anyMatch(user -> user.getUserNum() == delUserNum)) {
                userRepository.deleteUser(delUserNum);
                System.out.println("유저번호 " + delUserNum + "번 회원탈퇴 완료.");
            } else {
                System.out.println("검색된 유저 번호를 입력해주세요.");
            }
        } else {
            System.out.println("조회 결과가 없습니다.");
        }

    }
}
