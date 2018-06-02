/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.bean;

import films.ejb.FilmFacade;
import films.ejb.LanguageFacade;
import films.ejb.RatingFacade;
import films.entity.Film;
import films.entity.Language;
import films.entity.Rating;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author beaco
 */
@Named(value = "filmsEditarBean")
@RequestScoped
public class FilmsEditarBean {

    
    @Inject
    private FilmsBean filmsBean;
    
    @EJB
    private LanguageFacade languageFacade;

    @EJB
    private RatingFacade ratingFacade;

    @EJB
    private FilmFacade filmFacade;

    protected Film pelicula;
    protected List<Language> listaLanguage;
    protected List<Rating> listaRating;
    protected Short languageSeleccionado;
    protected Short ratingSeleccionado;

    public Film getPelicula() {
        return pelicula;
    }

    public void setPelicula(Film pelicula) {
        this.pelicula = pelicula;
    }

    public List<Language> getListaLanguage() {
        return listaLanguage;
    }

    public void setListaLanguage(List<Language> listaLanguage) {
        this.listaLanguage = listaLanguage;
    }

    public List<Rating> getListaRating() {
        return listaRating;
    }

    public void setListaRating(List<Rating> listaRating) {
        this.listaRating = listaRating;
    }

    public Short getLanguageSeleccionado() {
        return languageSeleccionado;
    }

    public void setLanguageSeleccionado(Short languageSeleccionado) {
        this.languageSeleccionado = languageSeleccionado;
    }

    public Short getRatingSeleccionado() {
        return ratingSeleccionado;
    }

    public void setRatingSeleccionado(Short ratingSeleccionado) {
        this.ratingSeleccionado = ratingSeleccionado;
    }

   
    /**
     * Creates a new instance of FilmsEditarBean
     */
    public FilmsEditarBean() {
    }
    
    @PostConstruct
    public void init(){
        this.pelicula = this.filmsBean.getPeliculaSeleccionada();
        this.listaLanguage = this.languageFacade.findAll();
        this.listaRating = this.ratingFacade.findAll();
        this.languageSeleccionado = this.pelicula.getLanguageId().getLanguageId();
        this.ratingSeleccionado = this.pelicula.getRatingId().getRatingId();
    }
    
    public String doGuardar(){
        this.pelicula.setLanguageId(this.languageFacade.find(this.languageSeleccionado));
        this.pelicula.setRatingId(this.ratingFacade.find(this.ratingSeleccionado));
        this.pelicula.setLastUpdate(new Date());
        this.filmFacade.edit(pelicula);
        
        //GUARDO VALORES
        String categoriaSeleccionada = this.filmsBean.getCategoriaSeleccionada();
        List<Film> listaFilms = this.filmFacade.buscarPeliculasPorCategoria(categoriaSeleccionada);
        
        //INICIALIZO
        this.filmsBean.init();
        
        //OBLIGO A QUE TENGAN ESOS VALORES
        this.filmsBean.setCategoriaSeleccionada(categoriaSeleccionada);
        this.filmsBean.setListaFilms(listaFilms);
        
        //MENSAJE MOSTRADO EN PANTALLA
        FacesMessage msg;
        msg = new FacesMessage("Película editada con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        return "index";
    }
    
    public String doCancelar(){
        /*SI NO FUNCIONA HACER:
        this.filmsBean.setPeliculaSeleccionada(null);
        String categoriaSeleccionada = this.filmsBean.getCategoriaSeleccionada();
        List<Film> listaFilms = this.filmFacade.buscarPeliculasPorCategoria(categoriaSeleccionada);

        this.filmsBean.init();
        
        this.filmsBean.setCategoriaSeleccionada(categoriaSeleccionada);
        this.filmsBean.setListaFilms(listaFilms);*/
        return "index";
    }
}
