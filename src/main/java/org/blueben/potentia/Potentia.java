package org.blueben.potentia;

import java.util.HashMap;
import java.util.List;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;

public class Potentia implements ModInitializer {

    public static final HashMap<Entity, List<LandingAction>> onLandingEntityActions = new HashMap<>();

    @Override
    public void onInitialize() {
        ModPowers.init();
        ModActions.init();
    }
}
