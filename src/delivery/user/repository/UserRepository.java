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

            pstmt.setInt(1,user.getUserNum());
            pstmt.setString(2,user.getUserName());
            pstmt.setString(3,user.getUserId());
            pstmt.setString(4,user.getUserPassword());
            pstmt.setString(5,user.getAddress());
            pstmt.setString(6,user.getUserType());
            pstmt.setString(7,user.getUserGrade().toString());

            pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> findUserByName(String userName){
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users_info WHERE user_name = ? AND active = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, "Y");

            ResultSet rs = pstmt.executeQuery();

            return userList;
            while (rs.next()) {
                User user = new User(

                );
                user.setUserNumber(rs.getInt("user_number"));
                userList.add(user);
            }


    } catch (SQLException e) {
            e.printStackTrace();
        }

        public void deleteUser(int delUserNum){}

    public User findUserByNumber(int userNumber){
        return userDatabase.get(userNumber);
    }
}
