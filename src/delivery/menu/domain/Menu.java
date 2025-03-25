package delivery.menu.domain;

public class Menu {

    private String menu_num; // 메뉴코드
    private String menu_name; // 메뉴명
    private String category; // 카테고리
    private int price; // 가격
    private String active; // 메뉴 활성화 여부

    public Integer getMenu_num() {
        return menu_num;
    }

    public void setMenu_num(String menu_num) {
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

    public Menu(String menu_name, String category, int price) {
        this.menu_name = menu_name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu_num='" + menu_num + '\'' +
                ", menu_name='" + menu_name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", active='" + active + '\'' +
                '}';
    }
}
