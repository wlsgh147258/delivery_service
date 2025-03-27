package delivery.order.repository;

public enum Option {
    FIND_BY_ORDER_NUM,
    FIND_BY_USER_NUM,
    FIND_BY_MASTER_NUM, // restaurnt 주인의 user_num
    FIND_BY_RESTAURANT_NUM,
    FIND_ALL
}
