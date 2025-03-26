package delivery.restaurants.domain;

public class Restaurants {

    private int store_num;  // 가게코드
    private String store_name; // 가게명
    private String open_hours; // 오픈시간
    private String call_number; // 가게 전화번호
    private String delivery_area; // 배송지역
    private String detail_info; // 세부정보
    private String active_flag; // 활성화 여부
    private int user_num; // 사용자(점주)코드
    private String category; // 겁색을 위한 카테고리


    public Restaurants(int user_num, String store_name, String category, String open_hours, String call_number, String delivery_area, String detail_info) {
        this.user_num = user_num;
        this.store_name = store_name;
        this.category = category;
        this.open_hours = open_hours;
        this.call_number = call_number;
        this.delivery_area = delivery_area;
        this.detail_info = detail_info;
    }

    public int getUser_num() {
        return user_num;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public String toString() {
        return "## 업장코드: " + store_num +
                ", 업장명:" + store_name +
                ", 오픈시간:" + open_hours +
                ", 전화번호:" + call_number +
                ", 배달지역:" + delivery_area +
                ", 상세설명:" + detail_info +
                ", 카테고리=" + category;
    }
}
//////