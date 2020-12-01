package de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.tables;

import de.fraunhofer.iosb.ilt.frostserver.model.EntityType;
import de.fraunhofer.iosb.ilt.frostserver.model.ModelRegistry;
import de.fraunhofer.iosb.ilt.frostserver.model.core.Entity;
import de.fraunhofer.iosb.ilt.frostserver.model.ext.UnitOfMeasurement;
import de.fraunhofer.iosb.ilt.frostserver.persistence.IdManager;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.bindings.JsonBinding;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.bindings.JsonValue;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.bindings.PostGisGeometryBinding;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.factories.EntityFactories;
import static de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.fieldwrapper.StaTimeIntervalWrapper.KEY_TIME_INTERVAL_END;
import static de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.fieldwrapper.StaTimeIntervalWrapper.KEY_TIME_INTERVAL_START;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.relations.RelationManyToManyOrdered;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.relations.RelationOneToMany;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.utils.DataSize;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.utils.PropertyFieldRegistry;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.utils.PropertyFieldRegistry.NFP;
import de.fraunhofer.iosb.ilt.frostserver.persistence.pgjooq.utils.Utils;
import de.fraunhofer.iosb.ilt.frostserver.util.GeoHelper;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import org.geojson.GeoJsonObject;
import org.geolatte.geom.Geometry;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;

public class TableImpMultiDatastreams<J extends Comparable> extends StaTableAbstract<J, TableImpMultiDatastreams<J>> {

    private static final long serialVersionUID = 560943996;

    /**
     * The column <code>public.MULTI_DATASTREAMS.NAME</code>.
     */
    public final TableField<Record, String> colName = createField(DSL.name("NAME"), SQLDataType.CLOB, this);

    /**
     * The column <code>public.MULTI_DATASTREAMS.DESCRIPTION</code>.
     */
    public final TableField<Record, String> colDescription = createField(DSL.name("DESCRIPTION"), SQLDataType.CLOB, this);

    /**
     * The column <code>public.MULTI_DATASTREAMS.OBSERVATION_TYPES</code>.
     */
    public final TableField<Record, JsonValue> colObservationTypes = createField(DSL.name("OBSERVATION_TYPES"), DefaultDataType.getDefaultDataType(TYPE_JSONB), this, "", new JsonBinding());

    /**
     * The column <code>public.MULTI_DATASTREAMS.PHENOMENON_TIME_START</code>.
     */
    public final TableField<Record, OffsetDateTime> colPhenomenonTimeStart = createField(DSL.name("PHENOMENON_TIME_START"), SQLDataType.TIMESTAMPWITHTIMEZONE, this);

    /**
     * The column <code>public.MULTI_DATASTREAMS.PHENOMENON_TIME_END</code>.
     */
    public final TableField<Record, OffsetDateTime> colPhenomenonTimeEnd = createField(DSL.name("PHENOMENON_TIME_END"), SQLDataType.TIMESTAMPWITHTIMEZONE, this);

    /**
     * The column <code>public.MULTI_DATASTREAMS.RESULT_TIME_START</code>.
     */
    public final TableField<Record, OffsetDateTime> colResultTimeStart = createField(DSL.name("RESULT_TIME_START"), SQLDataType.TIMESTAMPWITHTIMEZONE, this);

    /**
     * The column <code>public.MULTI_DATASTREAMS.RESULT_TIME_END</code>.
     */
    public final TableField<Record, OffsetDateTime> colResultTimeEnd = createField(DSL.name("RESULT_TIME_END"), SQLDataType.TIMESTAMPWITHTIMEZONE, this);

    /**
     * The column <code>public.MULTI_DATASTREAMS.UNIT_OF_MEASUREMENTS</code>.
     */
    public final TableField<Record, JsonValue> colUnitOfMeasurements = createField(DSL.name("UNIT_OF_MEASUREMENTS"), DefaultDataType.getDefaultDataType(TYPE_JSONB), this, "", new JsonBinding());
    /**
     * The column <code>public.MULTI_DATASTREAMS.OBSERVED_AREA</code>.
     */
    public final TableField<Record, Geometry> colObservedArea = createField(DSL.name("OBSERVED_AREA"), DefaultDataType.getDefaultDataType(TYPE_GEOMETRY), this, "", new PostGisGeometryBinding());

    /**
     * A helper field for getting the observedArea
     */
    public final Field<String> colObservedAreaText = DSL.field("ST_AsGeoJSON(?)", String.class, colObservedArea);

    /**
     * The column <code>public.MULTI_DATASTREAMS.PROPERTIES</code>.
     */
    public final TableField<Record, JsonValue> colProperties = createField(DSL.name("PROPERTIES"), DefaultDataType.getDefaultDataType(TYPE_JSONB), this, "", new JsonBinding());

    /**
     * The column <code>public.MULTI_DATASTREAMS.ID</code>.
     */
    public final TableField<Record, J> colId = createField(DSL.name("ID"), getIdType(), this);

