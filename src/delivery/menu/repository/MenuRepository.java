
package delivery.menu.repository;

import delivery.common.Condition;
import delivery.jdbc.DBConnectionManager;
import delivery.menu.domain.Menu;
import delivery.restaurants.domain.Restaurants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import static delivery.common.Condition.*;


public class MenuRepository{

    // 음식점 추가하기
    public void insertMenu(int store_num, Menu menu) {
        String sql = """
                INSERT INTO menu_info
                      VALUES(menu_info_seq.NEXTVAL,?,?,?,?,?)""";

        try (Connection conn = delivery.jdbc.DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, store_num);
            pstmt.setString(2, menu.getMenu_name());
            pstmt.setString(3, menu.getCategory());
            pstmt.setInt(4, menu.getPrice());
            pstmt.setString(5, menu.getActive());

            pstmt.executeUpdate();

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 음식점 찾기
    public List<Menu> searchMenuList(Condition condition, String keyword) throws Exception {
        List<Menu> searchList = new ArrayList<>();

        String sql = "SELECT * FROM menu_info WHERE active = 'Y'";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

        if (condition == MENU_NAME) {
            sql += " AND menu_name LIKE ? ORDER BY restaurant_num, menu_num";
            pstmt.setString(1,"%"+keyword+"%");
        } else if (condition == CATEGORY) {
            sql += " AND category LIKE ? ORDER BY restaurant_num, menu_num";
            pstmt.setString(1,"%"+keyword+"%");
        }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Menu menu = createMenuFromResultSet(rs);
                searchList.add(menu);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return searchList;
    }

    public List<Menu> searchMenuList(Condition condition, int keyword) throws Exception {
        List<Menu> searchList = new ArrayList<>();

        String sql = "SELECT * FROM menu_info WHERE active = 'Y'";

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if  (condition == PRICE) {
                sql += " AND price < ? ORDER BY restaurant_num, menu_num";
                pstmt.setInt(1,keyword);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Menu menu = createMenuFromResultSet(rs);
                searchList.add(menu);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return searchList;
    }


    // String store_name, String open_hours, String call_number, String delivery_area, String detail_info
    // ResultSet에서 추출한 결과를 Restaurant 객체로 포장해주는 헬터 메서드
    private static Menu createMenuFromResultSet(ResultSet rs) throws SQLException {
        Menu menu = new Menu(rs.getString("menu_name"),
                rs.getString("category"),
                rs.getInt("price"));

        menu.setMenu_num(rs.getString("menu_num"));
        menu.setActive(rs.getString("active"));
        return menu;
    }


    public void deleteMenu(int delMenuNum) {
        String sql = "UPDATE menu_info SET active = 'N' WHERE menu_num = ?";

        Connection conn = null;
        try(Connection conn1 = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn1.prepareStatement(sql)) {

            pstmt.setInt(1, delMenuNum);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
