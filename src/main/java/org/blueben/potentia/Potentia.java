package org.blueben.potentia;
import net.minecraft.entity.Entity;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;

import java.util.HashMap;
import java.util.List;

public class Potentia implements ModInitializer {

    public static final HashMap<Entity, List<LandingAction>> onLandingEntityActions = new HashMap<>();

    public static int timesToSkip = 6;

    @Override
    public void onInitialize() {
        ModPowers.init();
        ModActions.init();
    }
}
