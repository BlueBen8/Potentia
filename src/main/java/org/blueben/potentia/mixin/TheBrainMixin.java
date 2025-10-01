package org.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import org.blueben.potentia.power.MakeMobsFriendlyPower;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

@Mixin(Brain.class)
public class TheBrainMixin {
    @Shadow @Final private Map<MemoryModuleType<?>, Optional<? extends Memory<?>>> memories;

    @Inject(method = "getOptionalRegisteredMemory", at = @At("HEAD"), cancellable = true)
    public void brain(MemoryModuleType<Object> type, CallbackInfoReturnable<Optional<Object>> cir) {
    switch (Registries.MEMORY_MODULE_TYPE.getId(type).getPath()) {
        case "attack_target", "ram_target" -> {

            Optional<? extends Memory<?>> membory  = memories.get(type);
            membory.ifPresent(memeory-> {
                if (memeory.getValue() instanceof PlayerEntity morebery) {
                    for (MakeMobsFriendlyPower power : PowerHolderComponent.getPowers(morebery, MakeMobsFriendlyPower.class)) {
                        if (power.isActive()) {
                            cir.setReturnValue(Optional.empty());
                            return;
                        }
                    }
                }
            });

        }
    }
    }
}
