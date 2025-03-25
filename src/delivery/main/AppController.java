package delivery.main;

import delivery.common.DeliveryService;
import delivery.restaurants.service.RestaurantsService;
import delivery.user.domain.User;
import delivery.user.service.UserService;

public class AppController {
    private DeliveryService service;

    // 선택한 메뉴에 따라 시스템을 정해주는 기능
    public void chooseSystem(int selectNumber) {
        switch (selectNumber) {
            case 1:
                service = new UserService();
                break;
            case 2:
                service = new RiderService();
                break;
            case 3:
                service = new RestaurantsService();
                break;
            case 4:
                System.out.println("# 프로그램을 종료합니다.");
                System.exit(0);
            default:
                System.out.println("# 존재하지 않는 메뉴입니다.");
        }

        try {
            service.start();
        } catch (Exception e) {
            System.out.println("# 메뉴를 다시 입력하세요!");
        }

    }

}
