package com.github.brunoabdon.commons.modelo;
/*
 * Copyright (C) 2017 Bruno Abdon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Classe base pra {@link Entidade}s com {@link Entidade#getId() id} do tipo
 * {@link String}.
 *
 * @author bruno abdon
 */
@MappedSuperclass
public class EntidadeBaseString implements Entidade<String>{

    private static final long serialVersionUID = 3764778963499113464L;

	@Id
    private String id;

    public EntidadeBaseString() {}

    public EntidadeBaseString(final String id) {
        this.id = id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

}
