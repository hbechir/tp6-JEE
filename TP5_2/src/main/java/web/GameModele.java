package web;
import java.util.ArrayList;
import java.util.List;
import metier.entities.Game;
public class GameModele {
private String motCle;
List<Game> produits = new ArrayList<>();
public String getMotCle() {
return motCle;
}
public void setMotCle(String motCle) {
this.motCle = motCle;
}
public List<Game> getGames() {
return produits;
}
public void setGames(List<Game> produits) {
this.produits = produits;
}
}