package xyz.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Targeter;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import xyz.blueben.potentia.power.MakeMobsFriendlyPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements Targeter {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    public void setTarget(LivingEntity target, CallbackInfo ci) {
        if (target instanceof PlayerEntity player) {
            for (MakeMobsFriendlyPower power : PowerHolderComponent.getPowers(player, MakeMobsFriendlyPower.class)) {
                if (power.isActive() && power.getEntityList().contains(getType())) {
                    ci.cancel();
                }
            }
        }

    }
}
