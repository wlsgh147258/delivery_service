package delivery.order.service;

import delivery.common.Condition;
import delivery.common.DeliveryService;
import delivery.main.Main;
import delivery.menu.repository.MenuRepository;
import delivery.order.domain.Order;
import delivery.menu.domain.Menu;
import delivery.order.repository.OrderRepository;
import delivery.restaurants.repository.RestaurantsRepository;
import delivery.review.repository.ReviewRepository;
import delivery.user.domain.User;
import delivery.user.repository.UserRepository;
import delivery.user.service.UserService;

import static delivery.ui.AppUi.*;

import java.util.*;
import java.util.List;

public class OrderService implements DeliveryService {
    private final UserRepository userRepository = new UserRepository();
    private final OrderRepository orderRepository = new OrderRepository();
    private final UserService userService = new UserService();
    private final MenuRepository menuRepository = new MenuRepository();


    private static final int KOREAN_FOOD = 1;
    private static final int CHINESE_FOOD = 2;
    private static final int WESTERN_FOOD = 3;
    private static final int SNACK_FOOD = 4;
    private static final int FAST_FOOD = 5;
    private static final int DESSERT = 6;
    private static final int PREVIOUS_MENU = 7;

    @Override
    public void start() { // start를 받아옴
        while (true) {
            orderManagementScreen();
            int selection = inputInteger(">>> ");
            int userNum = Main.user.getUserNum();

            switch (selection) {
                case 1:
                    processOrderMenu(); // 메뉴
                    break;
                case 2:
                    processReturnMenu();
                    break;
                case 3:
                    findOrderMenu(userNum);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\n### 메뉴를 다시 입력하세요.");
            }
        }
    }

    private void processOrderMenu() { // 오더 메뉴
        while (true) {
            System.out.println("\n============================ 음식 주문 시스템을 실행합니다. ============================");
            System.out.println("[ 1. 한식 | 2. 중식 | 3. 양식 | 4. 분식 | 5. 패스트 푸드 | 6. 후식 | 7.이전으로 돌아가기 ]");
            int selection = inputInteger(">>>");

            switch (selection) {
                case KOREAN_FOOD:
                    showFoodList("한식");
                    break;
                case CHINESE_FOOD:
                    showFoodList("중식");
                    break;
                case WESTERN_FOOD:
                    showFoodList("양식");
                    break;
                case SNACK_FOOD:
                    showFoodList("분식");
                    break;
                case FAST_FOOD:
                    showFoodList("패스트 푸드");
                    break;
                case DESSERT:
                    showFoodList("후식");
                    break;
                case PREVIOUS_MENU:
                    return;
                default:
                    System.out.println("\n### 메뉴를 다시 입력하세요.");
            }
        }
    }

    // 카테고리 안의 음식 보여주는 메서드
    public void showFoodList(String category) {
        try {
            List<Menu> menuList = menuRepository.searchMenuList(Condition.CATEGORY, category);
            int count = menuList.size();

            List<Integer> menuNum = new ArrayList<>();
            if (count > 0) {
                for (Menu menu : menuList) {
                    System.out.println(menu.getMenu_num()+ " " + menu.getMenu_name() + " | " + menu.getCategory() + " - " + menu.getPrice());
                    menuNum.add(menu.getMenu_num());
                }
                System.out.println("==========================================================================================");
                System.out.println("### 주문할 음식의 번호를 입력하세요.");
                int foodNumber = inputInteger(">>> "); // 주문할 음식의 번호라 foodNumber로 지정

                if (menuNum.contains(foodNumber)) {
                    Menu selectedMenu = findMenuByNumber(menuList, foodNumber);
                    if (selectedMenu != null) {
                        processOrder(selectedMenu);
                    } else {
                        System.out.println("### 잘못된 음식 번호입니다.");
                    }
                } else {
                    System.out.println("### 잘못된 음식 번호입니다.");
                }
            } else {
                System.out.println("### 해당 카테고리에 음식이 없습니다.");
            }
        }
        catch (Exception e){
            System.out.println("### 음식 목록을 가져오는 중 오류가 발생했습니다: ");
            e.printStackTrace();
        }
    }

    private Menu findMenuByNumber(List<Menu> menuList, int foodNumber) {
        for (Menu menu : menuList) {
            if (menu.getMenu_num() == foodNumber) {
                return menu;
            }
        }
        return null;
    }

    private void processOrder(Menu menu) {
        System.out.println(menu.getMenu_name() + "을(를) 주문합니다.");
        User currentUser = Main.getCurrentUser();
        Order order = new Order(
                menu.getMenu_num(),
                Main.user.getUserNum(),
                menu.getRestaurantNum(),
                menu.getMenu_num(),
                "N",
                "credit_card"

        );
        orderRepository.addOrder(order, menu.getPrice());
        System.out.println("주문이 완료되었습니다.");
    }



    private void processReturnMenu() {
        System.out.println("\n============================ 주문 취소 시스템을 실행합니다. ============================");
        System.out.println("### 취소할 주문 번호를 입력해주세요.");
        int orderNumber = inputInteger(">>> ");

        List<Order> orderList = orderRepository.findOrderByNumber(orderNumber);

        if (orderList.isEmpty()) {
            System.out.println("### 해당 주문 번호로 주문 정보를 찾을 수 없습니다.");
            return;
        }

        Order order = orderList.get(0); // 주문 번호는 고유하므로 첫 번째 요소 사용

        // 주문 취소 가능 여부 확인 (예: 주문 상태, 배송 상태 등) 만들지는 모름
        if (!isOrderCancelable(order)) {
            System.out.println("### 해당 주문은 취소할 수 없습니다.");
            return;
        }
        // 주문 취소 처리
        orderRepository.deleteOrder(orderNumber);

        System.out.println("### 주문이 취소되었습니다.");

    }
    // 주문 취소 가능 여부 확인 (가상의 메서드)
    private boolean isOrderCancelable(Order order) {
        // 주문 상태, 배송 상태 등을 확인하여 주문 취소 가능 여부를 반환
        // 실제 구현에서는 데이터베이스에서 주문 상태, 배송 상태 등을 조회하여 확인해야 함
        return true; // 예시: 항상 true 반환
    }
    public List<Order> findOrderMenu(int userNum) {
        List<Order> orderList = orderRepository.findOrderMenu(userNum);

        if (orderList.isEmpty()) {
            System.out.println("### 주문 내역이 없습니다.");
        } else {
            System.out.println("\n============================ 주문 내역 ============================");
            for (Order order : orderList) {
                System.out.println("주문 번호: " + order.getOrderNum() +
                        ", 메뉴 번호: " + order.getMenuNum() +
                        ", 고객 번호: " + order.getUserNum() +
                        ", 가게 번호: " + order.getRestaurantNum() +
                        ", 배달 기사: " + order.getRideYN() +
                        ", 결제 정보: " + order.getPaymentInfo());
            }
        }
        return orderList;
    }
}

