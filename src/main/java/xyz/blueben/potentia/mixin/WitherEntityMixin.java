package xyz.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import xyz.blueben.potentia.power.MakeMobsFriendlyPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherEntity.class)
public abstract class WitherEntityMixin extends HostileEntity implements SkinOverlayOwner, RangedAttackMob {

  protected WitherEntityMixin(EntityType<? extends HostileEntity> entityType,
      World world) {
    super(entityType, world);
  }

  @Inject(method = "setTrackedEntityId", at = @At("HEAD"), cancellable = true)
  public void gaslightGatekeepMakeMobsFriendly(int headIndex, int id, CallbackInfo ci) {
    if (getWorld().getEntityById(id) instanceof PlayerEntity player) {
      for (MakeMobsFriendlyPower power : PowerHolderComponent.getPowers(player, MakeMobsFriendlyPower.class)) {
        if (power.isActive() && power.getEntityList().contains(getType())) {
          ci.cancel();
        }
      }
    }
  }
}
