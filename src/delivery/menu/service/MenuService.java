package delivery.menu.service;

import delivery.common.Condition;
import delivery.common.DeliveryService;
import delivery.jdbc.DBConnectionManager;
import delivery.menu.domain.Menu;
import delivery.menu.repository.MenuRepository;
import delivery.restaurants.domain.Restaurants;
import delivery.restaurants.repository.RestaurantsRepository;
import delivery.restaurants.service.RestaurantsService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static delivery.ui.AppUi.*;
import static delivery.ui.AppUi.inputString;

public class MenuService {

    private static final MenuRepository menuRepository = new MenuRepository();


    public void menu(Restaurants restaurant) {

        while (true) {
            MenuManagementScreen();
            int selection = inputInteger(">>> ");

            switch (selection) {
                case 1:
                    insertMenuData(restaurant);
                    break;
                case 2:
                    searchMenuOwner(restaurant);
                    break;
                case 3:
                    updateMenuData(restaurant);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("### 메뉴를 다시 입력하세요.");

            }
        }

    }


    private void insertMenuData(Restaurants restaurants) {
        System.out.println("\n ====== 메뉴 정보를 추가합니다. ======");
        String menuName = inputString("# 메뉴명: ");
        String category = inputString("# 한식 | 중식 | 양식 | 분식 | 패스트 푸드 | 후식 : ");
        int price = inputInteger("# 가격: ");

        Menu newMenu = new Menu(menuName, category, price);

        menuRepository.insertMenu(restaurants.getStore_num(), newMenu);

        System.out.printf("\n### [%s] 정보가 정상적으로 추가되었습니다.\n", menuName);
    }


