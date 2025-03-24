package delivery.review.service;

import delivery.review.domain.Review;
import delivery.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository = new ReviewRepository();

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
        System.out.println();
    }

    private void deleteReviewData() {
        System.out.println("\n### 삭제를 위한 리뷰 검색을 시작합니다.");
        List<Review> reviews = findReviewData();

        if (reviews.size() > 0) {
            List<Integer> reviewNums = new ArrayList<>();
            for (Review review: reviews) {
                System.out.println(review);
                reviewNums.add(review.getReviewNum());
            }
            System.out.println("\n### 삭제할 리뷰의 번호를 입력하세요.");
            int delReviewNum = inputInteger(">>> ");

            if (reviewNums.contains(delReviewNum)) {
                reviewRepository.deleteReview(delReviewNum);
                Review deletedReview = reviews.stream().filter(x->x.getReviewNum() == delReviewNum).findFirst().orElse(null);
                System.out.printf("\n### 리뷰번호: %d 리뷰의 정보를 정상 삭제하였습니다.\n",
                        deletedReview.getReviewNum());
            } else {
                System.out.println("\n### 검색된 영화 번호로만 삭제가 가능합니다.");
            }
        } else {
            System.out.println("\n### 조회 결과가 없습니다.");
        }
    }

    private void showFoundReviewData() {
    }

    private void insertReviewData() {
    }
}
