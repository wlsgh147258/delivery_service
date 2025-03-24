package delivery.order.repository;

import delivery.jdbc.DBConnectionManager;
import delivery.order.domain.Order;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    public void addOrder(Order order) {
        String sql = "INSERT INTO order_info_seq VALUES(order_info_seq.NEXTVAL, ?, ?, ?, ?, ?)";
        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getUserNum());
            pstmt.setInt(2, order.getRestaurantNum());
            pstmt.setInt(3, order.getMenuNum());
            pstmt.setString(4, "N");
            pstmt.setString(5, "credit_card");

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> findByUserNum(int userNum) {

        List<Order> orderList = new ArrayList<>();
        // JOIN 문 이용
        String sql = "SELECT * FROM reviews r " +
                "INNER JOIN (SELECT o.order_num FROM order_info o WHERE o.user_num = ?) orn " +
                "ON r.order_num = orn.order_num";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Order review = new Order(
                        rs.getInt("order_num"),
                        rs.getInt("user_num"),
                        rs.getInt("restaurant_num"),
                        rs.getInt("menu_num"),
                        rs.getString("ride_yn"),
                        rs.getString("content")
                );

                orderList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public void deleteOrder(int delOrderNum) {
        String sql = "DELETE FROM order_info WHERE order_num = ?";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delOrderNum);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 주문 번호를 통해
    public List<Order> findOrderByNumber(int orderNum) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT o.*, u.user_name, u.phone_number, u.user_grade, " +
                "r.restaurant_name, r.phone_number AS restaurant_phone, " +
                "m.menu_name, m.price " +
                "FROM order_info o " +
                "JOIN users_info u ON o.user_num = u.user_num " +
                "JOIN restaurants r ON o.restaurant_num = r.restaurant_num " +
                "JOIN menu_info m ON o.menu_num = m.menu_num " +
                "WHERE o.order_num = ?";

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
                        rs.getInt("restaurantNum"),
                        rs.getInt("menuNum"),
                        rs.getString("ride_yn"),
                        rs.getString("payment_info")
                );
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
