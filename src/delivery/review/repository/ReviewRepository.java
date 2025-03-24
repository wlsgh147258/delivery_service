package delivery.review.repository;

import delivery.jdbc.DBConnectionManager;
import delivery.review.domain.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews VALUES(reviews_seq, ?, ?, ?)";

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

    public List<Review> findByUserNum(int userNum) {

        List<Review> reviewList = new ArrayList<>();
        // JOIN 문 이용
        String sql = "SELECT * FROM reviews r INNER JOIN (SELECT o.order_num FROM order_info o INNER JOIN users_info u ON o.user_num = u.user_num" +
                " WHERE o.user_num = ?) orn ON r.order_num = orn.order_num";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Review review = new Review(
                        rs.getInt("review_num"),
                        rs.getInt("order_num"),
                        rs.getInt("rating"),
                        rs.getString("content")
                );

                reviewList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    public void deleteReview(int delReviewNum) {
        String sql = "DELETE FROM reviews WHERE review_num = ?";
        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delReviewNum);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
