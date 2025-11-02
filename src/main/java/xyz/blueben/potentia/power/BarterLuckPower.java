package xyz.blueben.potentia.power;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

record BarterLuckData(Identifier id, int weight) {
    public static final SerializableDataType<BarterLuckData> TYPE = new SerializableDataType<>(
            BarterLuckData.class,
            (buf, barterLuckData) -> {
                buf.writeIdentifier(barterLuckData.id);
                buf.writeInt(barterLuckData.weight);
            },
            (buf) -> new BarterLuckData(buf.readIdentifier(), buf.readInt()),
            (json) -> {
                if (json.isJsonObject()) {
                    JsonObject jsonObject = json.getAsJsonObject();
                    return new BarterLuckData(new Identifier(jsonObject.get("id").getAsString()), jsonObject.get("weight").getAsInt());
                }
                throw new JsonParseException("Expected an object with id and weight fields, found none");
            }
            );
}

public class BarterLuckPower extends Power {
    protected Map<Identifier, Integer> weightTable;
    public BarterLuckPower(PowerType<?> type, LivingEntity entity, Map<Identifier, Integer> weightTable) {
        super(type, entity);
        this.weightTable = weightTable;
    }

    public Map<Identifier, Integer> getWeightTable() {
        return weightTable;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier("potentia","barter_luck"),
                new SerializableData()
                        .add("weights", SerializableDataType.list(BarterLuckData.TYPE)),

                data -> {
                    List<BarterLuckData> weightTable = data.get("weights");
                    Map<Identifier, Integer> weightMap = new HashMap<>();
                    for (BarterLuckData barterLuckData : weightTable) {
                        weightMap.put(barterLuckData.id(), barterLuckData.weight());
                    }
                    return (type, player) -> {
                        BarterLuckPower power = new BarterLuckPower(type, player, weightMap);
                        return power;
                    };
                }).allowCondition();
    }

}
