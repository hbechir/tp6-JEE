package web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import dao.StudioDaoImpl;
import dao.IStudioDao;
import metier.entities.Studio;

@WebServlet(name = "catServ", urlPatterns = { "/studios", "/saisieStudio",
        "/saveStudio", "/supprimerCat", "/editerCat", "/updateCat" })
public class StudioServlet extends HttpServlet {

    private IStudioDao metier;

    @Override
    public void init() throws ServletException {
        metier = new StudioDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        System.out.println("PATH " + path);

        if (path.equals("/studios")) {
            StudioModele model = new StudioModele();
            List<Studio> cats = metier.getAllStudios();
            model.setStudios(cats);
            request.setAttribute("model", model);
            request.getRequestDispatcher("studios.jsp").forward(request, response);
        } else if (path.equals("/saisieStudio")) {
            request.getRequestDispatcher("saisieStudio.jsp").forward(request, response);
        } else if (path.equals("/saveStudio") && request.getMethod().equals("POST")) {
            Date dateCat = new Date();
            String nom = request.getParameter("nom");
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                dateCat = simpleDateFormat.parse(request.getParameter("dateCat"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Studio cat = metier.save(new Studio(nom, dateCat));
            request.setAttribute("studio", cat);
            response.sendRedirect("studios");
        } else if (path.equals("/supprimerCat")) {
            Long id = Long.parseLong(request.getParameter("id"));
            metier.deleteStudio(id);
            response.sendRedirect("studios");
        } else if (path.equals("/editerCat")) {
            Long id = Long.parseLong(request.getParameter("id"));
            Studio cat = metier.getStudio(id);
            request.setAttribute("studio", cat);
            request.getRequestDispatcher("editerStudio.jsp").forward(request, response);
        } else if (path.equals("/updateCat")) {
            Date dateCat = new Date();
            Long id = Long.parseLong(request.getParameter("id"));
            String nom = request.getParameter("nom");
            Studio cat = new Studio();
            cat.setIdCat(id);
            cat.setNomCat(nom);
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                dateCat = simpleDateFormat.parse(request.getParameter("dateCreation"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cat.setDateCreation(dateCat);
            metier.updateStudio(cat);
            response.sendRedirect("studios");
        } else {
            response.sendError(Response.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
