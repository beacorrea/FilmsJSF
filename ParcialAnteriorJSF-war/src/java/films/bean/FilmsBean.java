/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.bean;

import films.ejb.CategoryFacade;
import films.ejb.FilmFacade;
import films.entity.Category;
import films.entity.Film;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author beaco
 */
@Named(value = "filmsBean")
@SessionScoped
public class FilmsBean implements Serializable {

    @EJB
    private CategoryFacade categoryFacade;

    @EJB
    private FilmFacade filmFacade;
    

    protected List<Film> listaFilms;
    protected List<String> listaCategorias;
    protected String categoriaSeleccionada;
    protected Film peliculaSeleccionada;

    public List<Film> getListaFilms() {
        return listaFilms;
    }

    public void setListaFilms(List<Film> listaFilms) {
        this.listaFilms = listaFilms;
    }

    public List<String> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<String> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public String getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(String categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public Film getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }

    public void setPeliculaSeleccionada(Film peliculaSeleccionada) {
        this.peliculaSeleccionada = peliculaSeleccionada;
    }
    
    /**
     * Creates a new instance of FilmsBean
     */
    public FilmsBean() {
     
    }
    
    @PostConstruct
    public void init(){
        listaCategorias = this.categoryFacade.buscarNombresCategorias();
        categoriaSeleccionada = "Categorias";
        if(categoriaSeleccionada.equals("Categorias")){
            listaFilms = this.filmFacade.findAll();
        }else{
            listaFilms = this.filmFacade.buscarPeliculasPorCategoria(categoriaSeleccionada);
        }
        
    }
    
    public String mostrarPorCategoria(){
        if(categoriaSeleccionada == null){
            listaFilms = this.filmFacade.findAll();
        }else{
            listaFilms = this.filmFacade.buscarPeliculasPorCategoria(categoriaSeleccionada);
        }
        return "index";
    }
    
    public String doBorrar(Short id){
        peliculaSeleccionada = this.filmFacade.findByFilmId(id);
        this.filmFacade.remove(peliculaSeleccionada);
        listaFilms = this.filmFacade.buscarPeliculasPorCategoria(categoriaSeleccionada);
        return "index";
    }
    
    public String doEditar(Short id){
        peliculaSeleccionada = this.filmFacade.findByFilmId(id);

        return "editarFilm";
    }
}
