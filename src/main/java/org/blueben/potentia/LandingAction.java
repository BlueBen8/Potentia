package org.blueben.potentia;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import net.minecraft.entity.Entity;

public class LandingAction {
    protected ActionFactory<Entity>.Instance action;
    protected int timesOnGround = 0;
    public LandingAction(ActionFactory<Entity>.Instance action) {
        this.action = action;
    }

    public ActionFactory<Entity>.Instance getAction() {
        return action;
    }

    public int getTimesOnGround() {
        return timesOnGround;
    }
    public void incrementTimesOnGround() {
        timesOnGround++;
    }
}
