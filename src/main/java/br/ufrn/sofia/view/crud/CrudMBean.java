/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.sofia.view.crud;

import br.ufrn.sofia.service.CrudService;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author salesfilho
 * @param <T>
 * @param <ID>
 */
public class CrudMBean<T extends Object, ID extends Serializable> extends BaseMBean {

    @Inject
    private CrudService<T, ID> crudService;

    @Getter
    @Setter
    private List<T> beans;

    private Class<T> entityBeanClass;
    private T bean;

    public CrudMBean() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityBeanClass = (Class) pt.getActualTypeArguments()[0];
    }

    @PostConstruct
    public void init() {
        findAll();
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public void findAll() {
        beans = crudService.findAll();
    }

    /**
     * Prepara view adicionar
     */
    @Override
    public void changeToInsertState() {
        setCurrentState(INSERT_STATE);
        if (this.bean == null) {
            createNewEntityBean();
        }
    }

    /**
     * Adiciona
     */
    public void processInsert() {
        try {
            this.bean = crudService.save(this.bean);
            findAll();
            addMessage(FacesMessage.SEVERITY_INFO, "Incusão: ", "Operação realizada com sucesso.");
            createNewEntityBean();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Falha ao inserir registro.", e.getMessage());
            Logger.getLogger(CrudMBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Prepara view atualizar
     *
     * @param id
     */
    public void startUpdate(ID id) {
        this.setBean(crudService.findOne(id));
        this.setCurrentState(UPDATE_STATE);
    }
    /**
     * Prepara view detalhe
     *
     * @param id
     */
    public void startDetail(ID id) {
        this.setBean(crudService.findOne(id));
        this.setCurrentState(DATAIL_STATE);
    }

    /**
     * Atualiza registro
     */
    public void processUpdate() {
        try {
            this.bean = crudService.save(this.bean);
            findAll();
            addMessage(FacesMessage.SEVERITY_INFO, "Alteração: ", "Operação realizada com sucesso.");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Falha ao atualizar registro.", e.getMessage());
            Logger.getLogger(CrudMBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Deleta o bean com id passado como parametro
     *
     * @param id
     */
    public void processDelete(ID id) {
        try {
            T auxBean = crudService.findOne(id);
            crudService.delete(auxBean);
            findAll();
            addMessage(FacesMessage.SEVERITY_INFO, "Exclusão: ", "Operação realizada com sucesso.");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Falha ao excluir registro.", e.getMessage());
            Logger.getLogger(CrudMBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void createNewEntityBean() {
        try {
            this.bean = (T) Class.forName(this.entityBeanClass.getName()).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException ex) {
            Logger.getLogger(CrudMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
