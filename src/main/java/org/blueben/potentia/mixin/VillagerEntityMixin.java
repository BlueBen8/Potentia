package org.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.TradeOffer;
import org.blueben.potentia.power.CharismaPower;
import org.blueben.potentia.power.NullifyDamageDealtPower;
import org.blueben.potentia.power.NullifyDamageTakenPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
    @Inject(method = "prepareOffersFor", at = @At("HEAD"), cancellable = true)
    private void injected(PlayerEntity player, CallbackInfo ci) {
        VillagerEntity villager = (VillagerEntity)(Object)this;

        for (CharismaPower power : PowerHolderComponent.getPowers(player, CharismaPower.class)) {
            if (power.isActive()) {
                int j = power.getStrength() * 15;
                for (TradeOffer tradeOffer2 : villager.getOffers()) {
                    double d = j / 100.0;
                    tradeOffer2.increaseSpecialPrice(-j);
                    System.out.println("Villager trade decreased.");
                }
                ci.cancel();
                return;
            }
        }
    }
}
