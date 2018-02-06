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
            String sql = "SELECT * FROM BUSES.DRIVERS ORDER BY DRIVER_ID";
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

    public HashMap<Integer, Driver> buildDriverMap() throws Exception {
        HashMap<Integer, Driver> map = new HashMap<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT * FROM BUSES.DRIVERS ORDER BY DRIVER_ID";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Driver driverInfo = populateDrivers(rs);
                map.put(driverInfo.getDriverId(), driverInfo);
            }

            rs.close();
            ps.close();

            return map;
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
        return driverInfo;
    }

    public void insertDriver(Driver drivers) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.DRIVERS (DRIVER_ID,"
                    + " FIRST_NAME_EN,"
                    + " FIRST_NAME_AR,"
                    + " LAST_NAME_EN,"
                    + " LAST_NAME_AR,"
                    + " PHONE_NUMBER,"
                    + " NATIONAL_ID)"
                    + " VALUES ((select max(DRIVER_ID) from BUSES.DRIVERS)+1,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, drivers.getFirstNameEn());
            ps.setString(2, drivers.getFirstNameAr());
            ps.setString(3, drivers.getLastNameEn());
            ps.setString(4, drivers.getLastNameAr());
            ps.setString(5, drivers.getPhoneNumber());
            ps.setString(6, drivers.getNationalId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateDriver(Driver drivers) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.DRIVERS SET"
                    + " FIRST_NAME_EN=?,"
                    + " FIRST_NAME_AR=?,"
                    + " LAST_NAME_EN=?,"
                    + " LAST_NAME_AR=?,"
                    + " PHONE_NUMBER=?,"
                    + " NATIONAL_ID=?"
                    + " WHERE DRIVER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, drivers.getFirstNameEn());
            ps.setString(2, drivers.getFirstNameAr());
            ps.setString(3, drivers.getLastNameEn());
            ps.setString(4, drivers.getLastNameAr());
            ps.setString(5, drivers.getPhoneNumber());
            ps.setString(6, drivers.getNationalId());
            ps.setInt(7, drivers.getDriverId());

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

            String sql = "SELECT * FROM BUSES.DRIVERS"
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
