package delivery.order.repository;

import delivery.jdbc.DBConnectionManager;
import delivery.order.domain.Order;

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
}
