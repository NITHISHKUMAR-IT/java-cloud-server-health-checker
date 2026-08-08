package com.nithu.cloud;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/health-check")
public class HealthCheckServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/cloud_health_db";

    private static final String DB_USER = "cloud_app";

    private static final String DB_PASSWORD =
            System.getProperty("db.password");

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String serverName = request.getParameter("serverName");
        String cpuUsageText = request.getParameter("cpuUsage");
        String serverStatus = request.getParameter("serverStatus");

        PrintWriter out = response.getWriter();

        try {
            int cpuUsage = Integer.parseInt(cpuUsageText);

            String health;
            String reason;

            if ("Stopped".equalsIgnoreCase(serverStatus)) {
                health = "CRITICAL";
                reason = "The server is currently stopped.";

            } else if (cpuUsage > 80) {
                health = "CRITICAL";
                reason = "CPU usage is above 80%.";

            } else if (cpuUsage >= 60) {
                health = "WARNING";
                reason = "CPU usage is between 60% and 80%.";

            } else {
                health = "HEALTHY";
                reason = "The server is running with normal CPU usage.";
            }

            saveHealthCheck(
                    serverName,
                    cpuUsage,
                    serverStatus,
                    health,
                    reason
            );

            String badgeClass;

            if ("HEALTHY".equalsIgnoreCase(health)) {
                badgeClass = "healthy";
            } else if ("WARNING".equalsIgnoreCase(health)) {
                badgeClass = "warning";
            } else {
                badgeClass = "critical";
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Server Health Result</title>");

            out.println("<style>");

            out.println("* {");
            out.println("    box-sizing: border-box;");
            out.println("}");

            out.println("body {");
            out.println("    margin: 0;");
            out.println("    font-family: Arial, sans-serif;");
            out.println("    background-color: #f4f6f8;");
            out.println("    color: #1f2937;");
            out.println("}");

            out.println(".container {");
            out.println("    width: 100%;");
            out.println("    max-width: 520px;");
            out.println("    margin: 60px auto;");
            out.println("    padding: 30px;");
            out.println("    background-color: white;");
            out.println("    border-radius: 14px;");
            out.println("    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.10);");
            out.println("}");

            out.println("h1 {");
            out.println("    margin-top: 0;");
            out.println("    text-align: center;");
            out.println("    font-size: 26px;");
            out.println("}");

            out.println(".status {");
            out.println("    text-align: center;");
            out.println("    margin: 22px 0;");
            out.println("}");

            out.println(".badge {");
            out.println("    display: inline-block;");
            out.println("    padding: 8px 18px;");
            out.println("    border-radius: 20px;");
            out.println("    font-weight: bold;");
            out.println("    font-size: 14px;");
            out.println("}");

            out.println(".healthy {");
            out.println("    background-color: #dcfce7;");
            out.println("    color: #166534;");
            out.println("}");

            out.println(".warning {");
            out.println("    background-color: #fef3c7;");
            out.println("    color: #92400e;");
            out.println("}");

            out.println(".critical {");
            out.println("    background-color: #fee2e2;");
            out.println("    color: #991b1b;");
            out.println("}");

            out.println(".details {");
            out.println("    margin-top: 15px;");
            out.println("}");

            out.println(".row {");
            out.println("    display: flex;");
            out.println("    justify-content: space-between;");
            out.println("    gap: 20px;");
            out.println("    padding: 13px 0;");
            out.println("    border-bottom: 1px solid #e5e7eb;");
            out.println("}");

            out.println(".label {");
            out.println("    font-weight: bold;");
            out.println("}");

            out.println(".value {");
            out.println("    text-align: right;");
            out.println("}");

            out.println(".database-success {");
            out.println("    margin-top: 22px;");
            out.println("    padding: 13px;");
            out.println("    background-color: #ecfdf5;");
            out.println("    border-radius: 8px;");
            out.println("    color: #065f46;");
            out.println("    text-align: center;");
            out.println("    font-weight: bold;");
            out.println("}");

            out.println(".actions {");
            out.println("    display: flex;");
            out.println("    gap: 10px;");
            out.println("    margin-top: 25px;");
            out.println("}");

            out.println(".btn {");
            out.println("    flex: 1;");
            out.println("    padding: 12px;");
            out.println("    border-radius: 8px;");
            out.println("    text-decoration: none;");
            out.println("    text-align: center;");
            out.println("    font-weight: bold;");
            out.println("}");

            out.println(".primary {");
            out.println("    background-color: #2563eb;");
            out.println("    color: white;");
            out.println("}");

            out.println(".secondary {");
            out.println("    background-color: #e5e7eb;");
            out.println("    color: #1f2937;");
            out.println("}");

            out.println(".btn:hover {");
            out.println("    opacity: 0.9;");
            out.println("}");

            out.println("@media (max-width: 550px) {");

            out.println("    .container {");
            out.println("        width: calc(100% - 30px);");
            out.println("        margin: 30px auto;");
            out.println("        padding: 22px;");
            out.println("    }");

            out.println("    .row {");
            out.println("        flex-direction: column;");
            out.println("        gap: 5px;");
            out.println("    }");

            out.println("    .value {");
            out.println("        text-align: left;");
            out.println("    }");

            out.println("    .actions {");
            out.println("        flex-direction: column;");
            out.println("    }");

            out.println("}");

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<h1>Server Health Result</h1>");

            out.println("<div class='status'>");

            out.println(
                    "<span class='badge "
                    + badgeClass
                    + "'>"
                    + escapeHtml(health)
                    + "</span>"
            );

            out.println("</div>");

            out.println("<div class='details'>");

            out.println("<div class='row'>");
            out.println("<span class='label'>Server Name</span>");
            out.println("<span class='value'>"
                    + escapeHtml(serverName)
                    + "</span>");
            out.println("</div>");

            out.println("<div class='row'>");
            out.println("<span class='label'>CPU Usage</span>");
            out.println("<span class='value'>"
                    + cpuUsage
                    + "%</span>");
            out.println("</div>");

            out.println("<div class='row'>");
            out.println("<span class='label'>Server Status</span>");
            out.println("<span class='value'>"
                    + escapeHtml(serverStatus)
                    + "</span>");
            out.println("</div>");

            out.println("<div class='row'>");
            out.println("<span class='label'>Reason</span>");
            out.println("<span class='value'>"
                    + escapeHtml(reason)
                    + "</span>");
            out.println("</div>");

            out.println("</div>");

            out.println("<div class='database-success'>");
            out.println("Result saved successfully to database.");
            out.println("</div>");

            out.println("<div class='actions'>");

            out.println(
                    "<a class='btn secondary' href='index.html'>"
                    + "Check Another Server"
                    + "</a>"
            );

            out.println(
                    "<a class='btn primary' href='history'>"
                    + "View Health History"
                    + "</a>"
            );

            out.println("</div>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        } catch (NumberFormatException exception) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            out.println("<h1>Invalid Input</h1>");
            out.println(
                    "<p>CPU usage must be a valid whole number.</p>"
            );

            out.println(
                    "<a href='index.html'>Go Back</a>"
            );

        } catch (SQLException exception) {

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            out.println("<h1>Database Error</h1>");

            out.println(
                    "<p>The health result could not be saved.</p>"
            );

            out.println(
                    "<p>Error: "
                    + escapeHtml(exception.getMessage())
                    + "</p>"
            );

            out.println(
                    "<a href='index.html'>Go Back</a>"
            );

            exception.printStackTrace();
        }
    }

    private void saveHealthCheck(
            String serverName,
            int cpuUsage,
            String serverStatus,
            String health,
            String reason
    ) throws SQLException {

        try {
            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );

        } catch (ClassNotFoundException exception) {

            throw new SQLException(
                    "MySQL JDBC Driver not found",
                    exception
            );
        }

        String sql =
                "INSERT INTO health_checks "
                + "(server_name, cpu_usage, "
                + "server_status, health, reason) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DriverManager.getConnection(
                                DB_URL,
                                DB_USER,
                                DB_PASSWORD
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    serverName
            );

            statement.setInt(
                    2,
                    cpuUsage
            );

            statement.setString(
                    3,
                    serverStatus
            );

            statement.setString(
                    4,
                    health
            );

            statement.setString(
                    5,
                    reason
            );

            statement.executeUpdate();
        }
    }

    private String escapeHtml(
            String value
    ) {

        if (value == null) {
            return "";
        }

        return value
                .replace(
                        "&",
                        "&amp;"
                )
                .replace(
                        "<",
                        "&lt;"
                )
                .replace(
                        ">",
                        "&gt;"
                )
                .replace(
                        "\"",
                        "&quot;"
                )
                .replace(
                        "'",
                        "&#39;"
                );
    }
}