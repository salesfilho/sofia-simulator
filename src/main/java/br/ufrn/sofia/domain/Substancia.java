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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
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
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@SequenceGenerator(sequenceName = "seq_substancia", name = "ID_SEQUENCE", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class Substancia extends AbstractBean<Substancia, Long> {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = false)
    private String formula;

    @Column(nullable = false, unique = false)
    private double massaMolar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "propriedade_substancia",
            joinColumns = @JoinColumn(table = "substancia", name = "substancias_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(table = "propriedade", name = "propriedades_id", referencedColumnName = "id"))

    private Set<Propriedade> propriedades;

    @Override
    public int compareTo(Substancia obj) {
        return getNome().compareTo(obj.getNome());
    }

    @Override
    public String toString() {
        return String.format("Parametro[%d, %s]", getId(), getDescricao());
    }

}