    /**
     * The column <code>public.MULTI_DATASTREAMS.SENSOR_ID</code>.
     */
    public final TableField<Record, J> colSensorId = createField(DSL.name("SENSOR_ID"), getIdType(), this);

    /**
     * The column <code>public.MULTI_DATASTREAMS.THING_ID</code>.
     */
    public final TableField<Record, J> colThingId = createField(DSL.name("THING_ID"), getIdType(), this);

    /**
     * Create a <code>public.MULTI_DATASTREAMS</code> table reference
     */
    public TableImpMultiDatastreams(DataType<J> idType) {
        super(idType, DSL.name("MULTI_DATASTREAMS"), null);
    }

    private TableImpMultiDatastreams(Name alias, TableImpMultiDatastreams<J> aliased) {
        super(aliased.getIdType(), alias, aliased);
    }

    @Override
    public void initRelations() {
        final TableCollection<J> tables = getTables();
        final ModelRegistry modelRegistry = getModelRegistry();
        final TableImpThings<J> thingsTable = tables.getTableForClass(TableImpThings.class);
        registerRelation(new RelationOneToMany<>(this, thingsTable, modelRegistry.THING)
                .setSourceFieldAccessor(TableImpMultiDatastreams::getThingId)
                .setTargetFieldAccessor(TableImpThings::getId)
        );
        final TableImpSensors<J> sensorsTable = tables.getTableForClass(TableImpSensors.class);
        registerRelation(new RelationOneToMany<>(this, sensorsTable, modelRegistry.SENSOR)
                .setSourceFieldAccessor(TableImpMultiDatastreams::getSensorId)
                .setTargetFieldAccessor(TableImpSensors::getId)
        );
        final TableImpMultiDatastreamsObsProperties<J> tableMdOp = tables.getTableForClass(TableImpMultiDatastreamsObsProperties.class);
        final TableImpObsProperties<J> tableObsProp = tables.getTableForClass(TableImpObsProperties.class);
        registerRelation(new RelationManyToManyOrdered<>(this, tableMdOp, tableObsProp, modelRegistry.OBSERVED_PROPERTY)
                .setOrderFieldAcc((TableImpMultiDatastreamsObsProperties<J> table) -> table.colRank)
                .setAlwaysDistinct(true)
                .setSourceFieldAcc(TableImpMultiDatastreams::getId)
                .setSourceLinkFieldAcc(TableImpMultiDatastreamsObsProperties::getMultiDatastreamId)
                .setTargetLinkFieldAcc(TableImpMultiDatastreamsObsProperties::getObsPropertyId)
                .setTargetFieldAcc(TableImpObsProperties::getId)
        );
        final TableImpObservations<J> tableObs = tables.getTableForClass(TableImpObservations.class);
        registerRelation(new RelationOneToMany<>(this, tableObs, modelRegistry.OBSERVATION, true)
                .setSourceFieldAccessor(TableImpMultiDatastreams::getId)
                .setTargetFieldAccessor(TableImpObservations::getMultiDatastreamId)
        );
    }

