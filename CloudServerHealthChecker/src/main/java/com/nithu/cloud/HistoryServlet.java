package com.nithu.cloud;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/cloud_health_db";

    private static final String DB_USER = "cloud_app";

    private static final String DB_PASSWORD =
            System.getProperty("db.password");

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                    Connection connection =
                            DriverManager.getConnection(
                                    DB_URL,
                                    DB_USER,
                                    DB_PASSWORD
                            );

                    Statement statement =
                            connection.createStatement();

                    ResultSet resultSet =
                            statement.executeQuery(
                                    "SELECT * FROM health_checks ORDER BY id ASC"
                            )
            ) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Server Health History</title>");

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
                out.println("    width: calc(100% - 40px);");
                out.println("    max-width: 1200px;");
                out.println("    margin: 50px auto;");
                out.println("    padding: 30px;");
                out.println("    background-color: white;");
                out.println("    border-radius: 14px;");
                out.println("    box-shadow: 0 6px 20px rgba(0,0,0,0.10);");
                out.println("}");

                out.println("h1 {");
                out.println("    margin-top: 0;");
                out.println("    text-align: center;");
                out.println("    font-size: 28px;");
                out.println("}");

                out.println(".subtitle {");
                out.println("    text-align: center;");
                out.println("    color: #6b7280;");
                out.println("    margin-bottom: 25px;");
                out.println("}");

                out.println(".table-wrapper {");
                out.println("    width: 100%;");
                out.println("    overflow-x: auto;");
                out.println("}");

                out.println("table {");
                out.println("    width: 100%;");
                out.println("    border-collapse: collapse;");
                out.println("    min-width: 850px;");
                out.println("}");

                out.println("th {");
                out.println("    background-color: #f8fafc;");
                out.println("    padding: 14px;");
                out.println("    text-align: left;");
                out.println("    border-bottom: 2px solid #e5e7eb;");
                out.println("    font-size: 14px;");
                out.println("}");

                out.println("td {");
                out.println("    padding: 14px;");
                out.println("    border-bottom: 1px solid #e5e7eb;");
                out.println("    font-size: 14px;");
                out.println("}");

                out.println("tr:hover {");
                out.println("    background-color: #f9fafb;");
                out.println("}");

                out.println(".badge {");
                out.println("    display: inline-block;");
                out.println("    padding: 6px 12px;");
                out.println("    border-radius: 999px;");
                out.println("    font-size: 12px;");
                out.println("    font-weight: bold;");
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

                out.println(".actions {");
                out.println("    margin-top: 25px;");
                out.println("    text-align: center;");
                out.println("}");

                out.println(".btn {");
                out.println("    display: inline-block;");
                out.println("    padding: 12px 20px;");
                out.println("    border-radius: 8px;");
                out.println("    background-color: #2563eb;");
                out.println("    color: white;");
                out.println("    text-decoration: none;");
                out.println("    font-weight: bold;");
                out.println("}");

                out.println(".btn:hover {");
                out.println("    background-color: #1d4ed8;");
                out.println("}");

                out.println("@media (max-width: 700px) {");

                out.println("    .container {");
                out.println("        width: calc(100% - 20px);");
                out.println("        margin: 20px auto;");
                out.println("        padding: 18px;");
                out.println("    }");

                out.println("    h1 {");
                out.println("        font-size: 22px;");
                out.println("    }");

                out.println("}");

                out.println("</style>");
                out.println("</head>");

                out.println("<body>");

                out.println("<div class='container'>");

                out.println("<h1>Server Health History</h1>");

                out.println("<p class='subtitle'>");
                out.println("Latest server health checks stored in MySQL.");
                out.println("</p>");

                out.println("<div class='table-wrapper'>");

                out.println("<table>");

                out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>Server Name</th>");
                out.println("<th>CPU Usage</th>");
                out.println("<th>Status</th>");
                out.println("<th>Health</th>");
                out.println("<th>Reason</th>");
                out.println("<th>Checked At</th>");
                out.println("</tr>");

                while (resultSet.next()) {

                    String health =
                            resultSet.getString("health");

                    String badgeClass;

                    if ("HEALTHY".equalsIgnoreCase(health)) {
                        badgeClass = "healthy";
                    } else if ("WARNING".equalsIgnoreCase(health)) {
                        badgeClass = "warning";
                    } else {
                        badgeClass = "critical";
                    }

                    out.println("<tr>");

                    out.println(
                            "<td>"
                            + resultSet.getInt("id")
                            + "</td>"
                    );

                    out.println(
                            "<td>"
                            + escapeHtml(
                                    resultSet.getString("server_name")
                            )
                            + "</td>"
                    );

                    out.println(
                            "<td>"
                            + resultSet.getInt("cpu_usage")
                            + "%</td>"
                    );

                    out.println(
                            "<td>"
                            + escapeHtml(
                                    resultSet.getString("server_status")
                            )
                            + "</td>"
                    );

                    out.println(
                            "<td>"
                            + "<span class='badge "
                            + badgeClass
                            + "'>"
                            + escapeHtml(health)
                            + "</span>"
                            + "</td>"
                    );

                    out.println(
                            "<td>"
                            + escapeHtml(
                                    resultSet.getString("reason")
                            )
                            + "</td>"
                    );

                    out.println(
                            "<td>"
                            + resultSet.getTimestamp("checked_at")
                            + "</td>"
                    );

                    out.println("</tr>");
                }

                out.println("</table>");

                out.println("</div>");

                out.println("<div class='actions'>");

                out.println(
                        "<a class='btn' href='index.html'>"
                        + "Back to Health Checker"
                        + "</a>"
                );

                out.println("</div>");

                out.println("</div>");

                out.println("</body>");
                out.println("</html>");
            }

        } catch (
                SQLException |
                ClassNotFoundException exception
        ) {

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            out.println("<h1>Database Error</h1>");

            out.println(
                    "<p>"
                    + escapeHtml(exception.getMessage())
                    + "</p>"
            );

            exception.printStackTrace();
        }
    }

    private String escapeHtml(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}