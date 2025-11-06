package xyz.blueben.potentia.mixin;

import java.util.Arrays;
import java.util.List;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.LootPoolEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.blueben.potentia.LootTableEntryBuilderAddConditions;

@Mixin(LootPoolEntry.Builder.class)
public class LootTableEntryBuilderMixin implements LootTableEntryBuilderAddConditions {

  @Shadow
  @Final
  private List<LootCondition> conditions;

  @Override
  public void addConditions(LootCondition... newConditions) {
    conditions.addAll(Arrays.asList(newConditions));
  }
}
