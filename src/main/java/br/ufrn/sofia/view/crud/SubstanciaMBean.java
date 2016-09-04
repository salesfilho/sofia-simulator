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
import br.ufrn.sofia.domain.Substancia;
import br.ufrn.sofia.service.ParametroService;
import br.ufrn.sofia.service.PropriedadeService;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author salesfilho
 */
@Named
@ViewScoped
public class SubstanciaMBean extends CrudMBean<Substancia, Long> {

    @Inject
    private PropriedadeService propriedadeService;
    @Inject
    private ParametroService parametroService;

    @Getter
    @Setter
    private Propriedade propriedade;

    @Getter
    @Setter
    private Parametro parametro;

    @Override
    public void init() {
        super.init();
        propriedade = new Propriedade();
        parametro = new Parametro();
    }

    @Override
    public void changeToInsertState() {
        this.setBean(null);
        super.changeToInsertState();
    }

    public void addPropriedade() {
        if (propriedade != null && !getBean().getPropriedades().contains(propriedade)) {
            getBean().addPropriedade(propriedade);
            propriedade = new Propriedade();
        }
    }

    public void removePropriedade(Propriedade outraPropriedade) {
        if (outraPropriedade != null) {
            getBean().removePropriedade(outraPropriedade);
        }
    }

    public void addParametroToPropriedade(Propriedade prop) {
        if (prop != null && parametro != null) {
            getBean().removePropriedade(prop);
            prop.addParametro(parametro);
            getBean().addPropriedade(prop);
        }
        parametro = new Parametro();
    }

    public void removeParametroFromPropriedade(Parametro outroParametro, Propriedade outraPropriedade) {
        if (outroParametro != null && outraPropriedade != null) {
            getBean().removePropriedade(outraPropriedade);
            outraPropriedade.removeParametro(outroParametro);
            getBean().addPropriedade(outraPropriedade);
        }
    }

    public String onFlowInsertProcess(FlowEvent event) {
        System.out.println("Insert: " + event);
        //Insere logo a substância
        if (event.getOldStep().equalsIgnoreCase("tab_substancia_insert")) {
            //this.save();
        }
        return event.getNewStep();
    }

    public String onFlowUpdateProcess(FlowEvent event) {
        System.out.println("Update: " + event);
        return event.getNewStep();
    }
}
