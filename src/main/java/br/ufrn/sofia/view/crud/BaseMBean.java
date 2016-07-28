/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.sofia.view.crud;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class BaseMBean implements Serializable {

    public static final String SEARCH_STATE = "pesquisar";
    public static final String LIST_STATE = "listar";
    public static final String INSERT_STATE = "adicionar";
    public static final String UPDATE_STATE = "editar";
    public static final String DATAIL_STATE = "detalhe";

    private String currentState = LIST_STATE;

    /* 
     * Métodos que expõem o estado à página 
     */
    public boolean isListState() {
        String state = this.getCurrentState();
        return (state == null || LIST_STATE.equals(state));
    }

    public boolean isSearchState() {
        String state = this.getCurrentState();
        return (state == null || SEARCH_STATE.equals(state));
    }

    public boolean isInsertState() {
        return INSERT_STATE.equals(this.getCurrentState());
    }

    public boolean isUpdateState() {
        return UPDATE_STATE.equals(this.getCurrentState());
    }
    public boolean isDatailState() {
        return DATAIL_STATE.equals(this.getCurrentState());
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public void changeToListState() {
        setCurrentState(LIST_STATE);
    }

    public void changeToSearchState() {
        setCurrentState(SEARCH_STATE);
    }

    public void changeToInsertState() {
        setCurrentState(INSERT_STATE);
    }

    public void changeToUpdateState() {
        setCurrentState(UPDATE_STATE);
    }

    public void changeToDatailState() {
        setCurrentState(DATAIL_STATE);
    }

    /**
     *
     * @param summary
     * @param severity
     * @param datail
     */
    public void addMessage(FacesMessage.Severity severity, String summary, String datail) {
        FacesMessage message = new FacesMessage(severity, summary, datail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
