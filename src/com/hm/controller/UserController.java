package com.hm.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.hm.model.Reservation;
import com.hm.model.User;
import com.hm.service.CustomerService;
import com.hm.service.ReservationService;
import com.hm.util.Utility;
import java.sql.Date;

import java.io.IOException;

public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public UserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	private CustomerService custServ;
	private ReservationService rServ;

	@Override
	public void init() throws ServletException {
		super.init();

		custServ = new CustomerService();
		rServ = new ReservationService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");

		switch (action) {
		case "logoutAdmin":
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
//			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
//			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
//			response.setDateHeader("Expires", 0); // Proxies
			response.sendRedirect("user/login.jsp");
			break;

		default:
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPostTestAdmin");
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		switch (action) {
		case "insertUser":

			String customerName = request.getParameter("customerName");
			String countryCode = request.getParameter("countryCode");
			String mobile = countryCode + request.getParameter("mobile");
			String email = request.getParameter("email");
			String customerId = request.getParameter("customerId");
			String address = request.getParameter("address");
//			java.sql.Date dob = Utility.convertStringToSQLDate(request.getParameter("dob"));
			String password = request.getParameter("password");

			User user = new User(customerName, mobile, email, customerId, address, password);
			System.out.println(user);

			int rowsAffected = custServ.reqisterCustomer(user);

			if (rowsAffected != 0) {
				request.setAttribute("status", "true");
				request.setAttribute("customerId", customerId);
			} else {
				request.setAttribute("status", "<font color=\"red\">Registration not successful!!.</font>");
			}

			RequestDispatcher rd = request.getRequestDispatcher("user/register.jsp");
			rd.forward(request, response);
			break;
		case "loginUser":
			customerId = request.getParameter("customerId");
			password = request.getParameter("password");
			System.out.println("logintest");

			boolean validUser = custServ.loginCustomer(customerId, password);
			if (validUser) {
				System.out.println("loginned user");
				HttpSession session = request.getSession();
				session.setAttribute("user", customerId);
				response.sendRedirect("user/dashboard.jsp");
			} else {
				request.setAttribute("errorMessage", "<font color=\"red\">Invalid username or password.</font>");
				request.getRequestDispatcher("user/login.jsp").forward(request, response);
			}

			break;

		case "reservationUser":
			Utility convDate = new Utility();

			Date checkInDate = convDate.convertStringToSQLDate(request.getParameter("checkinDate"));
			Date checkOutDate = convDate.convertStringToSQLDate(request.getParameter("checkoutDate"));
			String roomType = request.getParameter("roomPreferences");
			customerId = request.getParameter("customerId");
			String name = request.getParameter("name");
			long contact = Long.parseLong(request.getParameter("contact"));

			// Creating a Reservation object
			Reservation reservation = new Reservation(0, customerId, checkInDate, checkOutDate, roomType, name, contact,
					0);
//			System.out.println(reservation);
			rowsAffected = rServ.addReservation(reservation);

			if (rowsAffected != 0) {
				request.setAttribute("bookingId",
						"<font color=\"green\">Booking ID: " + reservation.getBookingId() + "</font>");
				request.setAttribute("status", "<font color=\"green\">Reservation  successful.</font>");
			} else {
				request.setAttribute("status", "<font color=\"red\">Currently <strong>" + roomType
						+ "</strong> room type is  not available for reservaton<br>Please try another room type!!.</font>");
			}

			rd = request.getRequestDispatcher("user/reservation.jsp");
			rd.forward(request, response);
			break;
		case "paymentUser":
			String totalAmount = request.getParameter("totalAmount");
			String reservationId = request.getParameter("reservationId");
			customerId = request.getParameter("customerId");

			request.setAttribute("totalAmount", totalAmount);
			request.setAttribute("reservationId", reservationId);
			request.setAttribute("customerId", customerId);

			// Forward to a JSP page to display updated details
			RequestDispatcher dispatcher = request.getRequestDispatcher("user/payment.jsp");
			dispatcher.forward(request, response);
			break;
		default:
			System.out.println("defaulttest");
			break;
		}
	}
}
