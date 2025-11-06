package xyz.blueben.potentia;

import net.minecraft.loot.condition.LootCondition;

public interface LootTableEntryBuilderAddConditions {
  void addConditions(LootCondition... conditions);
}
