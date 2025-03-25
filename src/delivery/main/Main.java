package delivery.main;

import delivery.ui.AppUi;

public class Main {
    public static void main(String[] args) {

        AppController controller = new AppController();

        while (true) {
            AppUi.startScreen();
            int selectNumber = AppUi.inputInteger(">>> ");
            controller.chooseSystem(selectNumber);
            if (selectNumber == 5){
                System.out.println("프로그램을 종료합니다.");
                break;
            }
        }
    }
}