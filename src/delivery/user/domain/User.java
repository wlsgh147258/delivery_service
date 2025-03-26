package delivery.user.domain;

import delivery.order.domain.Order;

import java.util.Map;

public class User {

    public static final int SILVER_PRICE = 50000;
    public static final int GOLD_PRICE = 70000;
    public static final int PLATINUM_PRICE = 100000; //나중에 상의해서 고치기


    private static int sequence;

    private int userNum;
    private String userName;
    private String userId;
    private String userPassword;
    private String address;
    private String phoneNumber;
    private String userType;
    private Grade userGrade;
    private String active;
    private int totalPaying;

    private Map<Integer, Order> orderList;


    public int getTotalPaying() {
        return totalPaying;
    }

    public void setTotalPaying(int totalPaying) {
        this.totalPaying += totalPaying;

        if(this.totalPaying >= PLATINUM_PRICE){
            this.userGrade = Grade.PLATINUM;
        } else if (this.totalPaying >= GOLD_PRICE) {
            this.userGrade = Grade.GOLD;
        } else if (this.totalPaying >= SILVER_PRICE) {
            this.userGrade = Grade.SILVER;
        }
    }

    public Map<Integer, Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(Map<Integer, Order> orderList) {
        this.orderList = orderList;
    }

    public User(
            int userNum, String userName, String userId, String userPassword, String address,
            String phoneNumber, String userType, Grade userGrade, String active) {
        this.userNum = userNum;
        this.userName = userName;
        this.userId = userId;
        this.userPassword = userPassword;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.userGrade = userGrade;
        this.active = active;
    }

    public static int getSequence() {return sequence;}

    public static void setSequence(int sequence) {User.sequence = sequence;}

    public int getUserNum() {return userNum;}

    public void setUserNum(int userNum) {this.userNum = userNum;}

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    public String getUserPassword() {return userPassword;}

    public void setUserPassword(String userPassword) {this.userPassword = userPassword;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getUserType() {return userType;}

    public void setUserType(String userType) {this.userType = userType;}


    public Grade getUserGrade() {return userGrade;}

    public void setUserGrade(Grade userGrade) {this.userGrade = userGrade;}

    public String getActive() {return active;}

    public void setActive(String active) {this.active = active;}

    @Override
    public String toString() {
        return "## 회원번호: " + userNum +
                ", 회원명: '" + userName + '\'' +
                ", 회원 아이디: " + userId + '\'' +
                ", 회원 비밀번호: " + userPassword + '\'' +
                ", 주소: " + address + '\'' +
                ", 전화번호: " + phoneNumber + '\'' +
                ", 타입: " + userType + '\'' +
                ", userGrade: " + userGrade +
                ", active: " + active + '\'' +
                '}';
    }

}
