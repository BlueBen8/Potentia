package org.blueben.potentia;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;
import org.blueben.potentia.power.NullifyDamageDealtPower;
import org.blueben.potentia.power.NullifyDamageTakenPower;


public class ModPowers {
    public static final PowerFactory<?> NULLIFY_DAMAGE_DEALT = NullifyDamageDealtPower.createFactory();
    public static final PowerFactory<?> NULLIFY_DAMAGE_TAKEN = NullifyDamageTakenPower.createFactory();

    public static void init() {
        Registry.register(ApoliRegistries.POWER_FACTORY, NULLIFY_DAMAGE_DEALT.getSerializerId(), NULLIFY_DAMAGE_DEALT);
        Registry.register(ApoliRegistries.POWER_FACTORY, NULLIFY_DAMAGE_TAKEN.getSerializerId(), NULLIFY_DAMAGE_TAKEN);
    }
}

