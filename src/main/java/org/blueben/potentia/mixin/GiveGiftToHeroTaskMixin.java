package org.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.ai.brain.task.GiveGiftsToHeroTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.TradeOffer;
import org.blueben.potentia.power.CharismaPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GiveGiftsToHeroTask.class)
public class GiveGiftToHeroTaskMixin {
    @Inject(method = "isHero", at = @At("HEAD"), cancellable = true)
    private void injected(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {

        for (CharismaPower power : PowerHolderComponent.getPowers(player, CharismaPower.class)) {
            if (power.isActive() && power.isGiveGifts()) {
                cir.setReturnValue(true);
            }
        }
    }
}
