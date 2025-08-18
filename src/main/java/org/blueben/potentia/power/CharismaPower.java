package org.blueben.potentia.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;

public class CharismaPower extends Power {
    protected int strength;
    public CharismaPower(PowerType<?> type, LivingEntity entity, int strength) {
        super(type, entity);
        this.strength = strength;


    }
    public void onUse() {
        if (!entity.getWorld().isClient && entity instanceof PlayerEntity p) {

        }
    }

    public int getStrength() {
        return strength;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier("potentia","charisma"),
                new SerializableData().add("strength", SerializableDataTypes.INT, 1),

                data -> {
                    int strength = data.get("strength");
                    return (type, player) -> {
                        CharismaPower power = new CharismaPower(type, player, strength);
                        return power;
                    };
                }).allowCondition();
    }

}
