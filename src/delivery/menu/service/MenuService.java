package delivery.menu.service;

import delivery.common.Condition;
import delivery.common.DeliveryService;
import delivery.menu.domain.Menu;
import delivery.menu.repository.MenuRepository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static delivery.ui.AppUi.MenuManagementScreen;

public class MenuService implements DeliveryService {

    private final MenuRepository menuRepository = new MenuRepository();

    @Override
    public void start() {

        while (true) {
            MenuManagementScreen();
            int selection = inputInteger(">>> ");

            switch (selection) {
                case 1:
                    insertMenuData();
                    break;
                case 2:
                    showSearchMenuData();
                    break;
                case 3:
                    deleteMenuData();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("### 메뉴를 다시 입력하세요.");

            }
        }

    }



    private void insertMenuData() {
        System.out.println("\n ====== 메뉴 정보를 추가합니다. ======");
        int store_num = inputInteger("# Restaurant 번호 : ");
        String menuName = inputString("# 메뉴명: ");
        String category = inputString("# 카테고리 분류: ");
        int price = inputInteger("# 가격: ");

        Menu newMenu = new Menu(menuName, category, price);

        menuRepository.insertMenu(store_num,newMenu);

        System.out.printf("\n### [%s] 정보가 정상적으로 추가되었습니다.", menuName);
    }


    private void showSearchMenuData() {
        try {
            List<Menu> menus = searchMenuData();
            int count = menus.size();
            if(count > 0) {
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


    private List<Menu> searchMenuData() throws Exception {
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
        if (condition == Condition.PRICE) {
            int keyword = inputInteger("# 입력값 이하의 메뉴 검색: ");
            return menuRepository.searchMenuList(condition, keyword);
        } else if (condition != Condition.ALL) {
            String keyword = inputString("# 검색어: ");
            return menuRepository.searchMenuList(condition, keyword);
        } else {
            String keyword= "";
            return menuRepository.searchMenuList(condition, keyword);
        }


    }



    private void deleteMenuData() {
        try {
            System.out.println("\n### 삭제를 위한 영화 검색을 시작합니다.");
            List<Menu> menus = searchMenuData();

            if (!menus.isEmpty()) {
                List<Integer> menuNums = new ArrayList<>();

                for (Menu menu : menus) {
                    System.out.println(menu);
                    menuNums.add(menu.getMenu_num());
                }
                System.out.println("\n### 삭제할 영화의 번호를 입력하세요.");
                int delMenuNum = inputInteger(">>> ");

                if (menuNums.contains(delMenuNum)) {
                    menuRepository.deleteMenu(delMenuNum);
                    for (Menu menu : menus) {
                        if (menu.getMenu_num() == delMenuNum) {
                            System.out.printf("\n### 영화번호: %d -> %s 영화의 정보를 정상 삭제하였습니다.\n"
                                    , menu.getMenu_num(), menu.getMenu_name());
                            break;
                        }
                    }
                } else {
                    System.out.println("\n### 검색된 메뉴 번호로만 삭제가 가능합니다.");
                }

            } else {
                System.out.println("\n### 조회 결과가 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("\n ### 조회 결과가 없습니다.2");
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
