package org.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import org.blueben.potentia.power.MakeMobsFriendlyPower;
import org.blueben.potentia.power.NullifyDamageDealtPower;
import org.blueben.potentia.power.NullifyDamageTakenPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void injected(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity damage = (LivingEntity)(Object)this;
        for (NullifyDamageTakenPower power : PowerHolderComponent.getPowers(damage, NullifyDamageTakenPower.class)) {
            if (power.isActive() && !source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                power.onUse();
                cir.setReturnValue(false);
                return;
            }
        }

        Entity attacker = source.getAttacker();

        if (!(attacker instanceof LivingEntity)) {
          return;
        }

        for (NullifyDamageDealtPower power : PowerHolderComponent.getPowers(attacker, NullifyDamageDealtPower.class)) {
            if (power.isActive()) {
                power.onUse();
                cir.setReturnValue(false);
                return;
            }
        }
        for (MakeMobsFriendlyPower power : PowerHolderComponent.getPowers(damage, MakeMobsFriendlyPower.class)) {
            if (power.isActive() && power.getEntityList().contains(attacker.getType())) {
                cir.setReturnValue(false);
                return;
            }
        }
    }
}
