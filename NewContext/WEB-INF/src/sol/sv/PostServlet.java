package sol.sv;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("name");
        String pwd = req.getParameter("pwd");

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                </head>
                <body style="text-align:center;">
                    <h1>Hello %s</h1>
                    <a href="index.html">Home</a>
                </body>
                </html>""".formatted(id);
        pw.println(html);
    }
}
