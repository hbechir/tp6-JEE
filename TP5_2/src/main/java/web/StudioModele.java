package web;
import java.util.ArrayList;
import java.util.List;
import metier.entities.Studio;


public class StudioModele {
List<Studio> studios = new ArrayList<>();
public List<Studio> getStudios() {
return studios;
}
public void setStudios(List<Studio> studios) {
this.studios = studios;
}
}