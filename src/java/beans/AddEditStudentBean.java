/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.StudentsDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Student;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "addEditStudentBean")
@ViewScoped
public class AddEditStudentBean implements Serializable {

    private final StudentsDao studentsDao = new StudentsDao();
    private String studentId;
    private String firstNameEn;
    private String firstNameAr;
    private String lastNameEn;
    private String lastNameAr;
    private int studentIncId;
    String error_message_header = "";
    String error_message_content = "";
    
    @Inject
    private SessionBean sessionBean;

    public AddEditStudentBean() {
    }

    @PostConstruct
    public void init() {
        try {
            studentIncId = sessionBean.getSelectedStudentIncId();

            if (studentIncId >0) {

                Student students = studentsDao.getStudents(studentIncId);
                if(students!=null){
                    studentId= students.getStudentId();
                    firstNameEn = students.getFirstNameEn();
                    firstNameAr = students.getFirstNameAr();
                    lastNameEn = students.getLastNameEn();
                    lastNameAr = students.getLastNameAr();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getStudentIncId() {
        return studentIncId;
    }

    public void setStudentIncId(int studentIncId) {
        this.studentIncId = studentIncId;
    }
    
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public String getFirstNameAr() {
        return firstNameAr;
    }

    public void setFirstNameAr(String firstNameAr) {
        this.firstNameAr = firstNameAr;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public String getLastNameAr() {
        return lastNameAr;
    }

    public void setLastNameAr(String lastNameAr) {
        this.lastNameAr = lastNameAr;
    }

    public void saveStudent() {
        try {

            Student students = new Student();
            students.setStudentId(studentId);
            students.setFirstNameEn(firstNameEn);
            students.setFirstNameAr(firstNameAr);
            students.setLastNameEn(lastNameEn);
            students.setLastNameAr(lastNameAr);

            if (sessionBean.getSelectedStudentIncId() > 0) {
                studentsDao.updateStudent(students, sessionBean.getSelectedStudentIncId());
            } else {
                studentsDao.insertStudent(students);
            }
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(AddEditStudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigateManageStudents();
    }
}
