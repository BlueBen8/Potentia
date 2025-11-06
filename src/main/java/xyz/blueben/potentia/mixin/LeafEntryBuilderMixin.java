package xyz.blueben.potentia.mixin;

import java.util.Arrays;
import java.util.List;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.LootFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.blueben.potentia.LeafEntryBuilderAddFunctions;

@Mixin(LeafEntry.Builder.class)
public class LeafEntryBuilderMixin implements LeafEntryBuilderAddFunctions {

  @Shadow
  @Final
  private List<LootFunction> functions;

  @Override
  public void addFunctions(LootFunction... newFunctions) {
    functions.addAll(Arrays.asList(newFunctions));
  }
}