    private void searchMenuOwner(Restaurants restaurant) {
        try {
            List<Menu> menus = searchMenuDataOwner(restaurant);
            int count = menus.size();
            if (count > 0) {
                System.out.printf("\n========================== [ %s ] 메뉴 검색 결과(총 %d건) ========================\n", restaurant.getStore_name(), count);
                for (Menu menu : menus) {
                    System.out.println(menu);
                }
            } else {
                System.out.println("\n### 검색 결과가 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("\n ### 검색 결과가 없습니다.2");
        }
    }

    public static void searchMenu() {
        try {
            List<Menu> menus = searchMenuData();
            int count = menus.size();
            if (count > 0) {
                System.out.printf("\n======================================= 검색 결과(총 %d건) =======================================\n", count);
                for (Menu menu : menus) {
                    System.out.println(menu);
                }
            } else {
                System.out.println("\n### 검색 결과가 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("\n ### 검색 결과가 없습니다.2");
        }
    }

    private List<Menu> searchMenuDataOwner(Restaurants restaurant) throws Exception {

        System.out.printf("\n## [ %s ] 식당 메뉴를 검색합니다. \n", restaurant.getStore_name());

        return menuRepository.searchMenuListByOwner(restaurant.getStore_num());
    }


    private static List<Menu> searchMenuData() throws Exception {
        System.out.println("\n============== 메뉴 검색 조건을 선택하세요. ===============");
        System.out.println("[ 1. 이름검색 | 2. 가격검색 | 3. 카테고리검색 | 4. 전체검색 ]");
        int selection = inputInteger(">>> ");

        Condition condition = Condition.ALL;

        switch (selection) {
            case 1:
                System.out.println("\n## 이름으로 검색합니다.");
                condition = Condition.MENU_NAME;
                break;
            case 2:
                System.out.println("\n## 가격으로 검색합니다.");
                condition = Condition.PRICE;
                break;
            case 3:
                System.out.println("\n## 카테고리로 검색합니다.");
                condition = Condition.CATEGORY;
                break;
            case 4:
                System.out.println("\n## 전체 정보를 검색합니다.");
                break;
            default:
                System.out.println("\n### 해당 메뉴가 존재하지 않습니다. 전체 정보로 검색합니다.");
        }

//        String keyword = "";
        return getMenus(condition);
    }

    private static List<Menu> getMenus(Condition condition) throws Exception {
        if (condition == Condition.PRICE) {
            int keyword = inputInteger("# 입력값 이하의 메뉴 검색: ");
            return menuRepository.searchMenuList(condition, keyword);
        } else if (condition == Condition.CATEGORY) {
            String keyword = inputString("# 검색어: ");
            return menuRepository.searchMenuList(condition, keyword);
        } else if (condition != Condition.ALL) {
            String keyword = inputString("# 검색어: ");
            return menuRepository.searchMenuList(condition, keyword);
        } else {
            String keyword = "";
            return menuRepository.searchMenuList(condition, keyword);
        }


    }


    //식당 메뉴 정보 수정
    private void updateMenuData(Restaurants restaurant) {

        try {
            //메서드 실행되면 바로 운영중인 식당 리스트 보여주고 수정할 식당 선택
            System.out.println("\n### 메뉴 수정/삭제를 위한 검색을 시작합니다. \n");

            List<Menu> menus = searchMenuDataOwner(restaurant);

            if (!menus.isEmpty()) {
                List<Integer> menuNums = new ArrayList<>();
                for (Menu menu : menus) {
                    System.out.println(menu);
                    menuNums.add(menu.getMenu_num());
                }
                System.out.println("\n### 수정할 메뉴의 코드를 입력하세요.");
                int updateMenuNum = inputInteger(">>> ");

                if (menuNums.contains(updateMenuNum)) {

                    //어떤 메뉴 정보를 수정할지 선택
                    menuUpdateScreen();
                    int updateSelection = inputInteger(">>> ");

                    switch (updateSelection) {
                        case 1, 2, 3:

                            for (Menu menu : menus) {
                                if (menu.getMenu_num() == updateMenuNum) {
                                    //수정 프로세스 진행
                                    updateProcess(updateSelection, menu);
                                    break;
                                }
                            }

                            break;
                        case 4:
                            if (menuNums.contains(updateMenuNum)) {
                                menuRepository.deleteMenu(updateMenuNum);
                                for (Menu menu : menus) {
                                    if (menu.getMenu_num() == updateMenuNum) {
                                        System.out.printf("\n### 메뉴 코드: %d -> 메뉴 %s 의 정보를 정상 삭제하였습니다.\n"
                                                , menu.getMenu_num(), menu.getMenu_name());
                                        break;
                                    }
                                }
                            } else {
                                System.out.println("\n### 검색된 메뉴 코드로만 삭제가 가능합니다.");
                            }
                            break;
                        default:
                            System.out.println("### 메뉴를 다시 입력해주세요.");
                            break;
                    }
                } else {
                    System.out.println("\n### 잘못된 메뉴 입력입니다.");
                }

            } else {
                System.out.println("메뉴가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 수정 프로세스
    private void updateProcess(int updateSelection, Menu menu) {

        try {
            String column = "";
            String newValue = "";

            switch (updateSelection) {
                case 1:
                    column = "menu_name";
                    System.out.printf("기존 메뉴 이름: %s >> 새로운 메뉴 이름: ", menu.getMenu_name());
                    newValue = inputString(" ");
                    break;
                case 2:
                    column = "category";
                    System.out.printf("기존 메뉴 카테고리: %s >> 새로운 메뉴 카테고리: ", menu.getCategory());
                    newValue = inputString(" ");
                    break;
                case 3:
                    column = "price";
                    System.out.printf("기존 메뉴 가격: %s >> 새로운 메뉴 가격: ", menu.getPrice());
                    newValue = inputString(" ");
                    break;
                default:
                    System.out.println("### 잘못된 입력입니다.");
                    return;
            }

            // DB 업데이트 실행
            menuRepository.updateMenuInfo(menu.getMenu_num(), column, newValue);
            System.out.printf("\n###[ %d번 ] 식당의 [ %s ] 정보가 성공적으로 수정되었습니다.\n", menu.getMenu_num(), column);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static Scanner sc = new Scanner(System.in);

    public static String inputString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    public static int inputInteger(String message) {
        System.out.print(message);
        int num = 0;
        try {
            num = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("# 올바른 정수 입력값이 아닙니다!");
        } finally {
            sc.nextLine(); // try에서도(엔터 입력값), catch에서도(쓰레기 문자열 수거) nextLine()이 동작해야 함.
        }
        return num;
    }

}
