package org.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.TradeOffer;
import org.blueben.potentia.power.CharismaPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
    @Inject(method = "prepareOffersFor", at = @At("HEAD"), cancellable = true)
    private void injected(PlayerEntity player, CallbackInfo ci) {
        VillagerEntity villager = (VillagerEntity)(Object)this;

        for (CharismaPower power : PowerHolderComponent.getPowers(player, CharismaPower.class)) {
            if (power.isActive()) {
                int j = power.getStrength() * 15;
                for (TradeOffer tradeOffer2 : villager.getOffers()) {
                    tradeOffer2.clearSpecialPrice();

                    var cost = tradeOffer2.getAdjustedFirstBuyItem().getCount();

                    var reducedCost = cost * (1 - (j / 100.0));

                    var costToDeduct = cost-reducedCost;

                    tradeOffer2.increaseSpecialPrice((int) -costToDeduct);
                    System.out.printf("Villager trade for %s decreased by %.2f (from %d to %d)%n", tradeOffer2.getOriginalFirstBuyItem().getName().getString(), costToDeduct, cost, (int) (cost-costToDeduct));
                }
                ci.cancel();
                return;
            }
        }
    }
}
