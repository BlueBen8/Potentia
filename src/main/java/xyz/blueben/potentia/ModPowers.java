package xyz.blueben.potentia;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;
import xyz.blueben.potentia.power.*;


public class ModPowers {
    public static final PowerFactory<?> NULLIFY_DAMAGE_DEALT = NullifyDamageDealtPower.createFactory();
    public static final PowerFactory<?> NULLIFY_DAMAGE_TAKEN = NullifyDamageTakenPower.createFactory();
    public static final PowerFactory<?> CHARISMA = CharismaPower.createFactory();
    public static final PowerFactory<?> MAKE_MOBS_FRIENDLY = MakeMobsFriendlyPower.createFactory();
    public static final PowerFactory<?> MAKE_MOBS_HOSTILE = MakeMobsHostilePower.createFactory();

    public static void init() {
        Registry.register(ApoliRegistries.POWER_FACTORY, NULLIFY_DAMAGE_DEALT.getSerializerId(), NULLIFY_DAMAGE_DEALT);
        Registry.register(ApoliRegistries.POWER_FACTORY, NULLIFY_DAMAGE_TAKEN.getSerializerId(), NULLIFY_DAMAGE_TAKEN);
        Registry.register(ApoliRegistries.POWER_FACTORY, CHARISMA.getSerializerId(), CHARISMA);
        Registry.register(ApoliRegistries.POWER_FACTORY, MAKE_MOBS_FRIENDLY.getSerializerId(), MAKE_MOBS_FRIENDLY);
        Registry.register(ApoliRegistries.POWER_FACTORY, MAKE_MOBS_HOSTILE.getSerializerId(), MAKE_MOBS_HOSTILE);
    }
}

