package web;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;

import dao.StudioDaoImpl;
import dao.IStudioDao;
import dao.IGameDao;
import dao.GameDaoImpl;
import metier.entities.Studio;
import metier.entities.Game;
@WebServlet (name="cs",urlPatterns= {"/controleur","*.do"})
public class ControleurServlet extends HttpServlet {
IGameDao metier;
IStudioDao metierCat;
@Override
public void init() throws ServletException {
metier = new GameDaoImpl();
metierCat = new StudioDaoImpl();
}

@Override
protected void doGet(HttpServletRequest request,
 HttpServletResponse response)
 throws ServletException, IOException {
String path=request.getServletPath();
if (path.equals("/index.do"))
{
request.getRequestDispatcher("games.jsp").forward(request,response);
}
else if (path.equals("/chercher.do"))
{
String motCle=request.getParameter("motCle");
GameModele model= new GameModele();
model.setMotCle(motCle);
List<Game> prods = metier.gamesParMC(motCle);
model.setGames(prods);
request.setAttribute("model", model);
request.getRequestDispatcher("games.jsp").forward(request,response);
}
else if (path.equals("/saisie.do") )

{ 
List<Studio> cats = metierCat.getAllStudios();
StudioModele model= new StudioModele();
model.setStudios(cats);
request.setAttribute("catModel", model);

request.getRequestDispatcher("saisieGame.jsp").forward(request,response);
}
else if (path.equals("/save.do") && request.getMethod().equals("POST"))

{
String nom=request.getParameter("nom");
Long studioId=Long.parseLong(request.getParameter("studio"));
double prix = Double.parseDouble(request.getParameter("prix"));
Studio cat = metierCat.getStudio(studioId);
Game p = metier.save(new Game(nom,prix,cat));
request.setAttribute("game", p);
response.sendRedirect("chercher.do?motCle="); }
else if (path.equals("/supprimer.do"))
{
 Long id= Long.parseLong(request.getParameter("id"));
 metier.deleteGame(id);
 response.sendRedirect("chercher.do?motCle=");
}
else if (path.equals("/editer.do") )

{
Long id= Long.parseLong(request.getParameter("id"));
Game p = metier.getGame(id);
request.setAttribute("game", p);
List<Studio> cats = metierCat.getAllStudios();
StudioModele model= new StudioModele();
model.setStudios(cats);
request.setAttribute("catModel", model);
request.getRequestDispatcher("editerGame.jsp").forward(request,response);
}

else if (path.equals("/update.do") )

{ Long id = Long.parseLong(request.getParameter("id"));
String nom=request.getParameter("nom");
double prix = Double.parseDouble(request.getParameter("prix"));
Long studioId=Long.parseLong(request.getParameter("studio"));
Game p = new Game();
p.setIdGame(id);
p.setNomGame(nom);
p.setPrix(prix);
Studio cat = metierCat.getStudio(studioId);
p.setStudio(cat);
metier.updateGame(p);
response.sendRedirect("chercher.do?motCle=");
}


else
{
response.sendError(Response.SC_NOT_FOUND);
}
}


@Override
protected void doPost(HttpServletRequest request,
 HttpServletResponse response) throws
ServletException, IOException {
doGet(request,response);
}
}