package org.blueben.potentia;

import io.github.apace100.apoli.power.factory.action.meta.SideAction.Side;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.fabricmc.api.ModInitializer;

public class Potentia implements ModInitializer {

    public static final HashMap<Side, HashMap<UUID, List<LandingAction>>> onLandingEntityActions = new HashMap<>() {{
      put(Side.CLIENT, new HashMap<>());
      put(Side.SERVER, new HashMap<>());
    }};

    @Override
    public void onInitialize() {
        ModPowers.init();
        ModActions.init();
    }
}
