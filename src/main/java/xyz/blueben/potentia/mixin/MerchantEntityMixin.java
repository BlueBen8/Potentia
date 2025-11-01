package xyz.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.Npc;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import xyz.blueben.potentia.power.CharismaPower;
import org.spongepowered.asm.mixin.Mixin;

import java.util.OptionalInt;

@Mixin(MerchantEntity.class)
public abstract class MerchantEntityMixin extends PassiveEntity
        implements InventoryOwner,
        Npc,
        Merchant {
    protected MerchantEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void sendOffers(PlayerEntity player, Text test, int levelProgress) {

        for (CharismaPower power : PowerHolderComponent.getPowers(player, CharismaPower.class)) {
            if (power.isActive()) {
                int j = power.getStrength() * 15;
                for (TradeOffer tradeOffer2 : this.getOffers()) {
                    tradeOffer2.clearSpecialPrice();

                    var cost = tradeOffer2.getAdjustedFirstBuyItem().getCount();

                    var reducedCost = cost * (1 - (j / 100.0));

                    var costToDeduct = cost-reducedCost;

                    tradeOffer2.increaseSpecialPrice((int) -costToDeduct);
                    System.out.printf("Villager trade for %s decreased by %.2f (from %d to %d)%n", tradeOffer2.getOriginalFirstBuyItem().getName().getString(), costToDeduct, cost, (int) (cost-costToDeduct));
                }
                TradeOfferList tradeOfferList;
                OptionalInt optionalInt = player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerx) -> new MerchantScreenHandler(syncId, playerInventory, this), test));
                if (optionalInt.isPresent() && !(tradeOfferList = this.getOffers()).isEmpty()) {
                    player.sendTradeOffers(optionalInt.getAsInt(), tradeOfferList, levelProgress, this.getExperience(), this.isLeveledMerchant(), this.canRefreshTrades());
                }
                return;
            }
        }
    }
}