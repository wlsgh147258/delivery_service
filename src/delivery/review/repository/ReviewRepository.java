package delivery.review.repository;

import delivery.jdbc.DBConnectionManager;
import delivery.main.Main;
import delivery.order.repository.Option;
import delivery.review.domain.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews VALUES(reviews_seq.NEXTVAL, ?, ?, ?)";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, review.getOrderNum());
            pstmt.setInt(2,review.getRating());
            pstmt.setString(3, review.getContent());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReview(int delReviewNum) {
        int userNum = Main.user.getUserNum();
        String sql = "DELETE FROM reviews WHERE review_num = ?";
        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delReviewNum);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Review> findReviews(Option op, String keyword) {
        List<Review> foundReviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews ";
        if (op == Option.FIND_BY_ORDER_NUM) {
            sql += "WHERE order_num = ?";
        } else if (op == Option.FIND_BY_USER_NUM) {
            sql = "SELECT * FROM reviews r INNER JOIN (SELECT DISTINCT o.order_num FROM order_info o INNER JOIN users_info u ON o.user_num = u.user_num" +
                    " WHERE o.user_num = ?) orn ON r.order_num = orn.order_num";
        } else if (op == Option.FIND_BY_RESTAURANT_NUM) {
            sql = "SELECT * FROM reviews r" +
                    " INNER JOIN (SELECT * FROM restaurants rt INNER JOIN order_info o ON rt.restaurant_num = o.restaurant_num WHERE rt.restaurant_num= ?) rto" +
                    " ON r.order_num = rto.order_num";
        }

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if(op != Option.FIND_ALL) {
                pstmt.setInt(1, Integer.parseInt(keyword));
            }
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                foundReviews.add(new Review(
                        rs.getInt("review_num"),
                        rs.getInt("order_num"),
                        rs.getInt("rating"),
                        rs.getString("content")
                ));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return foundReviews;
    }
}
