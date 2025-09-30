package org.blueben.potentia.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.util.List;

public class MakeMobsFriendlyPower extends Power {
    protected List<EntityType<?>> entityTypes;
    protected List<TagKey<EntityType<?>>> entityTypeTags;

    public MakeMobsFriendlyPower(PowerType<Power> type, LivingEntity player, List<EntityType<?>> entityTypes, List<TagKey<EntityType<?>>> entityTypeTags) {
        super(type, player);
        this.entityTypes = entityTypes;
        this.entityTypeTags = entityTypeTags;
    }

    public List<EntityType<?>> getEntityTypes() { return entityTypes; }

    public List<TagKey<EntityType<?>>> getEntityTypeTags() { return entityTypeTags; }


    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier("potentia","make_mobs_friendly"),
        new SerializableData().add("entity_types", SerializableDataType.list(SerializableDataTypes.ENTITY_TYPE), null).add("entity_type_tags", SerializableDataType.list(SerializableDataTypes.ENTITY_TAG), null),

        data -> {
            List<EntityType<?>> entityTypes = data.get("entity_types");
            List<TagKey<EntityType<?>>> entityTypeTags = data.get("entity_type_tags");
            return (type, player) -> {
                MakeMobsFriendlyPower power = new MakeMobsFriendlyPower(type, player, entityTypes, entityTypeTags);
                return power;
            };
        }).allowCondition();

    }
}
