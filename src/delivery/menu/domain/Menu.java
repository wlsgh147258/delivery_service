package delivery.menu.domain;

public class Menu {

    private int menu_num; // 메뉴코드
    private int restaurantNum;
    private String menu_name; // 메뉴명
    private String category; // 카테고리
    private int price; // 가격
    private String active; // 메뉴 활성화 여부

    public Integer getMenu_num() {
        return menu_num;
    }

    public void setMenu_num(int menu_num) {
        this.menu_num = menu_num;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getRestaurantNum() {
        return restaurantNum;
    }

    public void setRestaurantNum(int restaurant_num) {
        this.restaurantNum = restaurant_num;
    }

    public Menu(int menu_num, int restaurantNum, String menu_name, String category, int price, String active) {
        this.menu_num = menu_num;
        this.restaurantNum = restaurantNum;
        this.menu_name = menu_name;
        this.category = category;
        this.price = price;
        this.active = active;
    }

    public Menu(String menu_name, String category, int price) {
        this.menu_name = menu_name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "메뉴 번호: " + menu_num  +
                ", 메뉴 이름: " + menu_name +
                ", 카테고리: " + category +
                ", 가격: " + price +
                ", 주문가능여부: " + active;
    }
}
