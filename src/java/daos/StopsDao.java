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
import models.Stop;

/**
 *
 * @author MOH
 */
public class StopsDao extends ConnectionDao {

    public ArrayList<Stop> buildStops() throws Exception {
        ArrayList<Stop> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT * FROM BUSES.STOPS ORDER BY STOP_ID";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateStops(rs));
            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public HashMap<Integer, Stop> buildStopsMap() throws Exception {
        HashMap<Integer, Stop> map = new HashMap<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT * FROM BUSES.STOPS  ORDER BY STOP_ID";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Stop stopsInfo = populateStops(rs);
                map.put(stopsInfo.getStopId(), stopsInfo);
            }

            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Stop populateStops(ResultSet rs) throws SQLException {
        Stop stops = new Stop();

        stops.setStopId(rs.getInt("STOP_ID"));
        stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stops.setStopNameAr(rs.getString("STOP_NAME_AR"));

        return stops;
    }

    public void insertStop(Stop stops) throws Exception {
        try {
            Connection conn = getConnection();

            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.STOPS";
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

            String sql = "INSERT INTO BUSES.STOPS  (STOP_ID,"
                    + " STOP_NAME_EN,"
                    + " STOP_NAME_AR)"
                    + " VALUES ((select " + countOrMax + "(STOP_ID) from BUSES.STOPS)+1,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, stops.getStopNameEn());
            ps.setString(2, stops.getStopNameAr());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateStop(Stop stops) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.STOPS SET"
                    + " STOP_NAME_EN=?,"
                    + " STOP_NAME_AR=?"
                    + " WHERE STOP_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, stops.getStopNameEn());
            ps.setString(2, stops.getStopNameAr());
            ps.setInt(3, stops.getStopId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteStop(int stopId) throws Exception {
        Connection conn = getConnection();

        try {
            String sql = "DELETE FROM BUSES.STOPS WHERE STOP_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, stopId);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Stop getStops(int stopId) throws Exception {
        try {
            Stop stops = null;
            Connection conn = getConnection();

            String sql = "SELECT * FROM BUSES.STOPS"
                    + " WHERE STOP_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, stopId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                stops = populateStops(rs);
                stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
                stops.setStopNameAr(rs.getString("STOP_NAME_AR"));

            }

            rs.close();
            ps.close();

            return stops;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
