package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.DeliveryManager;
import model.Delivery;

@WebServlet("/updateDeliveryStatus")
public class UpdateDeliveryStatusServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws ServletException, IOException {
    HttpSession session = request.getSession();
    DeliveryManager deliveryManager = (DeliveryManager) session.getAttribute(
      "deliveryManager"
    );

    try {
      String deliveryId = request.getParameter("deliveryId");
      String status = request.getParameter("status");

      Delivery delivery = deliveryManager.findDelivery(deliveryId);

      if (delivery != null) {
        // Update only the status
        Date deliveringDate = delivery.getDeliveringDate();
        String orderId = delivery.getOrderId();
        String deliveringAddress = delivery.getDeliveringAddress();
        String nameOnDelivery = delivery.getNameOnDelivery();
        String trackingNumber = delivery.getTrackingNumber();

        deliveryManager.updateDelivery(
          deliveryId,
          orderId,
          deliveringDate,
          status,
          deliveringAddress,
          nameOnDelivery,
          trackingNumber
        );

        session.setAttribute(
          "successMessage",
          "Delivery status updated successfully!"
        );
      } else {
        request.setAttribute(
          "errorMessage",
          "Delivery not found with ID: " + deliveryId
        );
      }
      response.sendRedirect(
        "delivery?action=view-single&deliveryId=" + deliveryId
      );
    } catch (SQLException e) {
      request.setAttribute(
        "errorMessage",
        "Error updating delivery status: " + e.getMessage()
      );
      response.sendRedirect("delivery?action=list");
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error updating delivery status: " + e.getMessage()
      );
      response.sendRedirect("delivery?action=list");
    }
  }
}
