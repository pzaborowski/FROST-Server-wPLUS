/*
 * Copyright (C) 2020 Fraunhofer Institut IOSB, Fraunhoferstr. 1, D 76131
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
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package de.fraunhofer.iosb.ilt.frostserver.property;

import de.fraunhofer.iosb.ilt.frostserver.model.EntityType;
import de.fraunhofer.iosb.ilt.frostserver.model.core.Entity;
import de.fraunhofer.iosb.ilt.frostserver.property.type.TypeEntity;
import java.util.Objects;

/**
 *
 * @author hylke
 */
public class EntityPropertyCustomLink implements Property<Entity> {

    private static final String UNSUPPORTED = "Not supported on custom properties.";

    private final String name;
    private final EntityType targetEntityType;

    public EntityPropertyCustomLink(String name, EntityType targetEntityType) {
        this.name = name;
        this.targetEntityType = targetEntityType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getJsonName() {
        return getName();
    }

    @Override
    public TypeEntity getType() {
        return new TypeEntity(targetEntityType);
    }

    public EntityType getTargetEntityType() {
        return targetEntityType;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public Entity getFrom(Entity entity) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }

    @Override
    public void setOn(Entity entity, Entity value) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }

    @Override
    public boolean isSetOn(Entity entity) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityPropertyCustomLink other = (EntityPropertyCustomLink) obj;
        return Objects.equals(this.name, other.name)
                && Objects.equals(this.targetEntityType, other.targetEntityType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, targetEntityType);
    }

}
