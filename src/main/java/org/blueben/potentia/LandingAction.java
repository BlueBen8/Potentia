package org.blueben.potentia;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import net.minecraft.entity.Entity;

public class LandingAction {
    protected ActionFactory<Entity>.Instance action;
    protected boolean runOnClient;
    protected boolean runOnServer;
    public LandingAction(ActionFactory<Entity>.Instance action, boolean runOnClient, boolean runOnServer) {
        this.action = action;
        this.runOnClient = runOnClient;
        this.runOnServer = runOnServer;
    }

    public ActionFactory<Entity>.Instance getAction() {
        return action;
    }

    public boolean isRunOnClient() {
        return runOnClient;
    }

    public boolean isRunOnServer() {
        return runOnServer;
    }
}
