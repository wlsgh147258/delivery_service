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
        String sql = "INSERT INTO users_info VALUES(users_info_seq.NEXTVAL,?,?,?,?,?,?,?,?)";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,user.getUserName());
            pstmt.setString(2,user.getUserId());
            pstmt.setString(3,user.getUserPassword());
            pstmt.setString(4,user.getAddress());
            pstmt.setString(5,user.getPhoneNumber());
            pstmt.setString(6,user.getUserType());
            pstmt.setString(7,user.getUserGrade().toString());
            pstmt.setString(8,user.getActive());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> findUsers(int condition, String keyword) {
        List<User> foundUsers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM users_info";

            if (condition == 1) { // 회원 번호
                sql += " WHERE user_num = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(keyword));
            } else if (condition == 2) { // 이름
                sql += " WHERE user_name = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, keyword);
            } else if (condition == 3) { // 아이디
                sql += " WHERE user_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, keyword);
            } else {
                pstmt = conn.prepareStatement(sql); // 조건이 없을 경우 모든 사용자 검색
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                try {
                    foundUsers.add(new User(
                            rs.getInt("user_num"),
                            rs.getString("user_name"),
                            rs.getString("user_id"),
                            rs.getString("user_password"), // 오타 수정
                            rs.getString("address"),
                            rs.getString("phone_number"),
                            rs.getString("user_type"),
                            Grade.valueOf(rs.getString("grade")),
                            rs.getString("active")
                    ));
                } catch (IllegalArgumentException e) {
                    System.err.println("Grade 열 값 오류: " + e.getMessage());
                    // 또는 로그에 기록하거나, 다른 예외 처리 수행
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
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
