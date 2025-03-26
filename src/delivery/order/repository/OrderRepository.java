package delivery.order.repository;

import delivery.jdbc.DBConnectionManager;
import delivery.main.Main;

import delivery.order.domain.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {


    public void addOrder(Order order, int pri) {
        String sql = "INSERT INTO order_info VALUES(order_info_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getUserNum());
            pstmt.setInt(2, order.getRestaurantNum());
            pstmt.setInt(3, order.getMenuNum());
            pstmt.setString(4, "N");
            pstmt.setString(5, order.getPaymentInfo());
            pstmt.setInt(6, 0);
            pstmt.setString(7, "N");


            pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Order> findOrderByUserNum(int userNum) {
        List<Order> orderList = new ArrayList<>();
        // JOIN 문 이용
        String sql = "SELECT o.*, m.price FROM order_info o INNER JOIN menu_info m ON o.menu_num = m.menu_num WHERE user_num = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userNum); // userNum 매개변수 설정
            ResultSet rs = pstmt.executeQuery();

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
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public void deleteOrder(int delOrderNum) {
        String sql = "DELETE FROM order_info WHERE order_num = ? AND user_num = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delOrderNum);
            pstmt.setInt(2, Main.user.getUserNum());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("주문이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 주문을 삭제할 권한이 없거나 주문 번호가 유효하지 않습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 주문 번호를 통해
    public List<Order> findOrderByNumber(int orderNum) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT o.*, m.price FROM order_info o INNER JOIN menu_info m ON o.menu_num = m.menu_num WHERE order_num = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderNum);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // User, Restaurant, Menu 객체를 생성하는 로직 추가
                // user, restaurant, menu num 을 가져오기 위해선 조회를 해야함.

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
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
