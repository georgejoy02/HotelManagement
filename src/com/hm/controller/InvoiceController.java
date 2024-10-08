package com.hm.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import com.hm.service.AdminService;
import com.hm.service.InvoiceService;
import com.hm.util.Utility;

public class InvoiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InvoiceController() {
		super();

	}

	RequestDispatcher rd;

	ServletContext context;

	@Override
	public void init() throws ServletException {
		super.init();

		context = getServletContext();
		// Use context to access application resources
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InvoiceService invoiceServ = new InvoiceService(context);

		String action = request.getParameter("action");

		switch (action) {
		case "generateInvoice":
			String customerId = request.getParameter("customerId");
			int reservationId = Integer.parseInt(request.getParameter("reservationId"));

			String paymentMode = request.getParameter("paymentMode");
			String cardNumber = request.getParameter("cardNumber");
			String cardHolderName = request.getParameter("cardHolderName");
			String expiryDate = request.getParameter("expiryDate");
			String cvv = request.getParameter("cvv");
			Utility utility = new Utility();

			HttpSession session = request.getSession(false);
			String username = (String) session.getAttribute("user");
			AdminService as = new AdminService();

			boolean isAdmin = as.validateAdminServ(username);
			boolean isValid = utility.carddetailsVerify(paymentMode, cardNumber, cardHolderName, expiryDate, cvv);
			if (isAdmin) {
				System.out.println("admin verified");
			} else if (!isValid) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "card details not correct!!.");
				return;
			}
			String filePath = null;

			try {

				filePath = invoiceServ.fetchInvoiceService(customerId, reservationId);
				if (filePath == null) {
					filePath = invoiceServ.generateInvoice(reservationId, customerId);// payment updated here
				} else {
					System.out.println("file already generated");
				}

				if (filePath != null) {

					Path file = Path.of(filePath);
					if (Files.exists(file)) {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition", "inline; filename=" + file.getFileName());

						try (OutputStream out = response.getOutputStream()) {
							Files.copy(file, out);
							out.flush();
						}
					} else {
						System.out.println("file not found");
					}
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");

			}

			break;
		case "fetchInvoice":
			filePath = null;
			customerId = request.getParameter("customerId");
			reservationId = Integer.parseInt(request.getParameter("reservationId"));
			try {
				filePath = invoiceServ.fetchInvoiceService(customerId, reservationId);
				if (filePath != null) {

					Path file = Path.of(filePath);
					if (Files.exists(file)) {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition", "inline; filename=" + file.getFileName());

						try (OutputStream out = response.getOutputStream()) {
							Files.copy(file, out);
							out.flush();
						}
					} else {
						System.out.println("file not found");
					}
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");

			}

			break;

		default:
		}

	}

}
