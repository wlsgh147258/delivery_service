package delivery.order.service;

import delivery.common.Condition;
import delivery.common.DeliveryService;
import delivery.main.Main;
import delivery.menu.repository.MenuRepository;
import delivery.menu.service.MenuService;
import delivery.order.domain.Order;
import delivery.menu.domain.Menu;
import delivery.order.repository.OrderRepository;
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
            System.out.println("[ 1. 이름검색 | 2. 가격검색 | 3. 카테고리검색 | 4. 전체검색 | 5. 이전으로 돌아가기]");
            int selection1 = inputInteger(">>> ");
            Condition condition = Condition.ALL;

            switch (selection1) {
                case 1:
                    System.out.println("\n## 이름으로 검색합니다.");
                    condition = Condition.MENU_NAME;
                    break;
                case 2:
                    System.out.println("\n## 가격으로 검색합니다.");
                    condition = Condition.PRICE;
                    break;
                case 3:
                    System.out.println("\n## 카테고리로 검색합니다.");
                    condition = Condition.CATEGORY;
                    break;
                case 4:
                    System.out.println("\n## 전체 정보를 검색합니다.");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\n### 해당 메뉴가 존재하지 않습니다. 전체 정보로 검색합니다.");
            }

            if (condition == Condition.PRICE) {
                int keyword = inputInteger("# 입력값 이하의 메뉴 검색: ");
                try {
                    List<Menu> menus = menuRepository.searchMenuList(condition, keyword);
                    getOrder(menus);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (condition == Condition.CATEGORY) {
                System.out.println("[ 1. 한식 | 2. 중식 | 3. 양식 | 4. 분식 | 5. 패스트 푸드 | 6. 후식 | 7.이전으로 돌아가기 ]");
                int selection = inputInteger(">>>");
                if (selectCategory(selection)) return;
            } else if (condition == Condition.MENU_NAME) {
                String keyword = inputString("# 검색어: ");
                try {
                    List<Menu> menus = menuRepository.searchMenuList(condition, keyword);
                    getOrder(menus);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                String keyword = "";
                try {
                    List<Menu> menus = menuRepository.searchMenuList(condition, keyword);
                    getOrder(menus);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private boolean selectCategory(int selection) {
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
                return true;
            default:
                System.out.println("\n### 메뉴를 다시 입력하세요.");
        }
        return false;
    }

    // 카테고리 안의 음식 보여주는 메서드
    public void showFoodList(String category) {
        try {
            List<Menu> menuList = menuRepository.searchMenuList(Condition.CATEGORY, category);
            getOrder(menuList);
        }
        catch (Exception e){
            System.out.println("### 음식 목록을 가져오는 중 오류가 발생했습니다: ");
            e.printStackTrace();
        }
    }

    private void getOrder(List<Menu> menuList) {
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
        System.out.println("결제수단을 선택해주세요");
        System.out.println("1. 현금결제 |  2. 카드결제");
        int paynum = inputInteger(">>> ");

        String payment;

        if (paynum==1) {
            payment = "cash";
        } else {
            payment = "credit_card";
        }

        User currentUser = Main.getCurrentUser();
        Order order = new Order(
                menu.getMenu_num(),
                Main.user.getUserNum(),
                menu.getRestaurantNum(),
                menu.getMenu_num(),
                "N",
                payment,
                "N"
        );
        orderRepository.addOrder(order, menu.getPrice());
        System.out.println("주문이 완료되었습니다.");
    }



    private void processReturnMenu() {
        System.out.println("\n============================ 주문 취소 시스템을 실행합니다. ============================");
        findOrderMenu(Main.user.getUserNum());
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
        String orderStatus = order.getRideYN(); // 현재 주문 상태 (예: 'N' - 주문 완료, '~' - 배송 중)

        if ("N".equals(orderStatus)) { // 주문 배달 전 상태
            return true; // 주문 완료 상태이므로 취소 가능
        } else if ("~".equals(orderStatus)) { // 배송 중 상태
            return false;
        } else if ("Y".equals(orderStatus)) { // 이미 취소된 상태
            return false;
        }
        return false; // 다른 상태의 경우 기본적으로 취소 불가
    }
    public List<Order> findOrderMenu(int userNum) {
        List<Order> orderList = orderRepository.findOrderByUserNum(userNum);

        if (orderList.isEmpty()) {
            System.out.println("### 주문 내역이 없습니다.");
        } else {
            System.out.println("\n============================ 주문 내역 ============================");
            for (Order order : orderList) {
                System.out.println("주문 코드: " + order.getOrderNum() +
                        ", 메뉴 번호: " + order.getMenuNum() +
                        ", 고객 번호: " + order.getUserNum() +
                        ", 가게 번호: " + order.getRestaurantNum() +
                        ", 조리 상태: " + order.getCookYN() +
                        ", 배달 상태: " + order.getRideYN() +
                        ", 결제 정보: " + order.getPaymentInfo() +
                        ", 금액: " + order.getMenuPrice());
            }
        }
        return orderList;
    }
}

