package org.blueben.potentia.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.minecraft.entity.LivingEntity;

public class MakeMobsHostilePower extends Power {
    public MakeMobsHostilePower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
    public static PowerFactory createFactory() {

        return null;
    }
}
