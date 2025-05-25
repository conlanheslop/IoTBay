package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.DeliveryManager;
import model.Delivery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/delivery")
public class DeliveryServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws ServletException, IOException {
    HttpSession session = request.getSession();
    DeliveryManager deliveryManager = (DeliveryManager) session.getAttribute(
      "deliveryManager"
    );

    String action = request.getParameter("action");

    if (action == null) {
      action = "list";
    }

    switch (action) {
      case "create-form":
        showCreateForm(request, response);
        break;
      case "view-single":
        viewSingleDelivery(request, response, deliveryManager);
        break;
      case "update-form":
        showUpdateForm(request, response, deliveryManager);
        break;
      case "list":
      default:
        listDeliveries(request, response, deliveryManager);
        break;
    }
  }

  @Override
  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws ServletException, IOException {
    HttpSession session = request.getSession();
    DeliveryManager deliveryManager = (DeliveryManager) session.getAttribute(
      "deliveryManager"
    );

    String action = request.getParameter("action");

    if (action == null) {
      action = "list";
    }

    switch (action) {
      case "create":
        createDelivery(request, response, session, deliveryManager);
        break;
      case "update":
        updateDelivery(request, response, session, deliveryManager);
        break;
      case "delete":
        deleteDelivery(request, response, session, deliveryManager);
        break;
      case "search":
        searchDeliveries(request, response, deliveryManager);
        break;
      default:
        listDeliveries(request, response, deliveryManager);
        break;
    }
  }

  private void showCreateForm(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher(
      "delivery_form.jsp" // CORRECTED: This should be delivery_form.jsp
      // (your create form)
    );
    dispatcher.forward(request, response);
  }

  private void viewSingleDelivery(
    HttpServletRequest request,
    HttpServletResponse response,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      String deliveryId = request.getParameter("deliveryId");
      Delivery delivery = deliveryManager.findDelivery(deliveryId);

      if (delivery != null) {
        request.setAttribute("delivery", delivery);
        RequestDispatcher dispatcher = request.getRequestDispatcher(
          "delivery_details.jsp" // CORRECTED: This should be delivery_details.jsp
        );
        dispatcher.forward(request, response);
      } else {
        request.setAttribute(
          "errorMessage",
          "Delivery not found with ID: " + deliveryId
        );
        listDeliveries(request, response, deliveryManager);
      }
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error retrieving delivery: " + e.getMessage()
      );
      listDeliveries(request, response, deliveryManager);
    }
  }

  private void showUpdateForm(
    HttpServletRequest request,
    HttpServletResponse response,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      String deliveryId = request.getParameter("deliveryId");
      Delivery delivery = deliveryManager.findDelivery(deliveryId);

      if (delivery != null) {
        request.setAttribute("delivery", delivery);
        RequestDispatcher dispatcher = request.getRequestDispatcher(
          "delivery_update.jsp" // CORRECTED: This should be delivery_update.jsp
        );
        dispatcher.forward(request, response);
      } else {
        request.setAttribute(
          "errorMessage",
          "Delivery not found with ID: " + deliveryId
        );
        listDeliveries(request, response, deliveryManager);
      }
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error retrieving delivery: " + e.getMessage()
      );
      listDeliveries(request, response, deliveryManager);
    }
  }

  private void listDeliveries(
    HttpServletRequest request,
    HttpServletResponse response,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      List<Delivery> deliveries = deliveryManager.fetchAllDeliveries();
      request.setAttribute("deliveries", deliveries);
      RequestDispatcher dispatcher = request.getRequestDispatcher(
        "delivery_list.jsp" // CORRECTED: This should be delivery_list.jsp
      );
      dispatcher.forward(request, response);
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error retrieving deliveries: " + e.getMessage()
      );
      RequestDispatcher dispatcher = request.getRequestDispatcher(
        "delivery_list.jsp" // CORRECTED: This should be delivery_list.jsp
      );
      dispatcher.forward(request, response);
    }
  }

  private void createDelivery(
    HttpServletRequest request,
    HttpServletResponse response,
    HttpSession session,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      // Generate a unique delivery ID
      String deliveryId =
        "DEL" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

      String orderId = request.getParameter("orderId");
      String deliveringDateStr = request.getParameter("deliveringDate");
      String status = request.getParameter("status");
      String deliveringAddress = request.getParameter("deliveringAddress");
      String nameOnDelivery = request.getParameter("nameOnDelivery");
      String trackingNumber = request.getParameter("trackingNumber");

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
      Date deliveringDate = sdf.parse(deliveringDateStr);

      deliveryManager.addDelivery(
        deliveryId,
        orderId,
        deliveringDate,
        status,
        deliveringAddress,
        nameOnDelivery,
        trackingNumber
      );

      session.setAttribute("successMessage", "Delivery created successfully!");
      response.sendRedirect("delivery?action=list");
    } catch (ParseException e) {
      request.setAttribute(
        "errorMessage",
        "Invalid date format: " + e.getMessage()
      );
      showCreateForm(request, response);
    } catch (SQLException e) {
      request.setAttribute(
        "errorMessage",
        "Error creating delivery: " + e.getMessage()
      );
      showCreateForm(request, response);
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error creating delivery: " + e.getMessage()
      );
      showCreateForm(request, response);
    }
  }

  private void updateDelivery(
    HttpServletRequest request,
    HttpServletResponse response,
    HttpSession session,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      String deliveryId = request.getParameter("deliveryId");
      String orderId = request.getParameter("orderId");
      String deliveringDateStr = request.getParameter("deliveringDate");
      String status = request.getParameter("status");
      String deliveringAddress = request.getParameter("deliveringAddress");
      String nameOnDelivery = request.getParameter("nameOnDelivery");
      String trackingNumber = request.getParameter("trackingNumber");

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
      Date deliveringDate = sdf.parse(deliveringDateStr);

      deliveryManager.updateDelivery(
        deliveryId,
        orderId,
        deliveringDate,
        status,
        deliveringAddress,
        nameOnDelivery,
        trackingNumber
      );

      session.setAttribute("successMessage", "Delivery updated successfully!");
      // Changed redirect to go back to the delivery list
      response.sendRedirect("delivery?action=list");
    } catch (ParseException e) {
      request.setAttribute(
        "errorMessage",
        "Invalid date format: " + e.getMessage()
      );
      showUpdateForm(request, response, deliveryManager);
    } catch (SQLException e) {
      request.setAttribute(
        "errorMessage",
        "Error updating delivery: " + e.getMessage()
      );
      response.sendRedirect("delivery?action=list");
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error updating delivery: " + e.getMessage()
      );
      response.sendRedirect("delivery?action=list");
    }
  }

  private void deleteDelivery(
    HttpServletRequest request,
    HttpServletResponse response,
    HttpSession session,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      String deliveryId = request.getParameter("deliveryId");

      deliveryManager.deleteDelivery(deliveryId);

      session.setAttribute("successMessage", "Delivery deleted successfully!");

      response.sendRedirect("delivery?action=list");
    } catch (SQLException e) {
      request.setAttribute(
        "errorMessage",
        "Error deleting delivery: " + e.getMessage()
      );
      listDeliveries(request, response, deliveryManager);
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error deleting delivery: " + e.getMessage()
      );
      listDeliveries(request, response, deliveryManager);
    }
  }

  private void searchDeliveries(
    HttpServletRequest request,
    HttpServletResponse response,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    String searchTerm = request.getParameter("searchTerm");
    String searchDateStr = request.getParameter("searchDate");
    List<Delivery> deliveries = new ArrayList<>();
    String errorMessage = null;

    try {
      LocalDate searchDate = null;
      if (searchDateStr != null && !searchDateStr.trim().isEmpty()) {
        try {
          searchDate = LocalDate.parse(searchDateStr);
        } catch (DateTimeParseException e) {
          errorMessage = "Invalid date format. Please use YYYY-MM-DD.";
        }
      }

      // Check if both search term and search date are provided
      if (
        (searchTerm != null && !searchTerm.trim().isEmpty()) &&
        searchDate != null
      ) {
        // Search by both search term and date (AND condition)
        deliveries = deliveryManager.searchDeliveries(searchTerm, searchDate);
      } else if (searchTerm != null && !searchTerm.trim().isEmpty()) {
        // Search by search term only
        deliveries = deliveryManager.searchDeliveriesByID(searchTerm);
      } else if (searchDate != null) {
        // Search by date only
        deliveries = deliveryManager.searchDeliveriesByDate(searchDate);
      } else {
        // No search criteria provided, fetch all deliveries
        deliveries = deliveryManager.fetchAllDeliveries();
      }
    } catch (Exception e) {
      errorMessage = "Error searching deliveries: " + e.getMessage();
    } finally {
      request.setAttribute("deliveries", deliveries);
      request.setAttribute("searchTerm", searchTerm);
      request.setAttribute("searchDate", searchDateStr);
      if (errorMessage != null) {
        request.setAttribute("errorMessage", errorMessage);
      }
      RequestDispatcher dispatcher = request.getRequestDispatcher(
        "delivery_list.jsp"
      );
      dispatcher.forward(request, response);
    }
  }
}
