package xyz.blueben.potentia;

import io.github.apace100.apoli.power.factory.action.meta.SideAction.Side;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SideUtility {
  public static Side from(Entity entity) {
    return from(entity.getWorld());
  }
  public static Side from(World world) {
    return world.isClient ? Side.CLIENT : Side.SERVER;
  }
}
