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
        String sql = "INSERT INTO users VALUES(user_seq.NEXTVAL,?,?,?,?,?,?,?)";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,user.getUserName());
            pstmt.setString(2,user.getUserId());
            pstmt.setString(3,user.getUserPassword());
            pstmt.setString(4,user.getAddress());
            pstmt.setString(5,user.getUserType());
            pstmt.setString(6,user.getUserGrade().toString());
            pstmt.setString(7,user.getActive());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> findUsers(int condition, String keyword) {
        List<User> foundUsers = new ArrayList<>();
        String sql = "SELECT * FROM users_info";
        if (condition == 1){ // 회원 번호
            sql += "WHERE user_num = ?";
        }
        else if (condition == 2) { // 이름
            sql += "WHERE user_name = ?";
        }
        else if (condition == 3) { // 아이디
            sql += "WHERE user_id = ?";
        }

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if(condition != 4){
                pstmt.setInt(1,Integer.parseInt(keyword));
            }

            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                foundUsers.add(new User(
                        rs.getInt("user_num"),
                        rs.getString("user_name"),
                        rs.getString("user_id"),
                        rs.getString("user_passward"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("user_type"),
                        Grade.valueOf(rs.getString("grade")),
                        rs.getString("active")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foundUsers;
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
