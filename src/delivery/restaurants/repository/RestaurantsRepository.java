package delivery.restaurants.repository;

import delivery.common.Condition;
import delivery.jdbc.DBConnectionManager;
import delivery.main.Main;
import delivery.order.domain.Order;
import delivery.restaurants.domain.Restaurants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


import static delivery.common.Condition.*;

public class RestaurantsRepository {

    // 음식점 추가하기
    public void insertRestaurant(Restaurants resta) {
        String sql = """
                INSERT INTO restaurants
                      VALUES(store_info_seq.NEXTVAL, ?,?,?,?,?,?,?,?)""";

        try (Connection conn = delivery.jdbc.DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, resta.getStore_name());
            pstmt.setString(2, resta.getCall_number());
            pstmt.setString(3, resta.getOpen_hours());
            pstmt.setString(4, resta.getDetail_info());
            pstmt.setString(5, resta.getDelivery_area());
            pstmt.setString(6, "Y");
            pstmt.setInt(7, resta.getUser_num());
            pstmt.setString(8, resta.getCategory());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 음식점 찾기
    public List<Restaurants> searchRestaurantByCustomer(Condition condition, String keyword) throws Exception {
        List<Restaurants> searchList = new ArrayList<>();

        String sql = "SELECT * FROM restaurants WHERE active = 'Y'";
        if (condition == AREA) {
            sql += " AND delivery_area LIKE ?";
        } else if (condition == CATEGORY) {
            sql += " AND category LIKE ?";
        }  //else if (condition == TITLE) {
//            sql += " AND movies_name LIKE ?";
//        }
        sql += " ORDER BY restaurant_num";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (condition != ALL) {
                //  LIKE 사용시 %, _ 기호를 따옴표  안에 넣어줘야 한다
                // ? 열에 %를 쓰는게 아니라, ?를 채울 때 특정 단어에 %를 미리 세팅해서 채워야 함
                pstmt.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Restaurants resta = createStoreFromResultSet(rs);
                searchList.add(resta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchList;
    }

    public List<Restaurants> searchRestaurantByOwner(int userNum) throws Exception {
        List<Restaurants> searchList = new ArrayList<>();

        String sql = "SELECT * FROM restaurants WHERE active = 'Y' AND user_num = ?";

        sql += " ORDER BY restaurant_num";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userNum);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Restaurants resta = createStoreFromResultSet(rs);
                searchList.add(resta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchList;
    }


    // String store_name, String open_hours, String call_number, String delivery_area, String detail_info
    // ResultSet에서 추출한 결과를 Restaurant 객체로 포장해주는 헬터 메서드
    private static Restaurants createStoreFromResultSet(ResultSet rs) throws SQLException {
        Restaurants resta = new Restaurants(rs.getInt("user_num"),
                rs.getString("restaurant_name"),
                rs.getString("category"),
                rs.getString("opening_hours"),
                rs.getString("phone_number"),
                rs.getString("delivery_area"),
                rs.getString("detailed_info"));

        resta.setActive_flag(rs.getString("active"));
        resta.setStore_num(rs.getInt("restaurant_num"));
        return resta;
    }


    public void deleteRestaurant(int delRestaNum) {
        String sql = "UPDATE restaurants SET active = 'N' WHERE restaurant_num = ?";

        Connection conn = null;
        try (Connection conn1 = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn1.prepareStatement(sql)) {

            pstmt.setInt(1, delRestaNum);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // DB에서 특정 컬럼을 업데이트하는 메서드
    public void updateRestaurantInfo(int storeNum, String column, String newValue) {

        String sql = "UPDATE restaurants SET " + column + " = ? WHERE restaurant_num = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newValue);
            pstmt.setInt(2, storeNum);

            pstmt.executeUpdate();

            System.out.println("### 업데이트 성공!");

        } catch (SQLException e) {
            System.out.println("### 업데이트 실패. 입력을 다시 확인하세요!");
            e.printStackTrace();
        }
    }

    //조리중인 주문 db에서 조회
    public List<Order> findOrdersComplete() {
        List<Order> foundOrders = new ArrayList<>();
        String sql = "SELECT o.*, m.price FROM order_info o JOIN restaurants r ON o.restaurant_num = r.restaurant_num " +
                "JOIN users_info u ON u.user_num = r.user_num JOIN menu_info m ON o.menu_num = m.menu_num " +
                "WHERE o.cook_yn = '~' AND u.user_num = ?";
        ;
        PreparedStatement pstmt = null;

        try (Connection conn = DBConnectionManager.getConnection()) {
            pstmt = conn.prepareStatement(sql); // try 블록 안에서 PreparedStatement 생성
            pstmt.setInt(1, Main.user.getUserNum());

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

}