/*
 * Copyright 2016 Persapiens.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.ufrn.sofia.view.crud;

import br.ufrn.sofia.domain.Parametro;
import br.ufrn.sofia.domain.Propriedade;
import br.ufrn.sofia.service.ParametroService;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DualListModel;

/**
 *
 * @author salesfilho
 */
@Named
@ViewScoped
public class PropriedadeMBean extends CrudMBean<Propriedade, Long> {

    @Getter
    @Setter
    private DualListModel<Parametro> insertParametroList;

    @Getter
    @Setter
    private DualListModel<Parametro> updateParametroList;

    @Getter
    @Setter
    private List<Parametro> fullList;

    @Getter
    @Setter
    private Parametro srcParametro;

    @Getter
    @Setter
    private Parametro dstParametro;

    @Getter
    @Setter
    private List<Parametro> soucreList;

    @Getter
    @Setter
    private List<Parametro> destinationList;

    @Inject
    private ParametroService parametroService;

    @PostConstruct
    private void initPickLists() {
        insertParametroList = new DualListModel();
        updateParametroList = new DualListModel();
        fullList = parametroService.findAll();
    }

    private void configureInsertPickList() {
        fullList = parametroService.findAll();
        insertParametroList.setSource(fullList);
        insertParametroList.setTarget(new ArrayList<>());
    }

    private void configureUpdatePickList() {
        fullList = parametroService.findAll();
        List<Parametro> tmpSourceList = fullList;
        List<Parametro> tmpDestinationList = new ArrayList();
        if (getBean() != null && getBean().getParametros() != null) {
            tmpDestinationList = new ArrayList(getBean().getParametros());
            for (Parametro param : tmpDestinationList) {
                if (tmpSourceList.contains(param)) {
                    tmpSourceList.remove(param);
                }
            }
        }
        soucreList = tmpSourceList;
        destinationList = tmpDestinationList;
    }

    public List<Parametro> getParametros() {
        if (getBean() != null) {
            return new ArrayList(getBean().getParametros());
        }
        return new ArrayList();
    }

    @Override
    public void changeToInsertState() {
        this.setBean(null);
        super.changeToInsertState();
        configureInsertPickList();
    }

    @Override
    public void processInsert() {
        getBean().setParametros(new TreeSet(insertParametroList.getTarget()));
        super.processInsert();
    }

    @Override
    public void startUpdate(Long id) {
        super.startUpdate(id);
        configureUpdatePickList();
    }

    @Override
    public void processUpdate() {
        getBean().setParametros(new TreeSet(getDestinationList()));
        super.processUpdate();
    }

    public void addPropriedade() {

        if (getSrcParametro() != null && !destinationList.contains(getSrcParametro())) {
            destinationList.add(getSrcParametro());
            soucreList.remove(getSrcParametro());
        }
    }

    public void removePropriedade() {
        if (getDstParametro() != null && !soucreList.contains(getDstParametro())) {
            soucreList.add(getDstParametro());
            destinationList.remove(getDstParametro());
        }
    }

}
