package delivery.main;

import delivery.restaurants.service.RestaurantsService;

public class Main {

    public static void main(String[] args) {
        RestaurantsService service = new RestaurantsService();

        service.start();


    }
}
