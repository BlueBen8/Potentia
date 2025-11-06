package xyz.blueben.potentia.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.blueben.potentia.HasBarterTarget;
import xyz.blueben.potentia.LeafEntryBuilderAddFunctions;
import xyz.blueben.potentia.LootTableEntryBuilderAddConditions;
import xyz.blueben.potentia.power.BarterLuckPower;

@Debug(export = true)

@Mixin(LootTable.class)
public abstract class LootTableMixin {

  @Shadow
  @Final
  public LootPool[] pools;

  @Shadow
  @Final
  private LootContextType type;

  @Shadow
  @Final
  public LootFunction[] functions;

  @Shadow
  @Final
  @Nullable
  private Identifier randomSequenceId;

  @Shadow
  protected abstract ObjectArrayList<ItemStack> generateLoot(LootContext context);

  @Inject(at = @At("HEAD"), method = "generateLoot(Lnet/minecraft/loot/context/LootContextParameterSet;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", cancellable = true)
  private void generateLoot(LootContextParameterSet parameters,
      CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir) {
    if (!parameters.contains(LootContextParameters.THIS_ENTITY)) {
      return;
    }

    Entity entity = parameters.get(LootContextParameters.THIS_ENTITY);

    if (!(entity instanceof HasBarterTarget barterer)) {
      return;
    }

    PlayerEntity player = barterer.getBarterTarget();

    for (BarterLuckPower power : PowerHolderComponent.getPowers(player, BarterLuckPower.class)) {
      if (power.isActive()) {
        Map<Identifier, Integer> weightTable = power.getWeightTable();

        LootPool[] modifiedPools = Arrays.stream(pools).map((pool) -> {
          LootPoolEntry[] modifiedEntries = Arrays.stream(pool.entries)
              .map(entry -> {
                if (!(entry instanceof ItemEntry itemEntry)) {
                  return entry;
                }

                Identifier itemId = Registries.ITEM.getId(itemEntry.item);
                Integer modifiedItemWeight = weightTable.get(itemId);

                if (modifiedItemWeight == null) {
                  return entry;
                }

                LeafEntry.Builder<?> itemEntryBuilder = ItemEntry.builder(itemEntry.item)
                    .weight(modifiedItemWeight)
                    .quality(itemEntry.quality);

                if (itemEntryBuilder instanceof LootTableEntryBuilderAddConditions itemEntryAddConditions) {
                  itemEntryAddConditions.addConditions(itemEntry.conditions);
                }

                if (itemEntryBuilder instanceof LeafEntryBuilderAddFunctions itemEntryAddFunctions) {
                  itemEntryAddFunctions.addFunctions(itemEntry.functions);
                }

                return itemEntryBuilder.build();
              }).toArray(LootPoolEntry[]::new);

          return LootPool.builder()
              .with(List.of(modifiedEntries))
              .conditionally(List.of(pool.conditions))
              .apply(List.of(pool.functions))
              .rolls(pool.rolls)
              .bonusRolls(pool.bonusRolls).build();
        }).toArray(LootPool[]::new);

        LootTable modifiedLootTable = LootTable.builder()
            .type(type)
            .randomSequenceId(LootTables.PIGLIN_BARTERING_GAMEPLAY)
            .pools(List.of(modifiedPools))
            .apply(List.of(functions))
            .build();

        cir.setReturnValue(((LootTableMixin)(Object) modifiedLootTable).generateLoot((new LootContext.Builder(parameters)).build(this.randomSequenceId)));
        cir.cancel();
      }
    }

  }
}
