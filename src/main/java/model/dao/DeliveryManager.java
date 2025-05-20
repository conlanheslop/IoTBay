package model.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Delivery; // assuming you have a Delivery POJO class

public class DeliveryManager {

    private Statement st;

    public DeliveryManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // Add a delivery
    public void addDelivery(String deliveryId, String orderId, Date deliveringDate, String status,
                            String deliveringAddress, String nameOnDelivery, String trackingNumber) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(deliveringDate);

        String query = "INSERT INTO Delivery (deliveryId, orderId, deliveringDate, status, deliveringAddress, nameOnDelivery, trackingNumber) " +
                "VALUES ('" + deliveryId + "', '" + orderId + "', '" + dateStr + "', '" + status + "', '" +
                deliveringAddress + "', '" + nameOnDelivery + "', '" + trackingNumber + "')";
        st.executeUpdate(query);
    }

    // Find a delivery by ID
    public Delivery findDelivery(String deliveryId) throws SQLException {
        String query = "SELECT * FROM Delivery WHERE deliveryId = '" + deliveryId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String orderId = rs.getString("orderId");
            Date deliveringDate = rs.getTimestamp("deliveringDate");
            String status = rs.getString("status");
            String deliveringAddress = rs.getString("deliveringAddress");
            String nameOnDelivery = rs.getString("nameOnDelivery");
            String trackingNumber = rs.getString("trackingNumber");

            return new Delivery(deliveryId, orderId, deliveringDate, status, deliveringAddress, nameOnDelivery, trackingNumber);
        }
        return null;
    }

    // Update a delivery record
    public void updateDelivery(String deliveryId, String orderId, Date deliveringDate, String status,
                               String deliveringAddress, String nameOnDelivery, String trackingNumber) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(deliveringDate);

        String query = "UPDATE Delivery SET orderId='" + orderId + "', deliveringDate='" + dateStr +
                "', status='" + status + "', deliveringAddress='" + deliveringAddress +
                "', nameOnDelivery='" + nameOnDelivery + "', trackingNumber='" + trackingNumber +
                "' WHERE deliveryId='" + deliveryId + "'";
        st.executeUpdate(query);
    }

    // Delete a delivery
    public void deleteDelivery(String deliveryId) throws SQLException {
        String query = "DELETE FROM Delivery WHERE deliveryId='" + deliveryId + "'";
        st.executeUpdate(query);
    }

    // Fetch all deliveries
    public List<Delivery> fetchAllDeliveries() throws SQLException {
        List<Delivery> deliveries = new ArrayList<>();
        String query = "SELECT * FROM Delivery";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String deliveryId = rs.getString("deliveryId");
            String orderId = rs.getString("orderId");
            Date deliveringDate = rs.getTimestamp("deliveringDate");
            String status = rs.getString("status");
            String deliveringAddress = rs.getString("deliveringAddress");
            String nameOnDelivery = rs.getString("nameOnDelivery");
            String trackingNumber = rs.getString("trackingNumber");

            deliveries.add(new Delivery(deliveryId, orderId, deliveringDate, status, deliveringAddress, nameOnDelivery, trackingNumber));
        }
        return deliveries;
    }
}
