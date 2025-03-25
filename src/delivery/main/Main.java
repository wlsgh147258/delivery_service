package delivery.main;

import static delivery.ui.AppUi.*;
import static delivery.user.repository.UserRepository.findUserType;

public class Main {

    public static void main(String[] args) {
        int selectNumber = 0;
        AppController controller = new AppController();

        while (true) {
            System.out.println("\n========= 음식 배달 시스템 =========");
            System.out.println("### 로그인 ###");
            System.out.println("### 아이디를 입력하세요.");
            String userid = inputString(">>> ");
            System.out.println("### 비밀번호를 입력하세요.");
            String userpw = inputString(">>> ");

            String type = findUserType(userid,userpw);

            if (type.equals("고객")) {
                selectNumber = 1;
            } else if (type.equals("라이더")) {
                selectNumber = 2;
            } else if (type.equals("점주")) {
                selectNumber = 3;
            }

            controller.chooseSystem(selectNumber);
        }


    }
}
