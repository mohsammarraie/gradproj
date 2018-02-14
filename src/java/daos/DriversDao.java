/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import models.Driver;

/**
 *
 * @author MOH
 */
public class DriversDao extends ConnectionDao {

    public ArrayList<Driver> buildDriver() throws Exception {
        ArrayList<Driver> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT DRIVER_ID,FIRST_NAME_AR,FIRST_NAME_EN,"
                    + " LAST_NAME_EN,LAST_NAME_AR,PHONE_NUMBER,"
                    + " NATIONAL_ID,DATE_OF_BIRTH,GENDER_EN,GENDER_AR,"
                    + " NATIONALITY_EN, NATIONALITY_AR,"
                    + " CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN) AS DRIVER_NAME_EN,"
                    + " CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR) AS DRIVER_NAME_AR"
                    + " FROM"
                    + " BUSES.DRIVERS ORDER BY DRIVER_ID";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateDrivers(rs));
            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Driver populateDrivers(ResultSet rs) throws SQLException {
        Driver driverInfo = new Driver();

        driverInfo.setDriverId(rs.getInt("DRIVER_ID"));
        driverInfo.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
        driverInfo.setFirstNameEn(rs.getString("FIRST_NAME_EN"));
        driverInfo.setLastNameEn(rs.getString("LAST_NAME_EN"));
        driverInfo.setLastNameAr(rs.getString("LAST_NAME_AR"));
        driverInfo.setPhoneNumber(rs.getString("PHONE_NUMBER"));
        driverInfo.setNationalId(rs.getString("NATIONAL_ID"));
        driverInfo.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
        driverInfo.setGenderEn(rs.getString("GENDER_EN"));
        driverInfo.setNationalityEn(rs.getString("NATIONALITY_EN"));
        driverInfo.setGenderAr(rs.getString("GENDER_AR"));
        driverInfo.setNationalityAr(rs.getString("NATIONALITY_AR"));
        driverInfo.setDriverNameEn(rs.getString("DRIVER_NAME_EN"));
        driverInfo.setDriverNameAr(rs.getString("DRIVER_NAME_AR"));
        return driverInfo;
    }

    public void insertDriver(Driver drivers) throws Exception {
        try {
            Connection conn = getConnection();

            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.DRIVERS";
            PreparedStatement preparedStm = conn.prepareStatement(query);
            ResultSet resultSet = preparedStm.executeQuery();

            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt("ROW_COUNTER");
            }
            String countOrMax = "";
            if (count > 0) {
                countOrMax = "max";
            } else {
                countOrMax = "count";
            }

            String sql = "INSERT INTO BUSES.DRIVERS (DRIVER_ID,"
                    + " FIRST_NAME_EN,"
                    + " FIRST_NAME_AR,"
                    + " LAST_NAME_EN,"
                    + " LAST_NAME_AR,"
                    + " PHONE_NUMBER,"
                    + " NATIONAL_ID,"
                    + " GENDER_EN,"
                    + " DATE_OF_BIRTH,"
                    + " NATIONALITY_EN,"
                    + " GENDER_AR,"
                    + " NATIONALITY_AR)"
                    + " VALUES ((select " + countOrMax + "(DRIVER_ID) from BUSES.DRIVERS)+1,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.Date sqlStartDate = new java.sql.Date(drivers.getDateOfBirth().getTime());

            ps.setString(1, drivers.getFirstNameEn());
            ps.setString(2, drivers.getFirstNameAr());
            ps.setString(3, drivers.getLastNameEn());
            ps.setString(4, drivers.getLastNameAr());
            ps.setString(5, drivers.getPhoneNumber());
            ps.setString(6, drivers.getNationalId());
            ps.setString(7, drivers.getGenderEn());
            ps.setDate(8, sqlStartDate);
            ps.setString(9, drivers.getNationalityEn());
            ps.setString(10, drivers.getGenderAr());
            ps.setString(11, drivers.getNationalityAr());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateDriver(Driver drivers) throws Exception {
        try {
            Connection conn = getConnection();
            java.sql.Date sqlStartDate = new java.sql.Date(drivers.getDateOfBirth().getTime());
            String sql = "UPDATE BUSES.DRIVERS SET"
                    + " FIRST_NAME_EN=?,"
                    + " FIRST_NAME_AR=?,"
                    + " LAST_NAME_EN=?,"
                    + " LAST_NAME_AR=?,"
                    + " PHONE_NUMBER=?,"
                    + " NATIONAL_ID=?,"
                    + " GENDER_EN=?,"
                    + " DATE_OF_BIRTH=?,"
                    + " NATIONALITY_EN=?,"
                    + " GENDER_AR=?,"
                    + " NATIONALITY_AR=?"
                    + " WHERE DRIVER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, drivers.getFirstNameEn());
            ps.setString(2, drivers.getFirstNameAr());
            ps.setString(3, drivers.getLastNameEn());
            ps.setString(4, drivers.getLastNameAr());
            ps.setString(5, drivers.getPhoneNumber());
            ps.setString(6, drivers.getNationalId());
            ps.setString(7, drivers.getGenderEn());
            ps.setDate(8, sqlStartDate);
            ps.setString(9, drivers.getNationalityEn());
            ps.setString(10, drivers.getGenderAr());
            ps.setString(11, drivers.getNationalityAr());
            ps.setInt(12, drivers.getDriverId());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteDriver(int driverId) throws Exception {
        Connection conn = getConnection();

        try {
            String sql = "DELETE FROM BUSES.DRIVERS WHERE DRIVER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, driverId);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Driver getDrivers(int driverId) throws Exception {
        try {
            Driver drivers = null;
            Connection conn = getConnection();

            String sql = "SELECT DRIVER_ID,FIRST_NAME_AR,FIRST_NAME_EN,"
                    + " LAST_NAME_EN,LAST_NAME_AR,PHONE_NUMBER,"
                    + " NATIONAL_ID,DATE_OF_BIRTH,GENDER_EN,GENDER_AR,"
                    + " NATIONALITY_EN, NATIONALITY_AR,"
                    + " CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN) AS DRIVER_NAME_EN,"
                    + " CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR) AS DRIVER_NAME_AR"
                    + " FROM BUSES.DRIVERS"
                    + " WHERE DRIVER_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, driverId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                drivers = populateDrivers(rs);

            }

            rs.close();
            ps.close();

            return drivers;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
