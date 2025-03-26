package delivery.order.domain;

public class Order {

    private int orderNum;
    private int userNum; // 아직 User class 존재 x
    private int restaurantNum; // 아직 restaurant class 존재 x
    private int menuNum;
    private String rideYN;
    private String paymentInfo;
    private int menuPrice;

    public Order(int orderNum, int userNum, int restaurantNum, int menuNum, String rideYN, String paymentInfo) {
        this.orderNum = orderNum;
        this.userNum = userNum;
        this.restaurantNum = restaurantNum;
        this.menuNum = menuNum;
        this.rideYN = rideYN;
        this.paymentInfo = paymentInfo;
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
                "## 주문 번호: " + orderNum +
                        ", 사용자 번호: " + userNum +
                        ", 식당 번호: " + restaurantNum +
                        ", 메뉴 번호: " + menuNum +
                        ", 라이더 배치여부: " + rideYN +
                        ", 금액: " + paymentInfo + "원"
                ;
    }
}
