/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.UserAccount;
/**
 *
 * @author MOH
 */
public class AdminsUsersDao extends ConnectionDao{
    
     public ArrayList<UserAccount> buildAdminsUsers()
            throws Exception {
        ArrayList<UserAccount> list = new ArrayList<>();

        try {
            Connection conn = getConnection();

            String sql = "SELECT * FROM BUSES.ADMINS_USERS";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(populateAdminsUsers(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private UserAccount populateAdminsUsers(ResultSet rs) throws SQLException {
        UserAccount studentUser = new UserAccount();

  
        studentUser.setUserName(rs.getString("USERNAME"));
        studentUser.setPassWord(rs.getString("PASSWORD"));

        return studentUser;
    }
    public void insertNewAdmin(UserAccount userAccount) throws SQLException{
            
         try {
             Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.ADMINS_USERS (USERNAME,"
                    + " PASSWORD)"
                    + " VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, userAccount.getUserName());
           
            ps.setString(2, userAccount.getPassWord());
            
            ps.executeUpdate();

            ps.close();

           } catch (Exception ex) {
             Logger.getLogger(AdminsUsersDao.class.getName()).log(Level.SEVERE, null, ex);
         }
    
    }
}
