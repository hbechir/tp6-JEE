package dao;
import java.util.List;
import metier.entities.Studio;
public interface IStudioDao {
	public Studio save(Studio cat);
	public Studio getStudio(Long id);
	public Studio updateStudio(Studio cat);
	public void deleteStudio(Long id);
	public List<Studio> getAllStudios();
}