package xyz.blueben.potentia.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class CharismaPower extends Power {
    protected int strength;
    protected boolean giveGifts;
    public CharismaPower(PowerType<?> type, LivingEntity entity, int strength, boolean giveGifts) {
        super(type, entity);
        this.strength = strength;
        this.giveGifts = giveGifts;


    }

    public int getStrength() {
        return strength;
    }

    public boolean isGiveGifts() {
        return giveGifts;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier("potentia","charisma"),
                new SerializableData().add("strength", SerializableDataTypes.INT, 1).add("give_gifts", SerializableDataTypes.BOOLEAN, true),

                data -> {
                    int strength = data.get("strength");
                    boolean giveGifts = data.get("give_gifts");
                    return (type, player) -> {
                        CharismaPower power = new CharismaPower(type, player, strength, giveGifts);
                        return power;
                    };
                }).allowCondition();
    }

}
