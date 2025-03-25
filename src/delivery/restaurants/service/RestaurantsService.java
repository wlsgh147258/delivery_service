package delivery.restaurants.service;

import delivery.common.DeliveryService;
import delivery.jdbc.DBConnectionManager;
import delivery.restaurants.domain.Restaurants;
import delivery.restaurants.repository.RestaurantsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static delivery.ui.AppUi.*;

public class RestaurantsService implements DeliveryService {

    private final RestaurantsRepository restaurantsRepository = new RestaurantsRepository();

    public void start() {

        while (true) {

            //로그인 로직 추가하기
            int userNum = 10;

            restaurantManagementScreen();
            int num = inputInteger(">>> ");

            switch (num) {
                case 1:
                    insertRestaurant(userNum);
                    break;
                case 2:
                    searchRestaurant(userNum);
                    break;
                case 3:
                    updateRestaurant(userNum);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("### 메뉴를 다시 입력해주세요.");
            }
        }
    }

    //식당 정보 추가
    private void insertRestaurant(int userNum) {

        System.out.println("====== 식당 정보를 추가합니다 =====");
        String store_name = inputString("# 식당명: ");
        String category = inputString("# 카테고리: ");
        String open_hours = inputString("# 영업 시간: ");
        String call_number = inputString("# 식당 전화 번호: ");
        String delivery_area = inputString("# 식당 주소: ");
        String detail_info = inputString("# 식당 소개: ");


        Restaurants restaurants = new Restaurants(userNum, store_name, category, open_hours, call_number, delivery_area, detail_info);

        restaurantsRepository.insertRestaurant(restaurants);

        System.out.printf("### [%s] 정보가 정상적으로 추가되었습니다.", store_name);

    }


    //기존 식당 정보 수정
    private void updateRestaurant(int userNum) {

        try {
            //메서드 실행되면 바로 운영중인 식당 리스트 보여주고 수정할 식당 선택
            List<Restaurants> restaurantsList = restaurantsRepository.searchRestaurantByOwner(userNum);

            if (restaurantsList.size() > 0) {
                List<Integer> storeNums = new ArrayList<>();
                for (Restaurants restaurant : restaurantsList) {
                    System.out.println(restaurant);
                    storeNums.add(restaurant.getStore_num());
                }
                System.out.println("\n### 수정할 식당의 번호를 입력하세요. ");
                int updateRestaNum = inputInteger(">>> ");

                if (storeNums.contains(updateRestaNum)) {

                    //어떤 정보를 수정할지 선택
                    restaurantUpdateScreen();
                    int updateSelection = inputInteger(">>> ");

                    switch (updateSelection) {
                        case 1, 2, 3, 4, 5, 6:

                            for (Restaurants restaurant : restaurantsList) {

                                //수정 프로세스 진행
                                updateProcess(updateSelection, updateRestaNum, restaurant);
                                break;
                            }

                            break;
                        case 7:
                            System.out.println("메뉴 수정 하기");
                            break;
                        case 8:
                            if (storeNums.contains(updateRestaNum)) {
                                restaurantsRepository.deleteRestaurant(updateRestaNum);

                                for (Restaurants restaurant : restaurantsList) {
                                    if (restaurant.getStore_num() == updateRestaNum) {

                                        System.out.printf("\n### %d번 %s 식당 정보를 정상 삭제하였습니다. \n",
                                                restaurant.getStore_num(), restaurant.getStore_name());
                                        break;
                                    }
                                }
                            } else {
                                System.out.println("\n### 알맞은 식당 번호만 삭제할 수 있습니다.");
                            }
                            break;
                        default:
                            System.out.println("### 메뉴를 다시 입력해주세요.");
                            break;
                    }
                } else {
                    System.out.println("\n### 잘못된 식당 입력입니다.");
                }


            } else {
                System.out.println("운영중인 식당이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 수정 프로세스
    private void updateProcess(int updateSelection, int updateRestaNum, Restaurants restaurant) {

        try {

            String column = "";
            String newValue = "";

            switch (updateSelection) {
                case 1:
                    column = "restaurant_name";
                    System.out.print("새로운 식당 이름 입력: ");
                    newValue = inputString(">> ");
                    break;
                case 2:
                    column = "category";
                    System.out.print("새로운 카테고리 입력: ");
                    newValue = inputString(">> ");
                    break;
                case 3:
                    column = "opening_hours";
                    System.out.print("새로운 영업 시간 입력: ");
                    newValue = inputString(">> ");
                    break;
                case 4:
                    column = "phone_number";
                    System.out.print("새로운 전화번호 입력: ");
                    newValue = inputString(">> ");
                    break;
                case 5:
                    column = "delivery_area";
                    System.out.print("새로운 주소 입력: ");
                    newValue = inputString(">> ");
                    break;
                case 6:
                    column = "detailed_info";
                    System.out.print("새로운 식당 정보 입력: ");
                    newValue = inputString(">> ");
                    break;
                default:
                    System.out.println("### 잘못된 입력입니다.");
                    return;
            }

            // DB 업데이트 실행
            updateRestaurantInfo(updateRestaNum, column, newValue);
            System.out.printf("\n###[ %d번 ] 식당의[ %s ] 정보가 성공적으로 수정되었습니다.\n", updateRestaNum, column);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // DB에서 특정 컬럼을 업데이트하는 메서드
    private void updateRestaurantInfo(int storeNum, String column, String newValue) {
        System.out.println("storeNum = " + storeNum);
        System.out.println("column = " + column);
        System.out.println("newValue = " + newValue);

        String sql = "UPDATE restaurants SET " + column + " = ? WHERE restaurant_num = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newValue);
            pstmt.setInt(2, storeNum);

            pstmt.executeUpdate();

            System.out.println("### 업데이트 성공!");

        } catch (SQLException e) {
            System.out.println("### 업데이트 실패. 입력을 다시 확인하세요!");
            e.printStackTrace();
        }
    }


    // 운영중인 식당 정보 출력
    private void searchRestaurant(int userNum) {

        try {

            List<Restaurants> restaurantsList = restaurantsRepository.searchRestaurantByOwner(userNum);

            int count = restaurantsList.size();

//            List<Integer> restaurantNums = new ArrayList<>();

            if (count > 0) {
                System.out.println("\n===================검색 결과 (총 %d건)=====================\n");
                for (Restaurants restaurants : restaurantsList) {
                    System.out.println(restaurants);
//                    restaurantNums.add(restaurants.getStore_num());
                }
                System.out.println();
            } else {
                System.out.println("\n### 검색 결과가 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}