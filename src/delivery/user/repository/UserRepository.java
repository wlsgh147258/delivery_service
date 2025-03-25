package delivery.user.repository;

import delivery.jdbc.DBConnectionManager;
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

    public void addUser(User user){
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
            sql += " WHERE user_name = ? AND active ='Y' ";
        } else if (condition == 3) { // 아이디
            sql += " WHERE user_id = ? AND active ='Y' ";
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


    public static String findUserType(String id, String pw) {
        String userType = "";
        List<User> searchList = new ArrayList<>();
        String sql = "SELECT * FROM users_info WHERE user_id = ? AND user_password = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("user_num"),
                        rs.getString("user_name"),
                        rs.getString("user_id"),
                        rs.getString("user_password"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("user_type"),
                        Grade.valueOf(rs.getString("user_grade")),
                        rs.getString("active"));

                searchList.add(user);
            }


            if (!searchList.isEmpty()) {
                for (User users : searchList) {
                    userType = users.getUserType();
                }
            }
            return userType;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchList.toString();
    }


        public void deleteUser(int delUserNum){
            String sql = "UPDATE users_info SET active = ? WHERE user_num = ? ";

            try(Connection conn = DBConnectionManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1,"N");
                pstmt.setInt(2,delUserNum);
                pstmt.executeUpdate();

                System.out.println("회원 탈퇴가 완료 되었습니다.");

            } catch (SQLException e) {
                e.printStackTrace();;
            }
        }
}
