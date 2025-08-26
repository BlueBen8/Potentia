package org.blueben.potentia.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.blueben.potentia.Potentia;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Shadow private World world;

    @Inject(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onLandedUpon(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V"))
    private void injected(CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        var array = Potentia.onLandingEntityActions.get(entity);
        if (array == null || array.isEmpty()) {
            return;
        }
        System.out.println("Entity has landed! (Bingus) -" + " " + entity.getX() + " " + entity.getY() + " " + entity.getZ());
        var iterated = array.iterator();
        while (iterated.hasNext()) {
            var action = iterated.next();

            if (!action.isRunOnClient() && !action.isRunOnServer()) {
                iterated.remove();
                continue;
            }

            if (world.isClient && !action.isRunOnClient()) {
                continue;
            }

            if (!world.isClient && !action.isRunOnServer()) {
                continue;
            }

            var entityAction = action.getAction();

            if (entityAction != null) {
                entityAction.accept(entity);
            }

            iterated.remove();
        }
        System.out.println("array has been cleared (zingus)");
    }
}
