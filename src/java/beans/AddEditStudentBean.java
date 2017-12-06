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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Students;
/**
 *
 * @author MOH
 */
@Named(value = "addEditStudentBean")
@ViewScoped
public class AddEditStudentBean implements Serializable{
    
    private final StudentsDao studentsDao = new StudentsDao();
    private String studentId;
    private String firstNameEn;
    private String firstNameAr;
    private String lastNameEn;
    private String lastNameAr;
    
           @Inject
    private SessionBean sessionBean;

    public AddEditStudentBean() {
    }
           
    @PostConstruct
    public void init(){                
        try {
            studentId = sessionBean.getSelectedStudentId();
         
            
            if(studentId != null){
                
                Students students = studentsDao.getStudents(studentId);
                firstNameEn = students.getFirstNameEn();
                firstNameAr = students.getFirstNameAr();
                lastNameEn =  students.getLastNameEn();
                lastNameAr =  students.getLastNameAr();
               
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
            Students students = new Students();
            students.setStudentId(studentId);
            students.setFirstNameEn(firstNameEn);
            students.setFirstNameAr(firstNameAr);
            students.setLastNameEn(lastNameEn);
            students.setLastNameAr(lastNameAr);
 
            if (sessionBean.getSelectedDriverId() > 0) {
                studentsDao.updateStudent(students);
            } else {
                studentsDao.insertStudent(students);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStudentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_students");
    }
}
