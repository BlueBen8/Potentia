package org.blueben.potentia.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.calio.util.TagLike;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.util.List;

public class MakeMobsFriendlyPower extends Power {
    protected TagLike<EntityType<?>> entityList;

    public MakeMobsFriendlyPower(PowerType<Power> type, LivingEntity player, TagLike<EntityType<?>> entityList) {
        super(type, player);
        this.entityList = entityList;
    }

    public TagLike<EntityType<?>> getEntityList() { return entityList; }


    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier("potentia","make_mobs_friendly"),
        new SerializableData().add("entity_list", SerializableDataTypes.ENTITY_TYPE_TAG_LIKE, null),

        data -> {
            TagLike<EntityType<?>> entityList = data.get("entity_list");
            return (type, player) -> {
                MakeMobsFriendlyPower power = new MakeMobsFriendlyPower(type, player, entityList);
                return power;
            };
        }).allowCondition();

    }
}
