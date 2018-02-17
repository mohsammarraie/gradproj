/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.AdminsUsersDao;
import daos.DriversUsersDao;
import daos.StudentsUsersDao;
import models.UserAccount;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "loginBean")
@ViewScoped
public class LogInBean implements Serializable {

    private String username;
    private String password;
    private final AdminsUsersDao adminsUsersDao = new AdminsUsersDao();
    private final DriversUsersDao driversUsersDao = new DriversUsersDao();
    private final StudentsUsersDao studentUserDao = new StudentsUsersDao();
    private ArrayList<UserAccount> studentUsersArray;
    private ArrayList<UserAccount> driverUsersArray;
    private ArrayList<UserAccount> adminUsersArray;

    String error_message_header = "";
    String error_message_content = "";
    @Inject
    private SessionBean sessionBean;

    public LogInBean() {
    }

    @PostConstruct
    public void init() {
        try {
            adminUsersArray = adminsUsersDao.buildAdminsUsers();
            driverUsersArray = driversUsersDao.buildDriverUsers();
            studentUsersArray = studentUserDao.buildStudentUsers();
        } catch (Exception ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<UserAccount> getAdminUsersArray() {
        return adminUsersArray;
    }

    public void setAdminUsersArray(ArrayList<UserAccount> adminUsersArray) {
        this.adminUsersArray = adminUsersArray;
    }

    public ArrayList<UserAccount> getDriverUsersArray() {
        return driverUsersArray;
    }

    public void setDriverUsersArray(ArrayList<UserAccount> driverUsersArray) {
        this.driverUsersArray = driverUsersArray;
    }

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

    public ArrayList<UserAccount> getStudentUsersArray() {
        return studentUsersArray;
    }

    public void setStudentUsersArray(ArrayList<UserAccount> StudentUsersArray) {
        this.studentUsersArray = StudentUsersArray;
    }

    public void loginAsStudent() throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean success = false;
        int i = 0;
        try {

            for (i = 0; i < studentUsersArray.size(); i++) {
                if (username.equals(studentUsersArray.get(i).getUserName()) && password.equals(studentUsersArray.get(i).getPassWord())) {
                    sessionBean.setStudentUserId(username);
                    success = true;
                } else {
                    error_message_header = "Error!";
                    error_message_content = "Incorrect username or password.";
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));

                }
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (success) {
            sessionBean.navigate("/first_page_student");
        }
    }

    public void loginAsDriver() throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean success = false;
        int i = 0;
        try {

            for (i = 0; i < driverUsersArray.size(); i++) {
                if (username.equals(driverUsersArray.get(i).getUserName()) && password.equals(driverUsersArray.get(i).getPassWord())) {
                    sessionBean.setDriverUserNationalId(username);
                    success = true;
                } else {
                    error_message_header = "Error!";
                    error_message_content = "Incorrect username or password.";
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));

                }
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (success) {
            sessionBean.navigate("/first_page_driver");
        }
    }

    public void loginAsAdmin() throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean success = false;
        int i = 0;
        try {

            for (i = 0; i < adminUsersArray.size(); i++) {
                if (username.equals(adminUsersArray.get(i).getUserName()) && password.equals(adminUsersArray.get(i).getPassWord())) {
                    sessionBean.setAdminUserName(username);
                    success = true;
                } else {
                    error_message_header = "Error!";
                    error_message_content = "Incorrect username or password.";
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));

                }
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (success) {
            sessionBean.navigate("/first_page");
        }
    }

    public void logout() throws Exception {
        try {
            // Release and close database resources and connections 
            if (sessionBean.getConnection() != null) {
                if (!sessionBean.getConnection().getAutoCommit()) {
                    sessionBean.getConnection().rollback();
                    sessionBean.getConnection().setAutoCommit(true);
                }

                sessionBean.getConnection().close();
                sessionBean.setConnection(null);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            setPassword(null);
            setUsername(null);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().invalidateSession();
        }
    }

}
