package delivery.main;

import delivery.restaurants.service.RestaurantsService;
import delivery.user.service.UserService;

import static delivery.ui.AppUi.*;
import static delivery.user.repository.UserRepository.findUserType;

public class Main {

    public static void main(String[] args) {
        int userType = 0;
        AppController controller = new AppController();
        UserService userservice =new UserService();

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

                String type = findUserType(userid,userpw);

                if (type.equals("고객")) {
                    userType = 1;
                } else if (type.equals("라이더")) {
                    userType = 2;
                } else if (type.equals("점주")) {
                    userType = 3;
                }

            } else {
                // 회원가입
                userservice.insertUserData();
            }

            controller.chooseSystem(userType);
        }


    }
}