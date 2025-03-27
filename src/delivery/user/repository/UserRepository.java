package delivery.user.repository;

import delivery.jdbc.DBConnectionManager;
import delivery.main.Main;
import delivery.order.domain.Order;
import delivery.user.domain.Grade;
import delivery.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserRepository {

    private static final Map<Integer, User> userDatabase = new HashMap<>();

    public void addUser(User user) {
        String sql = "INSERT INTO users_info" +
                "(user_num, user_name, user_id, user_password, address, phone_number, user_type, user_grade, active) " +
                "VALUES (users_info_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)"; // 열 이름 명시

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserId());
            pstmt.setString(3, user.getUserPassword());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getPhoneNumber());
            pstmt.setString(6, user.getUserType());
            pstmt.setString(7, user.getUserGrade().toString()); // Grade enum을 문자열로 변환
            pstmt.setString(8, user.getActive()); // 열 이름으로 설정

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> findUsers(int condition, String keyword) {
        List<User> foundUsers = new ArrayList<>();
        String sql = "SELECT * FROM users_info ";
        PreparedStatement pstmt = null;

        if (condition == 1) { // 회원 번호
            sql += " WHERE user_num = ? AND active ='Y' ";
        } else if (condition == 2) { // 이름
            sql += " WHERE user_name LIKE ? AND active ='Y' ";
        } else if (condition == 3) { // 아이디
            sql += " WHERE user_id = ? AND active ='Y' ";
        }

        try (Connection conn = DBConnectionManager.getConnection()) {
            pstmt = conn.prepareStatement(sql); // try 블록 안에서 PreparedStatement 생성

            if (condition == 1) { // 회원 번호
                pstmt.setInt(1, Integer.parseInt(keyword));
            } else if (condition == 2) { // 이름
                pstmt.setString(1, "%" + keyword + "%");
            } else if (condition == 3) { // 아이디
                pstmt.setString(1, keyword);
            }
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                foundUsers.add(new User(
                        rs.getInt("user_num"),
                        rs.getString("user_name"),
                        rs.getString("user_id"),
                        rs.getString("user_password"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("user_type"),
                        Grade.valueOf(rs.getString("user_grade")),
                        rs.getString("active")
                ));
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
        return foundUsers;
    }

    public List<Order> findOrders() {
        List<Order> foundOrders = new ArrayList<>();
        String sql = "SELECT o.*, m.price FROM order_info o INNER JOIN menu_info m ON o.menu_num = m.menu_num " +
                "WHERE ride_yn = 'N' AND cook_yn = 'Y'";
        PreparedStatement pstmt = null;

        try (Connection conn = DBConnectionManager.getConnection()) {
            pstmt = conn.prepareStatement(sql); // try 블록 안에서 PreparedStatement 생성

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

    public List<Order> findOrdersComplete() {
        List<Order> foundOrders = new ArrayList<>();
        String sql = "SELECT o.*, m.price FROM order_info o JOIN menu_info m ON o.menu_num = m.menu_num " +
                "WHERE ride_yn = '~' AND rider_num = ? ";
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


    public static User findUserOne(String id, String pw) {
        String sql = "SELECT * FROM users_info WHERE user_id = ? AND user_password = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("user_num"),
                        rs.getString("user_name"),
                        rs.getString("user_id"),
                        rs.getString("user_password"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("user_type"),
                        Grade.valueOf(rs.getString("user_grade")),
                        rs.getString("active"));
                return user;
            } else {
                System.out.println("\n입력하신 회원정보와 일치하는 회원이 없습니다!\n");
                Main.Main_run(Main.userTypeno, Main.userservice, Main.controller);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }


    public void deleteUser(int delUserNum) {
        String sql = "UPDATE users_info SET active = ? WHERE user_num = ? ";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "N");
            pstmt.setInt(2, delUserNum);
            pstmt.executeUpdate();

            System.out.println("회원 탈퇴 완료!");

        } catch (SQLException e) {
            e.printStackTrace();
            ;
        }
    }

    public List<User> findRiderUsers(int condition, String keyword) {
        List<User> foundUsers = new ArrayList<>();
        String sql = "SELECT * FROM users_info WHERE user_type = '라이더' ";
        PreparedStatement pstmt = null;

        if (condition == 1) { // 회원 번호
            sql += " AND user_num = ? AND active ='Y' ";
        } else if (condition == 2) { // 이름
            sql += " AND user_name = ? AND active ='Y' ";
        } else if (condition == 3) { // 아이디
            sql += " AND user_id = ? AND active ='Y' ";
        }

        try (Connection conn = DBConnectionManager.getConnection()) {
            pstmt = conn.prepareStatement(sql); // try 블록 안에서 PreparedStatement 생성

            if (condition == 1) { // 회원 번호
                pstmt.setInt(1, Integer.parseInt(keyword));
            } else if (condition == 2) { // 이름
                pstmt.setString(1, keyword);
            } else if (condition == 3) { // 아이디
                pstmt.setString(1, keyword);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    foundUsers.add(new User(
                            rs.getInt("user_num"),
                            rs.getString("user_name"),
                            rs.getString("user_id"),
                            rs.getString("user_password"),
                            rs.getString("address"),
                            rs.getString("phone_number"),
                            rs.getString("user_type"),
                            Grade.valueOf(rs.getString("user_grade")),
                            rs.getString("active")
                    ));
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
        return foundUsers;
    }

    // DB에서 특정 컬럼을 업데이트하는 메서드
    public void updateUserInfo(int userNum, String column, String newValue) {

        String sql = "UPDATE users_info SET " + column + " = ? WHERE user_num = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newValue);
            pstmt.setInt(2, userNum);

            pstmt.executeUpdate();

            System.out.println("### 업데이트 성공!");

        } catch (SQLException e) {
            System.out.println("### 업데이트 실패. 입력을 다시 확인하세요!");
            e.printStackTrace();
        }
    }

    public User findUserById(String userId){
        User foundUser = null;

        String sql = "SELECT * FROM users_info WHERE user_id = ?";

        PreparedStatement pstmt = null;

        try (Connection conn = DBConnectionManager.getConnection()) {
            pstmt = conn.prepareStatement(sql); // try 블록 안에서 PreparedStatement 생성
            pstmt.setString(1,userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    foundUser = new User(
                            rs.getInt("user_num"),
                            rs.getString("user_name"),
                            rs.getString("user_id"),
                            rs.getString("user_password"),
                            rs.getString("address"),
                            rs.getString("phone_number"),
                            rs.getString("user_type"),
                            Grade.valueOf(rs.getString("user_grade")),
                            rs.getString("active")
                    );
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
        return foundUser;
    }
}
