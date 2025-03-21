package org.blueben.potentia;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;
import org.blueben.potentia.power.NullifyDamageDealtPower;


public class ModPowers {
    public static final PowerFactory<?> NULLIFY_DAMAGE_DEALT = NullifyDamageDealtPower.createFactory();

    public static void init() {
        Registry.register(ApoliRegistries.POWER_FACTORY, NULLIFY_DAMAGE_DEALT.getSerializerId(), NULLIFY_DAMAGE_DEALT);
    }
}

