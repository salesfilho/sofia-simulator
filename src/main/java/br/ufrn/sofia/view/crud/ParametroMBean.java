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
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author salesfilho
 */
@Named
@ViewScoped
public class ParametroMBean extends CrudMBean<Parametro, Long> {

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
    }

}
