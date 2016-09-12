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
package br.ufrn.sofia.domain;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author salesfilho
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"parametros", "substancia"}, callSuper = true)
@Builder
@Entity
@SequenceGenerator(sequenceName = "seq_propriedade", name = "ID_SEQUENCE", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class Propriedade extends AbstractBean<Propriedade, Long> {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "propriedade", orphanRemoval = true,
            fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Parametro> parametros = new TreeSet<>();

    @ManyToOne(optional = false)
    private Substancia substancia;

    public void addParametro(Parametro param) {
        if (param != null && !parametros.contains(param)) {
            param.setPropriedade(this);
            parametros.add(param);
        }
    }

    public void removeParametro(Parametro param) {
        if (param != null && parametros.contains(param)) {
            parametros.remove(param);
        }
    }

    @Override
    public int compareTo(Propriedade obj) {
        return getNome().compareTo(obj.getNome());
    }

    @Override
    public String toString() {
        return String.format("Propriedade[%d, %s, %s]", getId(), getNome(), getDescricao());
    }

}
