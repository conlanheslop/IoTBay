package main.java.controller;

import model.Delivery;
import model.dao.DeliveryManager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DeliveryController extends HttpServlet {

    private DeliveryManager deliveryManager;

    @Override
    public void init() {
        try {
            ServletContext context = getServletContext();
            Connection conn = (Connection) context.getAttribute("dbconn");
            deliveryManager = new DeliveryManager(conn);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DeliveryController", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addDelivery(request);
                    break;
                case "update":
                    updateDelivery(request);
                    break;
                case "delete":
                    deleteDelivery(request);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
        }

        response.sendRedirect("delivery.jsp");
    }

    private void addDelivery(HttpServletRequest request) throws Exception {
        String deliveryId = request.getParameter("deliveryId");
        String orderId = request.getParameter("orderId");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("deliveringDate"));
        String status = request.getParameter("status");
        String address = request.getParameter("deliveringAddress");
        String name = request.getParameter("nameOnDelivery");
        String tracking = request.getParameter("trackingNumber");

        deliveryManager.addDelivery(deliveryId, orderId, date, status, address, name, tracking);
    }

    private void updateDelivery(HttpServletRequest request) throws Exception {
        String deliveryId = request.getParameter("deliveryId");
        String orderId = request.getParameter("orderId");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("deliveringDate"));
        String status = request.getParameter("status");
        String address = request.getParameter("deliveringAddress");
        String name = request.getParameter("nameOnDelivery");
        String tracking = request.getParameter("trackingNumber");

        deliveryManager.updateDelivery(deliveryId, orderId, date, status, address, name, tracking);
    }

    private void deleteDelivery(HttpServletRequest request) throws Exception {
        String deliveryId = request.getParameter("deliveryId");
        deliveryManager.deleteDelivery(deliveryId);
    }
}
