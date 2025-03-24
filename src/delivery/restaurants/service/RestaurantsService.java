package delivery.restaurants.service;

import delivery.restaurants.domain.Restaurants;
import delivery.restaurants.repository.RestaurantsRepository;

import java.util.ArrayList;
import java.util.List;

import static delivery.ui.AppUi.*;

public class RestaurantsService {

    private final RestaurantsRepository restaurantsRepository = new RestaurantsRepository();

    public void start()  {

        while (true) {

            //로그인 로직 추가하기
            int userNum = 0;

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

                //어떤 정보를 수정할지 선택
                restaurantUpdateScreen();
                int updateSelection = inputInteger(">>> ");

                switch (updateSelection) {
                    case 1, 2, 3, 4, 5, 6:
                        if (storeNums.contains(updateSelection)) {

                            for (Restaurants restaurant : restaurantsList) {
                                if (restaurant.getStore_num() == updateSelection) {
                                    //수정 프로세스 진행
                                    updateProcess(restaurant);
                                    break;
                                }

                            }
                        }
                        break;
                    case 7:
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
                    default:
                        System.out.println("### 메뉴를 다시 입력해주세요.");
                }


            } else {
                System.out.println("운영중인 식당이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 수정 프로세스
    private void updateProcess(Restaurants restaurant) {

    }

    // 운영중인 식당 정보 출력
    private void searchRestaurant(int userNum)  {

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
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}