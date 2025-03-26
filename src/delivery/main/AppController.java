package delivery.main;

import delivery.common.DeliveryService;
import delivery.order.service.OrderService;
import delivery.restaurants.service.RestaurantsService;
import delivery.review.service.ReviewService;
import delivery.user.service.*;

import static delivery.ui.AppUi.inputInteger;

public class AppController {
    private DeliveryService service;

    // 선택한 메뉴에 따라 시스템을 정해주는 기능
    public void chooseSystem(int userType) {

        int selectNumber;

        if (userType == 1) {
            System.out.println("[1. 사용자 관리 서비스 | 2. 주문 서비스 | 3. 프로그램 종료]");
            selectNumber = inputInteger(">>> ");
            switch (selectNumber) {
                case 1:
                    service = new UserService();
                    break;
                case 2:
                    service = new OrderService();
                    break;
                case 3:
                    System.out.println("# 프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("# 존재하지 않는 메뉴입니다.");
                    return;
            }
        } else if (userType == 2) {
            System.out.println("[1. 라이더 서비스 | 2. 프로그램 종료]"); // 종료 옵션 추가
            selectNumber = inputInteger(">>> ");
            switch (selectNumber) {
                case 1:
                    service = new RiderService();
                    break;
                case 2:
                    System.out.println("# 프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("# 존재하지 않는 메뉴입니다.");
                    return;
            }
        } else if (userType == 3) {
            System.out.println("[1. 식당 서비스 |2. 식당 리뷰 확인 | 3. 프로그램 종료]");
            selectNumber = inputInteger(">>> ");
            switch (selectNumber) {
                case 1:
                    service = new RestaurantsService();
                    break;
                case 2:
                    service = new ReviewService();
                    break;
                case 3:
                    System.out.println("# 프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("# 존재하지 않는 메뉴입니다.");
                    return;
            }
        }

        if (service != null) {
            try {
                service.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("# 서비스 시작 중 오류가 발생했습니다. 메뉴를 다시 입력하세요!");

            }
        }
    }
}