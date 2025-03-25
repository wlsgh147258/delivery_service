package delivery.main;

import delivery.restaurants.service.RestaurantsService;

import static delivery.ui.AppUi.*;

public class Main {

    public static void main(String[] args) {

        AppController controller = new AppController();

        while (true) {
            startScreen();
            int selectNumber = inputInteger(">>> ");
            controller.chooseSystem(selectNumber);
        }


    }
}
