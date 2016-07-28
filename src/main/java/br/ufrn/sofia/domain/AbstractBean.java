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

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author salesfilho
 * @param <ID>
 * @param <T>
 *
 */

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, exclude = "id")
@ToString(callSuper = false)
public abstract class AbstractBean<T, ID> implements Serializable, Comparable<T> {

    @Id
    @GeneratedValue(generator = "ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    private ID id;

    @Column(nullable = false, unique = false)
    private String nome;

    @Column(nullable = false, unique = false)
    private String descricao;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

}
