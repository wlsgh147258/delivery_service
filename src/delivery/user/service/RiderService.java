package delivery.user.service;

import delivery.common.DeliveryService;
import delivery.jdbc.DBConnectionManager;
import delivery.main.Main;
import delivery.order.domain.Order;
import delivery.user.domain.User;
import delivery.user.repository.UserRepository;
import delivery.user.service.UserService;

import static delivery.ui.AppUi.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RiderService implements DeliveryService {
    private final UserRepository userRepository = new UserRepository();
    private final UserService userservice = new UserService();

    private final int FIND_BY_NUM = 1;
    private final int FIND_BY_NAME = 2;
    private final int FIND_BY_ID = 3;
    private final int FIND_ALL = 4;


    @Override
    public void start() {
        while (true) {
            riderManagementScreen();
            int selection = inputInteger(">>> ");

            switch (selection) {
                case 1:
                    getDelivery();
                    break;
                case 2:
                    completeDelivery();
                    break;
                case 3:
                    showFoundUserData();
                    break;
                case 4:
                    userservice.updateUserData();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("### 메뉴를 다시 입력하세요.");
            }
        }

    }

    private void completeDelivery() {
        List<Order> orders = findCompleteData();

        int count = orders.size();
        if (count > 0) {
            System.out.printf("\n========== %s님이 배달중인 주문 %d개 ==========\n",Main.user.getUserName(), count);
            List<Integer> deliveryNums = new ArrayList<>();
            for (Order order : orders) {
                System.out.println(order);
                deliveryNums.add(order.getOrderNum());
            }
            System.out.printf("\n%s님이 배달 완료한 주문 번호를 입력해 주세요.\n", Main.user.getUserName());
            int okdelivery_no = inputInteger(">>> ");
            if (deliveryNums.contains(okdelivery_no)) {
                completeOrderDelivery(okdelivery_no);
            } else {
                System.out.println("\n존재하는 주문 번호를 입력해 주세요.");
            }

        } else {
            System.out.println("\n## 완료 체크할 주문이 없습니다.");
        }

    }



    private void completeOrderDelivery(int okdeliveryNo) {
        String sql = "UPDATE order_info SET ride_yn = 'Y' WHERE order_num = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, okdeliveryNo);
            pstmt.executeUpdate();
            System.out.printf("%d 번 주문을 배달 완료하였습니다.\n", okdeliveryNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private List<Order> findCompleteData() {
        System.out.println("\n배달중인 주문 검색");

        try {
            return userRepository.findOrdersComplete();
        } catch (Exception e) {
            System.out.println("완료주문 검색 중 오류가 발생했습니다: " + e.getMessage());
            return List.of(); // 빈 리스트 반환 또는 예외 처리
        }
    }


    private List<Order> findOrderData() {
        System.out.println("\n대기중인 주문 검색");

        try {
            return userRepository.findOrders();
        } catch (Exception e) {
            System.out.println("주문 검색 중 오류가 발생했습니다: " + e.getMessage());
            return List.of(); // 빈 리스트 반환 또는 예외 처리
        }
    }

    private void getDelivery() {
        List<Order> orders = findOrderData();
        int count = orders.size();
        if(count > 0) {
            System.out.printf("\n========== 검색 결과 %d개 ==========\n", count);
            List<Integer> ordersNums = new ArrayList<>();

            for(Order order: orders) {
                System.out.println(order);
                ordersNums.add(order.getOrderNum());
            }

            System.out.println("\n배달을 하시겠습니까?");
            System.out.println("1. 네 | 2. 아니요");
            int ans = inputInteger(">>> ");
            if(ans == 1){

                System.out.println("\n담당할 주문 번호를 입력해 주세요.");

                int getdelivery_no = inputInteger(">>> ");

                if(ordersNums.contains(getdelivery_no)){

                    getOrderDelivery(getdelivery_no);

                }else {
                    System.out.println("\n존재하는 주문 번호를 입력해 주세요.");
                }

            } else if (ans == 2) {
                System.out.println("라이더 관리 시스템 화면으로 돌아갑니다.");
            } else System.out.println("올바르지 않은 답변입니다.");

        } else {
            System.out.println("\n## 대기 중인 주문이 없습니다.");
        }

    }

    private void getOrderDelivery(int getdeliveryNo) {
        String sql = "UPDATE order_info SET ride_yn = '~', rider_num =? WHERE order_num = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Main.user.getUserNum());
            pstmt.setInt(2, getdeliveryNo);
            pstmt.executeUpdate();
            System.out.printf("%d 번 주문을 배달 시작하였습니다.\n", getdeliveryNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private List<User> findRiderData() {
        System.out.println("라이더를 검색합니다.");
        System.out.println("[1. 회원등록 번호로 검색 | 2. 회원등록 이름으로 검색 | 3. 아이디로 검색 | 4. 전체검색]");

        int selection = inputInteger(">>> ");
        int condition = FIND_ALL;
        String keyword = "";

        switch (selection) {
            case 1:
                condition = FIND_BY_NUM;
                System.out.println("회원등록 번호로 검색합니다.");
                keyword = inputString("검색어: ");
                break;
            case 2:
                condition = FIND_BY_NAME;
                System.out.println("이름으로 검색합니다.");
                keyword = inputString("검색어: ");
                break;
            case 3:
                condition = FIND_BY_ID;
                System.out.println("아이디로 검색합니다.");
                keyword = inputString("검색어: ");
                break;
            case 4:
                System.out.println("전체 라이더를 검색합니다.");
                break;
            default:
        }

        try {
            return userRepository.findRiderUsers(condition, keyword);
        } catch (Exception e) {
            System.out.println("검색 중 오류가 발생했습니다: " + e.getMessage());
            return List.of(); // 빈 리스트 반환 또는 예외 처리
        }
    }

    private void showFoundUserData() {
        List<User> riders = findRiderData();
        int count = riders.size();
        if(count > 0) {
            System.out.printf("\n========== 검색 결과 %d개 ==========\n", count);
            for(User rider: riders) {
                System.out.println(rider);
            }

        } else {
            System.out.println("\n## 찾으시는 라이더가 없습니다.");
        }

    }

}
