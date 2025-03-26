package delivery.main;

import delivery.user.domain.User;
import delivery.user.service.UserService;

import static delivery.ui.AppUi.*;
import static delivery.user.repository.UserRepository.findUserOne;

public class Main {

    public static User user;

    public static void main(String[] args) {
        int userType = 0;
        AppController controller = new AppController();
        UserService userservice = new UserService();

        while (true) {
            System.out.println("\n========= 음식 배달 시스템 =========");
            System.out.println("### 1. 로그인");
            System.out.println("### 2. 회원가입");
            int initnum = inputInteger(">>> ");

            if (initnum == 1) {
                System.out.println("### 아이디를 입력하세요.");
                String userid = inputString(">>> ");
                System.out.println("### 비밀번호를 입력하세요.");
                String userpw = inputString(">>> ");

                user = findUserOne(userid, userpw);

                if (user.getUserType().equals("고객")) {
                    userType = 1;
                } else if (user.getUserType().equals("라이더")) {
                    userType = 2;
                } else if (user.getUserType().equals("점주")) {
                    userType = 3;
                }

            } else if (initnum == 2){
                // 회원가입
                userservice.insertUserData();
            } else continue;

            controller.chooseSystem(userType);
        }


    }
}