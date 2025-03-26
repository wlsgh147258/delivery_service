package delivery.review.service;

import static delivery.ui.AppUi.*;
import static delivery.ui.AppUi.reviewManagementScreenForMaster;

import delivery.common.DeliveryService;
import delivery.main.Main;
import delivery.order.domain.Order;
import delivery.order.repository.Option;
import delivery.order.repository.OrderRepository;
import delivery.restaurants.domain.Restaurants;
import delivery.restaurants.repository.RestaurantsRepository;
import delivery.review.domain.Review;
import delivery.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReviewService implements DeliveryService {
    private final ReviewRepository reviewRepository = new ReviewRepository();
    private final OrderRepository orderRepository = new OrderRepository();
    private final RestaurantsRepository restaurantsRepository = new RestaurantsRepository();

    @Override
    public void start() {
        while (true) {
            boolean isMaster = Main.user.getUserType().equals("점주");

            if (isMaster) {
                int restaurantNum = reviewManagementScreenForMaster(restaurantsRepository);
                if (restaurantNum == -1) {
                    return;
                }
                showFoundReviewData(restaurantNum);
            } else {
                reviewManagementScreen();
                int selection = inputInteger(">>> ");
                switch (selection) {
                    case 1:
                        insertReviewData();
                        break;
                    case 2:
                        showFoundReviewData(-1);
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
    }

    private Option getSelectedOptions() {
        System.out.println("\n========== 리뷰 검색 조건을 선택하세요 ===========");
        System.out.println("[1. 주문번호로 검색 | 2. 유저 아이디로 검색 | 3. 전체검색]");
        int selection = inputInteger(">>> ");

        Option option = Option.FIND_ALL;

        switch(selection) {
            case 1:
                System.out.println("주문 번호로 검색합니다.");
                option = Option.FIND_BY_ORDER_NUM;
                break;
            case 2:
                System.out.println("유저 번호로 검색합니다.");
                option = Option.FIND_BY_USER_NUM;
                break;
            case 3:
                System.out.println("전체 리뷰를 검색합니다.");
                break;
            default:
                System.out.println("해당 메뉴가 존재하지 않습니다. 전체 정보로 검색합니다.");
        }
        return option;

    }

    private List<Review> findReviewData(Option option, String keyword) {
        if (keyword == null && option != Option.FIND_ALL) {
            keyword = inputString("검색어: ");
        }

        return reviewRepository.findReviews(option, keyword);

    }

    private void deleteReviewData() {
        System.out.println("===== 삭제를 위한 리뷰 검색을 시작합니다. =====");
        List<Review> reviews;
        if(Main.user.getUserType().equals("고객")) {
            reviews = findReviewData(Option.FIND_BY_USER_NUM, Integer.toString(Main.user.getUserNum()));
        } else {
            reviews = findReviewData(Option.FIND_BY_MASTER_NUM, Integer.toString(Main.user.getUserNum()));//findReviewData(Option.FIND_BY_RESTAURANT_NUM, null);
        }
        for (Review review : reviews) {
            System.out.println(review);
        }
        if (!reviews.isEmpty()) {
            System.out.println("삭제할 리뷰의 번호를 입력하세요.");
            int delReviewNum = inputInteger(">>> ");
            if (reviews.stream().anyMatch(review -> review.getReviewNum() == delReviewNum)) {
                reviewRepository.deleteReview(delReviewNum);
                System.out.printf("리뷰번호: %d 리뷰의 정보를 정상 삭제하였습니다.\n", delReviewNum);
            } else {
                System.out.println("검색된 리뷰 번호로만 삭제가 가능합니다.");
            }
        } else {
            System.out.println("조회 결과가 없습니다.");
        }
    }

    private void showFoundReviewData(int restaurantNum) {
        List<Review> reviews;
        if(Main.user.getUserType().equals("고객")) {
            reviews = findReviewData(Option.FIND_BY_USER_NUM, Integer.toString(Main.user.getUserNum()));
        } else {
            reviews = findReviewData(Option.FIND_BY_RESTAURANT_NUM, Integer.toString(restaurantNum));
        }
        int count = reviews.size();
        if(count > 0) {
            System.out.printf("\n========== 검색 결과 %d개 ==========\n", count);
            for(Review review: reviews) {
                System.out.println(review);
            }
        } else {
            System.out.println("\n## 검색된 리뷰가 없습니다.");
        }
    }

    private void insertReviewData() {
        System.out.println("===== 주문 내역을 검색합니다. =====");
        List<Order> orderList = orderRepository.findOrderByUserNum(Main.user.getUserNum());
        List<Review> userReviews = findReviewData(Option.FIND_BY_USER_NUM, Integer.toString(Main.user.getUserNum()));
        Set<Integer> orderNumOfReviewsSet = new HashSet<>();
        List<Order> availableOrderList = new ArrayList<>();
        for (Review userReview : userReviews) {
            orderNumOfReviewsSet.add(userReview.getOrderNum());
        }
        for (Order order : orderList) {
            if(!orderNumOfReviewsSet.contains(order.getOrderNum()) && order.getRideYN().equals("Y")) {
                availableOrderList.add(order);
                System.out.println(order);
            }
        }
        if(availableOrderList.isEmpty()) {
            System.out.println("===== 리뷰를 작성할 수 있는 주문이 없습니다. =====");
            return;
        }
        System.out.println("\n===== review를 등록합니다. =====");
        int orderNum = inputInteger("# 주문번호: ");
        if(orderList.stream().noneMatch(order -> order.getOrderNum() == orderNum)
            || orderNumOfReviewsSet.contains(orderNum)) {
            System.out.println("잘못된 주문 번호 입력입니다. 처음으로 돌아갑니다.");
            return;
        }
        int rating = inputInteger("# 평점: ");
        if (rating < 0) {
            System.out.println("별점은 마이너스가 될 수 없습니다. 처음으로 돌아갑니다.");
            return;
        }

        String content = inputString("# 내용: ");

        reviewRepository.addReview(new Review(orderNum, rating, content));

        System.out.printf("## [%s] 리뷰가 정상적으로 추가되었습니다.\n", content);
    }
}
