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
import models.BusDriver;

/**
 *
 * @author MOH
 */
public class BusesDriversDao extends ConnectionDao {

    public ArrayList<BusDriver> buildAvailableBusesDrivers() throws Exception {
        ArrayList<BusDriver> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
//            String sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN),', '),NATIONAL_ID) AS DRIVER_NAME_EN,"
//                    + " CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR),', '),NATIONAL_ID) AS DRIVER_NAME_AR,"
//                    + " DRIVER_ID,NATIONAL_ID"
//                    + " FROM BUSES.DRIVERS "
//                    + " WHERE DRIVER_ID NOT IN (SELECT DRIVER_ID FROM BUSES_DRIVERS)"
//                    + " ORDER BY DRIVER_ID";

            String sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN),', '),NATIONAL_ID) AS DRIVER_NAME_EN,"
                    + " CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR),', '),NATIONAL_ID) AS DRIVER_NAME_AR,"
                    + " DRIVER_ID,NATIONAL_ID"
                    + " FROM BUSES.DRIVERS "
                    + " ORDER BY DRIVER_ID";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateAvailableBusesDrivers(rs));
            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private BusDriver populateAvailableBusesDrivers(ResultSet rs) throws SQLException {
        BusDriver driverInfo = new BusDriver();

        driverInfo.setDriverId(rs.getInt("DRIVER_ID"));
        driverInfo.setDriverNameEn(rs.getString("DRIVER_NAME_EN"));
        driverInfo.setDriverNameAr(rs.getString("DRIVER_NAME_AR"));
        driverInfo.setNationalId(rs.getString("NATIONAL_ID"));

        return driverInfo;
    }

    public ArrayList<BusDriver> buildBusesDrivers() throws Exception {
        ArrayList<BusDriver> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN),', '),NATIONAL_ID) AS DRIVER_NAME_EN,"
                    + " CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR),', '),NATIONAL_ID) AS DRIVER_NAME_AR,"
                    + " BUSES.BUSES_DRIVERS.DRIVER_ID,NATIONAL_ID,BUSES.BUSES_DRIVERS.BUS_ID"
                    + " FROM BUSES.DRIVERS,BUSES.BUSES_DRIVERS "
                    + " WHERE BUSES.BUSES_DRIVERS.DRIVER_ID = BUSES.DRIVERS.DRIVER_ID"
                    + " ORDER BY DRIVER_ID";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateBusesDrivers(rs));
            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private BusDriver populateBusesDrivers(ResultSet rs) throws SQLException {
        BusDriver driverInfo = new BusDriver();

        driverInfo.setDriverId(rs.getInt("DRIVER_ID"));
        driverInfo.setDriverNameEn(rs.getString("DRIVER_NAME_EN"));
        driverInfo.setDriverNameAr(rs.getString("DRIVER_NAME_AR"));
        driverInfo.setNationalId(rs.getString("NATIONAL_ID"));
        driverInfo.setBusId(rs.getInt("BUS_ID"));
        return driverInfo;
    }

    public void insertBusDriver(int busId, int driverId) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.BUSES_DRIVERS (DRIVER_ID,"
                    + " BUS_ID)"
                    + " VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, driverId);
            ps.setInt(2, busId);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateBusDriver(int busId, int driverId) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.BUSES_DRIVERS SET"
                    + " DRIVER_ID=?"
                    + " WHERE BUS_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, driverId);
            ps.setInt(2, busId);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteBusDriver(int busId) throws Exception {
        Connection conn = getConnection();

        try {
            String sql = "DELETE FROM BUSES.BUSES_DRIVERS WHERE BUS_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, busId);

            ps.executeUpdate();

            ps.close();

            String sql1 = "COMMIT";
            PreparedStatement ps1 = conn.prepareStatement(sql1);

            ps1.executeUpdate();

            ps1.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public BusDriver getBusesDrivers(int busId) throws Exception {
        try {
            BusDriver busesDrivers = null;
            Connection conn = getConnection();

            String sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN),', '),NATIONAL_ID) AS DRIVER_NAME_EN,"
                    + " CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR),', '),NATIONAL_ID) AS DRIVER_NAME_AR,"
                    + " BUSES.BUSES_DRIVERS.DRIVER_ID,NATIONAL_ID,BUSES.BUSES_DRIVERS.BUS_ID"
                    + " FROM BUSES.DRIVERS,BUSES.BUSES_DRIVERS "
                    + " WHERE BUSES.BUSES_DRIVERS.DRIVER_ID = BUSES.DRIVERS.DRIVER_ID"
                    + " AND"
                    + " BUS_ID=?"
                    + " ORDER BY DRIVER_ID";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, busId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                busesDrivers = populateBusesDrivers(rs);
            }

            rs.close();
            ps.close();

            return busesDrivers;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      public ArrayList<BusDriver> buildBusDriverInfo(int driverId) throws Exception {
        ArrayList<BusDriver> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT * FROM BUSES.BUSES, BUSES.DRIVERS, BUSES.BUSES_DRIVERS"
                    + " WHERE"
                    + " BUSES.BUSES.BUS_ID = BUSES.BUSES_DRIVERS.BUS_ID"
                    + " AND"
                    + " BUSES.DRIVERS.DRIVER_ID=BUSES.BUSES_DRIVERS.DRIVER_ID"
                    + " AND"
                    + " BUSES.DRIVERS.DRIVER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 1); //selected driverId instead of 1
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateBusDriverInfo(rs));
            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private BusDriver populateBusDriverInfo(ResultSet rs) throws SQLException {
        BusDriver driverInfo = new BusDriver();

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
        driverInfo.setBusId(rs.getInt("BUS_ID"));
        driverInfo.setModel(rs.getInt("MODEL"));
        driverInfo.setChasisNumber(rs.getInt("CHASIS_NUMBER"));
        driverInfo.setLicenseNumber(rs.getString("LICENSE_NUMBER"));
        driverInfo.setManufacturer(rs.getString("MANUFACTURER"));
        driverInfo.setCapacity(rs.getInt("CAPACITY"));

        return driverInfo;
    }

}
