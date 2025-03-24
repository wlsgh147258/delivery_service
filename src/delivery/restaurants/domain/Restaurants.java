package delivery.restaurants.domain;

public class Restaurants {

    private int store_num;  // 가게코드
    private String store_name; // 가게명
    private String open_hours; // 오픈시간
    private String call_number; // 가게 전화번호
    private String delivery_area; // 배송지역
    private String detail_info; // 세부정보
    private String active_flag; // 활성화 여부

    private String menu_num; // 메뉴코드
    private String menu_name; // 메뉴명
    private String category; // 카테고리
    private int price; // 가격


    public Restaurants(String store_name, String open_hours, String call_number, String delivery_area, String detail_info) {
        this.store_name = store_name;
        this.open_hours = open_hours;
        this.call_number = call_number;
        this.delivery_area = delivery_area;
        this.detail_info = detail_info;
    }

    public int getStore_num() {
        return store_num;
    }

    public void setStore_num(int store_num) {
        this.store_num = store_num;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getOpen_hours() {
        return open_hours;
    }

    public void setOpen_hours(String open_hours) {
        this.open_hours = open_hours;
    }

    public String getCall_number() {
        return call_number;
    }

    public void setCall_number(String call_number) {
        this.call_number = call_number;
    }

    public String getDelivery_area() {
        return delivery_area;
    }

    public void setDelivery_area(String delivery_area) {
        this.delivery_area = delivery_area;
    }

    public String getDetail_info() {
        return detail_info;
    }

    public void setDetail_info(String detail_info) {
        this.detail_info = detail_info;
    }

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }

    public String getMenu_num() {
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

    @java.lang.Override
    public java.lang.String toString() {
        return "Restaurants{" +
                "store_num=" + store_num +
                ", store_name='" + store_name + '\'' +
                ", open_hours='" + open_hours + '\'' +
                ", call_number='" + call_number + '\'' +
                ", delivery_area='" + delivery_area + '\'' +
                ", detail_info='" + detail_info + '\'' +
                ", active_flag='" + active_flag + '\'' +
                '}';
    }
}
//////