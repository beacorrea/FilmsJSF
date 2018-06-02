/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.ejb;

import films.entity.Category;
import films.entity.Film;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author beaco
 */
@Stateless
public class FilmFacade extends AbstractFacade<Film> {

    @PersistenceContext(unitName = "ParcialAnteriorJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FilmFacade() {
        super(Film.class);
    }
    public List<Film> buscarPeliculasPorCategoria(String cat){
       Query q = this.em.createQuery("select f from Film f JOIN f.filmCategoryList c where c.category.name = :cat");
       q.setParameter("cat", cat);
       return q.getResultList();
    }
     
    public Film findByFilmId(Short filmId){
        Query q = this.em.createNamedQuery("Film.findByFilmId");
        q.setParameter("filmId", filmId);
        
        List<Film> lista = q.getResultList();
        if (lista == null || lista.size() == 0) {
            return null;
        } else {
            return lista.get(0);
        }
    }
}
