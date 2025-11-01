package xyz.blueben.potentia.action.entity;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.action.meta.SideAction.Side;
import io.github.apace100.apoli.util.Space;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;
import xyz.blueben.potentia.LandingAction;
import xyz.blueben.potentia.Potentia;
import xyz.blueben.potentia.SideUtility;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class CrashDownEntityAction {
    protected float x;
    protected float y;
    protected float z;
    protected Space space;
    protected  boolean client;
    protected boolean server;
    protected boolean set;
    protected ActionFactory<Entity>.Instance action;

    public CrashDownEntityAction(Entity entity, float x, float y, float z, Space space, boolean client, boolean server, boolean set, ActionFactory<Entity>.Instance action) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.space = space;
        this.client = client;
        this.server = server;
        this.set = set;
        this.action = action;

        if (entity instanceof PlayerEntity
                && (entity.getWorld().isClient ?
                !this.client : !this.server))
            return;
        Vector3f vec = new Vector3f(this.x, this.y, this.z);
        TriConsumer<Float, Float, Float> method = entity::addVelocity;
        if(this.set) {
            method = entity::setVelocity;
        }
        this.space.toGlobal(vec, entity);
        method.accept(vec.x, vec.y, vec.z);
        entity.velocityModified = true;

        UUID entityUuid = entity.getUuid();

        Side side = SideUtility.from(entity);

        List<LandingAction> array = Potentia.onLandingEntityActions.get(side).getOrDefault(entityUuid, new ArrayList<>());
        array.add(new LandingAction(action, client, server));
        Potentia.onLandingEntityActions.get(side).put(entityUuid, array);
    }

    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(new Identifier("potentia","crash_down"),
                new SerializableData()
                        .add("x", SerializableDataTypes.FLOAT, 0F)
                        .add("y", SerializableDataTypes.FLOAT, 0F)
                        .add("z", SerializableDataTypes.FLOAT, 0F)
                        .add("space", ApoliDataTypes.SPACE, Space.WORLD)
                        .add("client", SerializableDataTypes.BOOLEAN, true)
                        .add("server", SerializableDataTypes.BOOLEAN, true)
                        .add("set", SerializableDataTypes.BOOLEAN, false)
                        .add("action", ApoliDataTypes.ENTITY_ACTION, null),
                (data, entity) -> {
                    float x = data.get("x");
                    float y = data.get("y");
                    float z = data.get("z");
                    Space space = data.get("space");
                    boolean client = data.get("client");
                    boolean server = data.get("server");
                    boolean set = data.get("set");
                    ActionFactory<Entity>.Instance action = data.get("action");

                    new CrashDownEntityAction(entity, x, y, z, space, client, server, set, action);
                }
                );
    }
}

