package dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import metier.entities.Studio;
import util.JPAutil;
public class StudioDaoImpl implements IStudioDao {
// TP6_JEE à replacer par votre persistence unit, consultez votre
//fichier persistence.xml <persistence-unit name="TP6_JEE">
private EntityManager entityManager=JPAutil.getEntityManager("TP6_JEE");
@Override
public Studio save(Studio cat ) {
EntityTransaction tx = entityManager.getTransaction();
tx.begin();
entityManager.persist(cat);
tx.commit();
return cat;
}
@Override
public Studio getStudio(Long id) {
return entityManager.find(Studio.class, id);
}
@Override
public Studio updateStudio(Studio cat) {
EntityTransaction tx = entityManager.getTransaction();
tx.begin();
entityManager.merge(cat);
tx.commit();
return cat;
}
@Override
public void deleteStudio(Long id) {
Studio studio = entityManager.find(Studio.class, id);
entityManager.getTransaction().begin();
entityManager.remove(studio);
entityManager.getTransaction().commit();
}
@Override
public List<Studio> getAllStudios() {
List<Studio> cats = entityManager.createQuery("select c from Studio c").getResultList();
return cats;
}
}