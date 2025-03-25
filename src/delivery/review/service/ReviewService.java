package delivery.review.service;

import static delivery.ui.AppUi.*;
import delivery.review.domain.Review;
import delivery.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository = new ReviewRepository();

    private final int FIND_BY_NUM = 1;
    private final int FIND_BY_ID = 2;
    private final int FIND_ALL = 3;
    public void start() {
        while (true) {
            reviewManagementScreen();
            int selection = inputInteger(">>> ");

            switch (selection) {
                case 1:
                    insertReviewData();
                    break;
                case 2:
                    showFoundReviewData();
                    break;
                case 3:
                    deleteReviewData();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("### 메뉴를 다시 입력하세요.");
            }
        }
    }

    private List<Review> findReviewData() {
        System.out.println("\n========== 리뷰 검색 조건을 선택하세요 ===========");
        System.out.println("[1. 주문번호로 검색 | 2. 유저 아이디로 검색 | 3. 전체검색]");
        int selection = inputInteger(">>> ");

        int condition = FIND_ALL;

        switch(selection) {
            case 1:
                System.out.println("주문 번호로 검색합니다.");
                condition = FIND_BY_NUM;
                break;
            case 2:
                System.out.println("유저 번호로 검색합니다.");
                condition = FIND_BY_ID;
                break;
            case 3:
                System.out.println("전체 리뷰를 검색합니다.");
                condition = FIND_ALL;
                break;
            default:
                System.out.println("해당 메뉴가 존재하지 않습니다. 전체 정보로 검색합니다.");
        }
        String keyword = "";
        if (condition != FIND_ALL) {
            keyword = inputString("검색어: ");
        }

        return reviewRepository.findReviews(condition, keyword);

    }

    private void deleteReviewData() {
        System.out.println("삭제를 위한 리뷰 검색을 시작합니다.");
        List<Review> reviews = findReviewData();

        if (reviews.size() > 0) {
            List<Integer> reviewNums = new ArrayList<>();
            for (Review review: reviews) {
                System.out.println(review);
                reviewNums.add(review.getReviewNum());
            }
            System.out.println("삭제할 리뷰의 번호를 입력하세요.");
            int delReviewNum = inputInteger(">>> ");

            if (reviewNums.contains(delReviewNum)) {
                reviewRepository.deleteReview(delReviewNum);
                Review deletedReview = reviews.stream().filter(x->x.getReviewNum() == delReviewNum).findFirst().orElse(null);
                System.out.printf("리뷰번호: %d 리뷰의 정보를 정상 삭제하였습니다.\n",
                        deletedReview.getReviewNum());
            } else {
                System.out.println("검색된 영화 번호로만 삭제가 가능합니다.");
            }
        } else {
            System.out.println("조회 결과가 없습니다.");
        }
    }

    private void showFoundReviewData() {
        List<Review> reviews = findReviewData();
        int count = reviews.size();
        if(count > 0) {
            System.out.printf("\n========== 검색 결과 %d개 ==========", count);
            for(Review review: reviews) {
                System.out.println(review);
            }
        } else {
            System.out.println("\n## 검색된 리뷰가 없습니다.");
        }
    }

    private void insertReviewData() {
        System.out.println("\n===== review를 등록합니다. =====");
        int orderNum = inputInteger("# 주문번호: ");
        int rating = inputInteger("# 평점: ");
        String content = inputString("# 내용: ");

        reviewRepository.addReview(new Review(orderNum, rating, content));

        System.out.printf("## [%s] 리뷰가 정상적으로 추가되었습니다.\n", content);
    }
}
