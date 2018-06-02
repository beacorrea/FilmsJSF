/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.ejb;

import films.entity.Language;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author beaco
 */
@Stateless
public class LanguageFacade extends AbstractFacade<Language> {

    @PersistenceContext(unitName = "ParcialAnteriorJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LanguageFacade() {
        super(Language.class);
    }
    
}
