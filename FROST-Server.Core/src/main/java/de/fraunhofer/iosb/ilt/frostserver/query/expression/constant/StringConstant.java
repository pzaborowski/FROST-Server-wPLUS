/*
 * Copyright (C) 2016 Fraunhofer Institut IOSB, Fraunhoferstr. 1, D 76131
 * Karlsruhe, Germany.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.fraunhofer.iosb.ilt.frostserver.query.expression.constant;

import de.fraunhofer.iosb.ilt.frostserver.query.expression.ExpressionVisitor;

/**
 *
 * @author jab
 */
public class StringConstant extends Constant<String> {

    public StringConstant(String value) {
        super(value.replaceAll("''", "'"));
    }

    @Override
    public <O> O accept(ExpressionVisitor<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toUrl() {
        return "'" + getValue().replaceAll("'", "''") + "'";
    }

}