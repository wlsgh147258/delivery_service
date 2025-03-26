package delivery.main;

import delivery.common.DeliveryService;
import delivery.order.service.OrderService;
import delivery.restaurants.service.RestaurantsService;
import delivery.user.service.*;

import static delivery.ui.AppUi.inputInteger;

public class AppController {
    private DeliveryService service;

    // 선택한 메뉴에 따라 시스템을 정해주는 기능
    public void chooseSystem(int userType) {
        System.out.println("[1. 고객 서비스 | 2. 주문 서비스 | 3. 식당 서비스 | 4. 라이더 서비스 | 5. 프로그램 종료]");
        int selectNumber = inputInteger(">>> ");
        switch (selectNumber) {
            case 1:
                service = new UserService();
                break;
            case 2:
                service = new OrderService();
                break;
            case 3:
                service = new RestaurantsService();
                break;
            case 4:
                service = new RiderService();
                break;

            case 5:
                System.out.println("# 프로그램을 종료합니다.");
                System.exit(0);
            default:
                System.out.println("# 존재하지 않는 메뉴입니다.");
        }

        try {
            service.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("# 메뉴를 다시 입력하세요!");
        }

    }

}