    @Override
    public void initProperties(final EntityFactories<J> entityFactories) {
        final IdManager idManager = entityFactories.getIdManager();
        ModelRegistry modelRegistry = getModelRegistry();
        pfReg.addEntryId(idManager, TableImpMultiDatastreams::getId);
        pfReg.addEntryString(modelRegistry.EP_NAME, table -> table.colName);
        pfReg.addEntryString(modelRegistry.EP_DESCRIPTION, table -> table.colDescription);
        pfReg.addEntry(modelRegistry.EP_OBSERVATIONTYPE, null,
                new PropertyFieldRegistry.ConverterRecordDeflt<>(
                        (TableImpMultiDatastreams<J> table, Record tuple, Entity entity, DataSize dataSize) -> {
                            entity.setProperty(modelRegistry.EP_OBSERVATIONTYPE, "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_ComplexObservation");
                        }, null, null));
        pfReg.addEntry(modelRegistry.EP_MULTIOBSERVATIONDATATYPES, table -> table.colObservationTypes,
                new PropertyFieldRegistry.ConverterRecordDeflt<>(
                        (TableImpMultiDatastreams<J> table, Record tuple, Entity entity, DataSize dataSize) -> {
                            final JsonValue fieldJsonValue = Utils.getFieldJsonValue(tuple, table.colObservationTypes);
                            List<String> observationTypes = fieldJsonValue.getValue(Utils.TYPE_LIST_STRING);
                            dataSize.increase(fieldJsonValue.getStringLength());
                            entity.setProperty(modelRegistry.EP_MULTIOBSERVATIONDATATYPES, observationTypes);
                        },
                        (table, entity, insertFields) -> {
                            insertFields.put(table.colObservationTypes, new JsonValue(entity.getProperty(modelRegistry.EP_MULTIOBSERVATIONDATATYPES)));
                        },
                        (table, entity, updateFields, message) -> {
                            updateFields.put(table.colObservationTypes, new JsonValue(entity.getProperty(modelRegistry.EP_MULTIOBSERVATIONDATATYPES)));
                            message.addField(modelRegistry.EP_MULTIOBSERVATIONDATATYPES);
                        }));
        pfReg.addEntry(modelRegistry.EP_OBSERVEDAREA,
                new PropertyFieldRegistry.ConverterRecordDeflt<>(
                        (table, tuple, entity, dataSize) -> {
                            String observedArea = tuple.get(table.colObservedAreaText);
                            if (observedArea != null) {
                                try {
                                    GeoJsonObject area = GeoHelper.parseGeoJson(observedArea);
                                    entity.setProperty(modelRegistry.EP_OBSERVEDAREA, area);
                                } catch (IOException e) {
                                    // It's not a polygon, probably a point or a line.
                                }
                            }
                        }, null, null),
                new NFP<>("s", table -> table.colObservedAreaText));
        pfReg.addEntryNoSelect(modelRegistry.EP_OBSERVEDAREA, "g", table -> table.colObservedArea);
        pfReg.addEntry(modelRegistry.EP_PHENOMENONTIME_DS,
                new PropertyFieldRegistry.ConverterTimeInterval<>(modelRegistry.EP_PHENOMENONTIME_DS, table -> table.colPhenomenonTimeStart, table -> table.colPhenomenonTimeEnd),
                new NFP<>(KEY_TIME_INTERVAL_START, table -> table.colPhenomenonTimeStart),
                new NFP<>(KEY_TIME_INTERVAL_END, table -> table.colPhenomenonTimeEnd));
        pfReg.addEntryMap(modelRegistry.EP_PROPERTIES, table -> table.colProperties);
        pfReg.addEntry(modelRegistry.EP_RESULTTIME_DS,
                new PropertyFieldRegistry.ConverterTimeInterval<>(modelRegistry.EP_PHENOMENONTIME_DS, table -> table.colResultTimeStart, table -> table.colResultTimeEnd),
                new NFP<>(KEY_TIME_INTERVAL_START, table -> table.colResultTimeStart),
                new NFP<>(KEY_TIME_INTERVAL_END, table -> table.colResultTimeEnd));
        pfReg.addEntry(modelRegistry.EP_UNITOFMEASUREMENTS, table -> table.colUnitOfMeasurements,
                new PropertyFieldRegistry.ConverterRecordDeflt<>(
                        (TableImpMultiDatastreams<J> table, Record tuple, Entity entity, DataSize dataSize) -> {
                            final JsonValue fieldJsonValue = Utils.getFieldJsonValue(tuple, table.colUnitOfMeasurements);
                            dataSize.increase(fieldJsonValue.getStringLength());
                            List<UnitOfMeasurement> units = fieldJsonValue.getValue(Utils.TYPE_LIST_UOM);
                            entity.setProperty(modelRegistry.EP_UNITOFMEASUREMENTS, units);
                        },
                        (table, entity, insertFields) -> {
                            insertFields.put(table.colUnitOfMeasurements, new JsonValue(entity.getProperty(modelRegistry.EP_UNITOFMEASUREMENTS)));
                        },
                        (table, entity, updateFields, message) -> {
                            updateFields.put(table.colUnitOfMeasurements, new JsonValue(entity.getProperty(modelRegistry.EP_UNITOFMEASUREMENTS)));
                            message.addField(modelRegistry.EP_UNITOFMEASUREMENTS);
                        }));
        pfReg.addEntry(modelRegistry.NP_SENSOR, TableImpMultiDatastreams::getSensorId, idManager);
        pfReg.addEntry(modelRegistry.NP_THING, TableImpMultiDatastreams::getThingId, idManager);
        pfReg.addEntry(modelRegistry.NP_OBSERVEDPROPERTIES, TableImpMultiDatastreams::getId, idManager);
        pfReg.addEntry(modelRegistry.NP_OBSERVATIONS, TableImpMultiDatastreams::getId, idManager);
    }

    @Override
    public EntityType getEntityType() {
        return getModelRegistry().MULTI_DATASTREAM;
    }

    @Override
    public TableField<Record, J> getId() {
        return colId;
    }

    public TableField<Record, J> getSensorId() {
        return colSensorId;
    }

    public TableField<Record, J> getThingId() {
        return colThingId;
    }

    @Override
    public TableImpMultiDatastreams<J> as(Name alias) {
        return new TableImpMultiDatastreams<>(alias, this);
    }

    @Override
    public TableImpMultiDatastreams<J> as(String alias) {
        return new TableImpMultiDatastreams<>(DSL.name(alias), this);
    }

    @Override
    public TableImpMultiDatastreams<J> getThis() {
        return this;
    }

}
