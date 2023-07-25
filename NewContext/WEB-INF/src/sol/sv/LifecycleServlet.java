package sol.sv;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LifecycleServlet extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init()");
    }
    
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        System.out.println("service()");
        if (req.getMethod().equals("GET")) {
            System.out.println("GET");
        } else if (req.getMethod().equals("POST")) {
            System.out.println("POST");
        } else {
            System.out.println("OtherMethod");
        }
    }

    public void destroy() { // from GenericServlet
        System.out.println("destroy()");
    }

}
