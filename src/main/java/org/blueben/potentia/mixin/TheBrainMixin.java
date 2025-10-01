package org.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.blueben.potentia.power.MakeMobsFriendlyPower;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Optional;

@Mixin(Brain.class)
public class TheBrainMixin {
    @Shadow @Final private Map<MemoryModuleType<?>, Optional<? extends Memory<?>>> memories;

    @Unique
    private ServerWorld world;

    @Unique
    private EntityType<?> entityType;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(ServerWorld world, LivingEntity entity, CallbackInfo ci) {
      this.world = world;
      entityType = entity.getType();
    }

//    @Inject(method = "getOptionalRegisteredMemory", at = @At("HEAD"), cancellable = true)
//    public void brain(MemoryModuleType<Object> type, CallbackInfoReturnable<Optional<Object>> cir) {
//      if (shouldGaslightGatekeepGirlboss(type)) {
//        cir.setReturnValue(Optional.empty());
//      }
//    }
//
//    @Inject(method = "getOptionalMemory", at = @At("HEAD"), cancellable = true)
//    public void getOptionalMemory(MemoryModuleType<Object> type, CallbackInfoReturnable<Optional<Object>> cir) {
//      if (shouldGaslightGatekeepGirlboss(type)) {
//        cir.setReturnValue(Optional.empty());
//      }
//    }
//
//    @Inject(method = "isMemoryInState", at = @At("HEAD"), cancellable = true)
//    public void isMemoryInState(MemoryModuleType<?> type, MemoryModuleState state, CallbackInfoReturnable<Boolean> cir) {
//      if (shouldGaslightGatekeepGirlboss(type)) {
//        cir.setReturnValue(false);
//      }
//    }

    @Inject(method = "setMemory", at = @At("HEAD"), cancellable = true)
    public void setMemory(MemoryModuleType<Object> type, Optional<? extends Memory<?>> membory, CallbackInfo ci) {
      if (membory.isEmpty()) {
        return;
      }

      Memory<?> memeory = membory.get();

      switch (type.toString().split(":")[1]) {
        case "attack_target", "roar_target" -> {
          if (memeory.getValue() instanceof PlayerEntity morebery) {
            for (MakeMobsFriendlyPower power : PowerHolderComponent.getPowers(morebery, MakeMobsFriendlyPower.class)) {
              if (power.isActive() && power.getEntityList().contains(entityType)) {
                ci.cancel();
              }
            }
          }
        }
        case "ram_target" -> {
          if (memeory.getValue() instanceof Vec3d blockPos) {
            PlayerEntity morebery = world.getClosestPlayer(blockPos.x, blockPos.y, blockPos.z, 1, true);

            for (MakeMobsFriendlyPower power : PowerHolderComponent.getPowers(morebery, MakeMobsFriendlyPower.class)) {
              if (power.isActive() && power.getEntityList().contains(entityType)) {
                ci.cancel();
              }
            }
          }
        }
      }
    }

//    @Unique
//    public boolean shouldGaslightGatekeepGirlboss(MemoryModuleType<?> type) {
//      switch (type.toString().split(":")[1]) {
//        case "attack_target", "ram_target", "roar_target" -> {
//          Optional<? extends Memory<?>> membory = memories.get(type);
//
//          if (membory != null && membory.isPresent()) {
//            Memory<?> memeory = membory.get();
//            if (memeory.getValue() instanceof PlayerEntity morebery) {
//              for (MakeMobsFriendlyPower power : PowerHolderComponent.getPowers(morebery, MakeMobsFriendlyPower.class)) {
//                if (power.isActive() && power.getEntityList().contains(entityType)) {
//                  return true;
//                }
//              }
//            }
//          }
//        }
//      }
//
//      return false;
//    }
}
