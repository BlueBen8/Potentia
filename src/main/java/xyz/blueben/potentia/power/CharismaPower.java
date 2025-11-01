package xyz.blueben.potentia.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.calio.util.TagLike;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class CharismaPower extends Power {
    protected TagLike<EntityType<?>> entityList;
    protected int strength;
    protected boolean giveGifts;
    public CharismaPower(PowerType<?> type, LivingEntity entity, TagLike<EntityType<?>> entityList, int strength, boolean giveGifts) {
        super(type, entity);
        this.entityList = entityList;
        this.strength = strength;
        this.giveGifts = giveGifts;
    }

    public TagLike<EntityType<?>> getEntityList() {
        return entityList;
    }

    public int getStrength() {
        return strength;
    }

    public boolean isGiveGifts() {
        return giveGifts;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier("potentia","charisma"),
                new SerializableData()
                        .add("entity_list", SerializableDataTypes.ENTITY_TYPE_TAG_LIKE)
                        .add("strength", SerializableDataTypes.INT, 1)
                        .add("give_gifts", SerializableDataTypes.BOOLEAN, false),

                data -> {
                    TagLike<EntityType<?>> entityList = data.get("entity_list");
                    int strength = data.get("strength");
                    boolean giveGifts = data.get("give_gifts");
                    return (type, player) -> {
                        CharismaPower power = new CharismaPower(type, player, entityList, strength, giveGifts);
                        return power;
                    };
                }).allowCondition();
    }

}
