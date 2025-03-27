package delivery.restaurants.service;

import delivery.common.DeliveryService;
import delivery.jdbc.DBConnectionManager;
import delivery.main.Main;
import delivery.menu.service.MenuService;
import delivery.order.domain.Order;
import delivery.order.repository.OrderRepository;
import delivery.restaurants.domain.Restaurants;
import delivery.restaurants.repository.RestaurantsRepository;
import delivery.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static delivery.ui.AppUi.*;

public class RestaurantsService implements DeliveryService {

    private final RestaurantsRepository restaurantsRepository = new RestaurantsRepository();

    @Override
    public void start() {

        while (true) {

            //로그인 로직 추가하기
            int userNum = Main.user.getUserNum();

            restaurantManagementScreen();
            int num = inputInteger(">>> ");

            switch (num) {
                case 1:
                    //내가 운영하는 식당들의 들어온 주문 확인하기
                    getCook(userNum);
                    break;
                case 2:
                    completeCook(userNum);
                    break;
                case 3:
                    insertRestaurant(userNum);
                    break;
                case 4:
                    searchRestaurant(userNum);
                    break;
                case 5:
                    updateRestaurant(userNum);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("### 메뉴를 다시 입력해주세요.");
            }
        }
    }


    //식당 정보 추가
    private void insertRestaurant(int userNum) {

        System.out.println("====== 식당 정보를 추가합니다 =====");
        String store_name = inputString("# 식당명: ");
        String category = inputString("# 한식 | 중식 | 양식 | 분식 | 패스트 푸드 | 후식 : ");
        String open_hours = inputString("# 영업 시간: ");
        String call_number = inputString("# 식당 전화 번호: ");
        String delivery_area = inputString("# 식당 주소: ");
        String detail_info = inputString("# 식당 소개: ");


        Restaurants restaurants = new Restaurants(userNum, store_name, category, open_hours, call_number, delivery_area, detail_info);

        restaurantsRepository.insertRestaurant(restaurants);

        System.out.printf("### [%s] 식당 정보가 정상적으로 추가되었습니다.", restaurants.getStore_name());
    }


