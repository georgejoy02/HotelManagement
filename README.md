# Hotel Management

A Java Servlet/JSP based Hotel Management web application with two portals:

- **User portal**: registration, login, reservation, billing, payment, booking history
- **Admin portal**: admin login, room creation, reservation lookup, invoice generation/view

The app uses:

- **Jakarta Servlet API** (`jakarta.servlet.*`)
- **JSP**
- **JDBC + Apache Derby (Embedded)**
- **Apache PDFBox** for invoice PDF generation

---

## 1) Project Structure

- `src/com/hm/controller` → Servlets (`UserController`, `AdminController`, `InvoiceController`)
- `src/com/hm/service` → Business logic
- `src/com/hm/dao` → DB access layer
- `src/com/hm/model` → Domain models
- `WebContent` → JSP pages, static assets, `WEB-INF/web.xml`
- `query.sql` → Database schema and helper SQL

---

## 2) Prerequisites

Install the following:

1. **JDK 17+** (or JDK 11+)
2. **Apache Tomcat 10.x** (important because code uses `jakarta.servlet.*`)
3. **Apache Derby** embedded JDBC driver (`org.apache.derby.jdbc.EmbeddedDriver`)
4. **Apache PDFBox 3.x** (plus transitive dependencies)
5. An IDE that supports Dynamic Web Projects (Eclipse/STS/IntelliJ Ultimate) **or** manual WAR deployment setup

---

## 3) Required Libraries (if not already configured by your IDE)

Add these jars to your web app classpath (typically `WEB-INF/lib` or server runtime libraries):

- `derby.jar`
- `pdfbox.jar`

Also ensure your server provides:

- Jakarta Servlet API (Tomcat 10+ does this automatically)

---

## 4) Database Setup (Apache Derby)

This project is currently configured to use:

```java
jdbc:derby:/home/xntrik/MyDB;create=true
```

in `src/com/hm/constants/DBconstants.java`.

### Steps

1. Open `src/com/hm/constants/DBconstants.java`.
2. Update `DBURL` if you want another DB location.
3. Start Derby in embedded mode by running the app once (or via SQL client).
4. Execute schema from `query.sql` (table creation statements).

> `query.sql` contains extra helper/select statements too; run the `CREATE TABLE ...` blocks first.

### Admin Seed Data

`query.sql` includes a sample admin insert (commented). You can use that hash directly:

```sql
INSERT INTO Admin (AdminID, PasswordHash, AccountStatus)
VALUES (
  'admin123',
  '1000:5b42403230393634343264:590f1ebe12e6244e895afd8176353c0be3aeb96d2f3502ce46879fe9079438515626d4260ed4984b8124169893b5212f7754a909def508e320e5e675400c540a',
  'active'
);
```

---

## 5) Run with Tomcat 10 (Recommended)

### Option A: Eclipse Dynamic Web Project flow

1. Import this folder as an existing project.
2. Configure the project as **Dynamic Web Module**.
3. Attach **Tomcat 10.x** runtime.
4. Add required third-party jars (`Derby`, `PDFBox`) to build path / deployment assembly.
5. Deploy and run on server.
6. Open:
   - Home: `http://localhost:8080/HotelManagement/`
   - User Login: `http://localhost:8080/HotelManagement/user/login.jsp`
   - Admin Login: `http://localhost:8080/HotelManagement/admin/adminLogin.jsp`

### Option B: Build WAR manually

If using another build setup, package sources + `WebContent` into a WAR, include required jars in `WEB-INF/lib`, and deploy to Tomcat 10 `webapps`.

---

## 6) Main Functional Flows

### User

- Register: `UserController?action=insertUser`
- Login: `UserController?action=loginUser`
- Make reservation: `UserController?action=reservationUser`
- Billing -> payment redirect: `UserController?action=paymentUser`
- Invoice generation/fetch: `InvoiceController?action=generateInvoice|fetchInvoice`

### Admin

- Login: `AdminController?action=loginAdmin`
- Add room: `AdminController?action=addRoom`
- Fetch customer bookings: `AdminController?action=fetchBookings`
- Generate/View invoices from admin billing page
