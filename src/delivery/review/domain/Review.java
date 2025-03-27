package delivery.review.domain;

public class Review {
    private int reviewNum;
    private int orderNum;
    private int rating;
    private String content;

    public Review(int orderNum, int rating, String content) {
        this.orderNum = orderNum;
        this.rating = rating;
        this.content = content;
    }
    public Review(int reviewNum, int orderNum, int rating, String content) {
        this.reviewNum = reviewNum;
        this.orderNum = orderNum;
        this.rating = rating;
        this.content = content;
    }

    public int getReviewNum() {
        return reviewNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public void setReviewNum(int reviewNum) {
        this.reviewNum = reviewNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return
                "## 리뷰 번호: " + reviewNum +
                        ", 주문 번호: " + orderNum +
                        ", 평점: " +  rating +
                        ", 내용: " + content
                ;
    }
}