    //기존 식당 정보 수정
    private void updateRestaurant(int userNum) {

        try {
            //메서드 실행되면 바로 운영중인 식당 리스트 보여주고 수정할 식당 선택
            List<Restaurants> restaurantsList = restaurantsRepository.searchRestaurantByOwner(userNum);
            String userName = Main.user.getUserName();

            if (restaurantsList.size() > 0) {
                System.out.printf("\n================== [ %s ] 님의 restaurant 검색 결과 (총 %d건)=====================\n", userName, restaurantsList.size());
                List<Integer> storeNums = new ArrayList<>();
                for (Restaurants restaurant : restaurantsList) {
                    //운영중인 식당 리스트 보여주기
                    System.out.println(restaurant);
                    storeNums.add(restaurant.getStore_num());
                }
                System.out.println("\n### 수정할 식당의 번호를 입력하세요. ");
                int updateRestaNum = inputInteger(">>> ");

                if (storeNums.contains(updateRestaNum)) {

                    //어떤 정보를 수정할지 선택
                    restaurantUpdateScreen();
                    int updateSelection = inputInteger(">>> ");

                    switch (updateSelection) {
                        case 1:
                            //메뉴 관리 시스템 실행
                            for (Restaurants restaurants : restaurantsList) {
                                if (restaurants.getStore_num() == updateRestaNum) {
                                    MenuService menuService = new MenuService();
                                    menuService.menu(restaurants);
                                    break;
                                }
                            }
                            break;

                        case 2, 3, 4, 5, 6, 7:

                            for (Restaurants restaurant : restaurantsList) {
                                if (restaurant.getStore_num() == updateRestaNum) {
                                    //수정 프로세스 진행
                                    updateProcess(updateSelection, restaurant);
                                    break;
                                }
                            }

                            break;
                        case 8:
                            if (storeNums.contains(updateRestaNum)) {
                                restaurantsRepository.deleteRestaurant(updateRestaNum);

                                for (Restaurants restaurant : restaurantsList) {
                                    if (restaurant.getStore_num() == updateRestaNum) {

                                        System.out.printf("\n### [ %d 번 ] [ %s ] 식당 정보를 정상 삭제하였습니다. \n",
                                                restaurant.getStore_num(), restaurant.getStore_name());
                                        break;
                                    }
                                }
                            } else {
                                System.out.println("\n### 알맞은 식당 번호만 삭제할 수 있습니다.");
                            }
                            break;
                        default:
                            System.out.println("### 메뉴를 다시 입력해주세요.");
                            break;
                    }
                } else {
                    System.out.println("\n### 잘못된 식당 입력입니다.");
                }


            } else {
                System.out.println("운영중인 식당이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 수정 프로세스
    private void updateProcess(int updateSelection, Restaurants restaurant) {

        try {

            String column = "";
            String newValue = "";

            switch (updateSelection) {
                case 2:
                    column = "restaurant_name";
                    System.out.printf("기존 식당 이름: %s >> 새로운 식당 이름: ", restaurant.getStore_name());
                    newValue = inputString(" ");
                    break;
                case 3:
                    column = "category";
                    System.out.printf("기존 카테고리: %s >> 새로운 카테고리: ", restaurant.getCategory());
                    newValue = inputString(" ");
                    break;
                case 4:
                    column = "opening_hours";
                    System.out.printf("기존 영업 시간: %s >> 새로운 영업 시간: ", restaurant.getOpen_hours());
                    newValue = inputString(" ");
                    break;
                case 5:
                    column = "phone_number";
                    System.out.printf("기존 식당 전화번호: %s >> 새로운 전화번호: ", restaurant.getCall_number());
                    newValue = inputString(" ");
                    break;
                case 6:
                    column = "delivery_area";
                    System.out.printf("기존 주소: %s >> 새로운 주소: ", restaurant.getDelivery_area());
                    newValue = inputString(" ");
                    break;
                case 7:
                    column = "detailed_info";
                    System.out.printf("기존 식당 상세 정보: %s >> 새로운 식당 상세 정보: ", restaurant.getDetail_info());
                    newValue = inputString(">> ");
                    break;
                default:
                    System.out.println("### 잘못된 입력입니다.");
                    return;
            }

            // DB 업데이트 실행
            restaurantsRepository.updateRestaurantInfo(restaurant.getStore_num(), column, newValue);
            System.out.printf("\n###[ %s ] 식당의 [ %s ] 정보가 성공적으로 수정되었습니다.\n", restaurant.getStore_name(), column);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 운영중인 식당 정보 출력
    private void searchRestaurant(int userNum) {

        try {
            List<Restaurants> restaurantsList = restaurantsRepository.searchRestaurantByOwner(userNum);

            int count = restaurantsList.size();

            String userName = Main.user.getUserName();

            if (count > 0) {
                System.out.printf("\n================== [ %s ] 님의 restaurant 검색 결과 (총 %d건)=====================\n", userName, restaurantsList.size());
                for (Restaurants restaurants : restaurantsList) {
                    System.out.println(restaurants);
                }
                System.out.println();
            } else {
                System.out.println("\n### 운영중인 식당이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //------------------------------------------------------------------------------------------------------

    //들어온 주문 리스트 확인하고 접수
    private void getCook(int userNum) {
        List<Order> orders = findOrderData(userNum);
        int count = orders.size();

        if (count > 0) {
            System.out.printf("\n========== 대기중인 주문 검색 결과 %d개 ==========\n", count);
            List<Integer> ordersNums = new ArrayList<>();

            for (Order order : orders) {
                System.out.println(order);
                ordersNums.add(order.getOrderNum());
            }

            System.out.println("\n주문을 접수 하시겠습니까?");
            System.out.println("1. 네 | 2. 아니요");
            int ans = inputInteger(">>> ");

            if (ans == 1) {
                System.out.println("\n담당할 주문 코드를 입력해 주세요.");
                int getOrderNum = inputInteger(">>> ");

                if (ordersNums.contains(getOrderNum)) {
                    getOrderCook(getOrderNum);

                } else {
                    System.out.println("\n존재하는 주문 코드를 입력해 주세요.");
                }

            } else if (ans == 2) {
                System.out.println("식당 관리 시스템 화면으로 돌아갑니다.");
            } else System.out.println("올바르지 않은 답변입니다.");
        } else {
            System.out.println("\n## 대기 중인 주문이 없습니다.");
        }

    }


    //주문 조리 시작
    private void getOrderCook(int getOrderNum) {
        String sql = "UPDATE order_info SET cook_yn = '~' WHERE order_num = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

//            pstmt.setInt(1, Main.user.getUserNum());
            pstmt.setInt(1, getOrderNum);
            pstmt.executeUpdate();
            System.out.printf("%d 번 주문 조리 시작하였습니다.\n", getOrderNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private List<Order> findOrderData(int userNum) {

        try {
            return findOrders(userNum);
        } catch (Exception e) {
            System.out.println("주문 검색 중 오류가 발생했습니다: " + e.getMessage());
            return List.of(); // 빈 리스트 반환 또는 예외 처리
        }

    }

    public List<Order> findOrders(int userNum) {
        List<Order> foundOrders = new ArrayList<>();
        String sql = "SELECT o.*, m.price FROM order_info o JOIN restaurants r ON o.restaurant_num = r.restaurant_num " +
                "JOIN users_info u ON u.user_num = r.user_num JOIN menu_info m ON o.menu_num = m.menu_num " +
                "WHERE o.cook_yn = 'N' AND u.user_num = ? " +
                "ORDER BY r.restaurant_num";
        PreparedStatement pstmt = null;

        try (Connection conn = DBConnectionManager.getConnection()) {
            pstmt = conn.prepareStatement(sql); // try 블록 안에서 PreparedStatement 생성

            pstmt.setInt(1, userNum);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("order_num"),
                            rs.getInt("user_num"),
                            rs.getInt("restaurant_num"),
                            rs.getInt("menu_num"),
                            rs.getString("ride_yn"),
                            rs.getString("payment_info"),
                            rs.getString("cook_yn")
                    );

                    order.setMenuPrice(rs.getInt("price"));
                    foundOrders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return foundOrders;
    }

    //조리 완료
    private void completeCook(int userNum) {
        List<Order> orders = findCompleteOrder();

        int count = orders.size();
        if (count > 0) {
            System.out.printf("\n========== %s님이 조리 중인 주문 %d개 ==========\n", Main.user.getUserName(), count);
            List<Integer> cookNums = new ArrayList<>();
            for (Order order : orders) {
                System.out.println(order);
                cookNums.add(order.getOrderNum());
            }
            System.out.printf("\n%s님이 조리 완료한 주문 번호를 입력해 주세요.\n", Main.user.getUserName());
            int okCookNum = inputInteger(">>> ");
            if (cookNums.contains(okCookNum)) {
                completeOrderCook(okCookNum);
            } else {
                System.out.println("\n존재하는 주문 번호를 입력해 주세요.");
            }

        } else {
            System.out.println("\n## 완료 체크할 주문이 없습니다.");
        }

    }

    private void completeOrderCook(int okCookNum) {
        String sql = "UPDATE order_info SET cook_yn = 'Y' WHERE order_num = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, okCookNum);
            pstmt.executeUpdate();
            System.out.printf("%d 번 주문을 조리 완료하였습니다.\n", okCookNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //조리중인 주문 리스트 출력
     private List<Order> findCompleteOrder() {
        System.out.println("\n조리중인 주문 검색");

        try {
            return restaurantsRepository.findOrdersComplete();
        } catch (Exception e) {
            System.out.println("조리중인 주문을 검색 중 오류가 발생했습니다: " + e.getMessage());
            return List.of(); // 빈 리스트 반환 또는 예외 처리
        }
    }


}