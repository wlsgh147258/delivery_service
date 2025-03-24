package delivery.order.domain;

public class Order {

    private int orderNum;
    private int userNum; // 아직 User class 존재 x
    private int restaurantNum; // 아직 restaurant class 존재 x
    private String rideYN;
    private String paymentInfo;

    public int getOrderNum() {return orderNum;}

    public void setOrderNum(int orderNum) {this.orderNum = orderNum;}

    public String getRideYN() {return rideYN;}

    public void setRideYN(String rideYN) {this.rideYN = rideYN;}

    public String getPaymentInfo() {return paymentInfo;}

    public void setPaymentInfo(String paymentInfo) {this.paymentInfo = paymentInfo;}

    @Override
    public String toString() {
        return
                "## 주문 번호: " + orderNum +
                        ", 사용자 번호: " + //userNum +
                        ", 식당 번호: " + // retaurantNum +
                        ", 메뉴 번호: " + // menuNum +
                        ", 라이더 가능여부: " + rideYN +
                        ", 금액: " + paymentInfo + "원"
                ;
    }
}
