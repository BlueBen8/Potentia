package xyz.blueben.potentia.mixin;

import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.blueben.potentia.HasBarterTarget;

@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends AbstractPiglinEntity
        implements CrossbowUser,
        InventoryOwner, HasBarterTarget {

    @Unique
    protected PlayerEntity potentia$barterTarget;

    public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public PlayerEntity getBarterTarget() {
        return potentia$barterTarget;
    }

    @Inject(method = "loot", at = @At("HEAD"))
    protected void loot(ItemEntity item, CallbackInfo ci) {
        if (item.getOwner() instanceof PlayerEntity player) {
            potentia$barterTarget = player;
        }
    }
    @Inject(method = "interactMob", at = @At("HEAD"))
    protected void injected(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        potentia$barterTarget = player;
    }
}
