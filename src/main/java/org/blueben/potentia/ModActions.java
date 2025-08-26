package org.blueben.potentia;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;
import org.blueben.potentia.action.entity.CrashDownEntityAction;



public class ModActions {
    public static final ActionFactory<Entity> CRASH_DOWN = CrashDownEntityAction.createFactory();

    public static void init() {
        Registry.register(ApoliRegistries.ENTITY_ACTION, CRASH_DOWN.getSerializerId(), CRASH_DOWN);
    }
}

