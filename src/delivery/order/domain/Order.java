package delivery.order.domain;

public class Order {

    private int orderNum;
    private int userNum; // 아직 User class 존재 x
    private int restaurantNum; // 아직 restaurant class 존재 x
    private int menuNum;
    private String rideYN;
    private String cookYN;
    private String paymentInfo;
    private int menuPrice;

    public Order(int orderNum, int userNum, int restaurantNum, int menuNum, String rideYN, String paymentInfo, String cookYN) {
        this.orderNum = orderNum;
        this.userNum = userNum;
        this.restaurantNum = restaurantNum;
        this.menuNum = menuNum;
        this.cookYN = cookYN;
        this.paymentInfo = paymentInfo;
        this.rideYN = rideYN;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getOrderNum() {return orderNum;}

    public void setOrderNum(int orderNum) {this.orderNum = orderNum;}

    public String getRideYN() {return rideYN;}

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public void setRestaurantNum(int restaurantNum) {
        this.restaurantNum = restaurantNum;
    }

    public void setMenuNum(int menuNum) {
        this.menuNum = menuNum;
    }

    public String getCookYN() {
        return cookYN;
    }

    public void setCookYN(String cookYN) {
        this.cookYN = cookYN;
    }

    public int getUserNum() {
        return userNum;
    }

    public int getRestaurantNum() {
        return restaurantNum;
    }

    public int getMenuNum() {
        return menuNum;
    }
    public void setRideYN(String rideYN) {this.rideYN = rideYN;}

    public String getPaymentInfo() {return paymentInfo;}

    public void setPaymentInfo(String paymentInfo) {this.paymentInfo = paymentInfo;}

    @Override
    public String toString() {
        return
                "## 주문 코드: " + orderNum +
                        ", 사용자 번호: " + userNum +
                        ", 식당 코드: " + restaurantNum +
                        ", 메뉴 코드: " + menuNum +
                        ", 라이더 배치여부: " + rideYN +
                        ", 결제수단: " + paymentInfo +
                        ", 금액: " + menuPrice + "원" +
                        ", 조리 완료여부: " + cookYN
                ;
    }
}
