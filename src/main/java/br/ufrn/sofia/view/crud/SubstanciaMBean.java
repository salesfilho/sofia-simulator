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

import br.ufrn.sofia.domain.Propriedade;
import br.ufrn.sofia.domain.Substancia;
import br.ufrn.sofia.service.PropriedadeService;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author salesfilho
 */
@Named
@ViewScoped
public class SubstanciaMBean extends CrudMBean<Substancia, Long> {

    @Getter
    @Setter
    private DualListModel<Propriedade> insertPropriedadeList;

    @Getter
    @Setter
    private List<Propriedade> fullList;

    @Getter
    @Setter
    private Propriedade srcPropriedade;

    @Getter
    @Setter
    private Propriedade dstPropriedade;

    @Getter
    @Setter
    private List<Propriedade> soucreList;

    @Getter
    @Setter
    private List<Propriedade> destinationList;

    private List<Propriedade> initialDestinationList;

    @Inject
    private PropriedadeService propriedadeService;

    @PostConstruct
    private void initPickLists() {
        insertPropriedadeList = new DualListModel();
        fullList = propriedadeService.findAll();
    }

    private void configureInsertPickList() {
        fullList = propriedadeService.findAll();
        insertPropriedadeList.setSource(fullList);
        insertPropriedadeList.setTarget(new ArrayList<>());
    }

    private void configureUpdatePickList() {
        fullList = propriedadeService.findAll();
        List<Propriedade> tmpSourceList = fullList;
        initialDestinationList = new ArrayList();
        if (getBean() != null && getBean().getPropriedades() != null) {
            initialDestinationList = new ArrayList(getBean().getPropriedades());
            for (Propriedade prop : initialDestinationList) {
                if (tmpSourceList.contains(prop)) {
                    tmpSourceList.remove(prop);
                }
            }
        }
        soucreList = tmpSourceList;
        destinationList = initialDestinationList;
    }

    public List<Propriedade> getPropriedades() {
        if (getBean() != null) {
            return new ArrayList(getBean().getPropriedades());
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
        getBean().setPropriedades(new TreeSet(insertPropriedadeList.getTarget()));
        super.processInsert();
    }

    @Override
    public void startUpdate(Long id) {
        super.startUpdate(id);
        configureUpdatePickList();
    }

    @Override
    public void processUpdate() {
        getBean().setPropriedades(new TreeSet(getDestinationList()));
        super.processUpdate();
    }

    public void addPropriedade() {

        if (getSrcPropriedade() != null && !destinationList.contains(getSrcPropriedade())) {
            destinationList.add(getSrcPropriedade());
            soucreList.remove(getSrcPropriedade());
        }
    }

    public void removePropriedade() {
        if (getDstPropriedade() != null && !soucreList.contains(getDstPropriedade())) {
            soucreList.add(getDstPropriedade());
            destinationList.remove(getDstPropriedade());
        }
    }

}
