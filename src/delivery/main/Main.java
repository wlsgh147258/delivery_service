package delivery.main;

import delivery.user.domain.User;
import delivery.user.service.UserService;

import static delivery.ui.AppUi.*;
import static delivery.user.repository.UserRepository.findUserOne;

public class Main {

    public static User user;
    public static int userTypeno = 0;
    public static AppController controller = new AppController();
    public static UserService userservice = new UserService();

    public static User getCurrentUser(){return Main.user;}
    public static void main(String[] args) {

        Main_run(userTypeno, userservice, controller);
    }

    public static void Main_run(int userTypeno, UserService userservice, AppController controller) {
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
                    userTypeno = 1;
                } else if (user.getUserType().equals("라이더")) {
                    userTypeno = 2;
                } else if (user.getUserType().equals("점주")) {
                    userTypeno = 3;
                }

            } else if (initnum == 2){
                // 회원가입
                userservice.insertUserData();
            } else continue;

            controller.chooseSystem(userTypeno);
        }


    }
}