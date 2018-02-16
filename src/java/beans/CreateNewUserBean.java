/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.AdminsUsersDao;
import daos.DriversUsersDao;
import daos.StudentsUsersDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Bus;
import models.UserAccount;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "createNewUserBean")
@ViewScoped
public class CreateNewUserBean implements Serializable {

    private String username;
    private String password;
    private final AdminsUsersDao adminsUsersDao = new AdminsUsersDao();
    private final DriversUsersDao driversUsersDao = new DriversUsersDao();
    private final StudentsUsersDao studentUserDao = new StudentsUsersDao();
    UserAccount userAccount = new UserAccount();
    String error_message_header = "";
    String error_message_content = "";

    public CreateNewUserBean() {
    }
      @Inject
    private SessionBean sessionBean;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void insertAdmin() {
        userAccount.setUserName(username);
        userAccount.setPassWord(password);
        try {
            adminsUsersDao.insertNewAdmin(userAccount);
            sessionBean.navigate("/first_page");
        } catch (SQLException ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(CreateNewUserBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void insertDriver() {
        userAccount.setUserName(username);
        userAccount.setPassWord(password);
        try {
            driversUsersDao.insertNewDriver(userAccount);
            sessionBean.navigate("/first_page");
        } catch (SQLException ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(CreateNewUserBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
      public void insertStudent() {
        userAccount.setUserName(username);
        userAccount.setPassWord(password);
        try {
            studentUserDao.insertNewStudent(userAccount);
            sessionBean.navigate("/first_page");
        } catch (SQLException ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(CreateNewUserBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
