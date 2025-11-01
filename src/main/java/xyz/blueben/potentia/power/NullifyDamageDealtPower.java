package xyz.blueben.potentia.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.Random;

// This power type causes attacking to deal no damage, with configurable chance and sound effect options.

public class NullifyDamageDealtPower extends Power {
    protected SoundEvent sound;
    public NullifyDamageDealtPower(PowerType<?> type, LivingEntity entity, SoundEvent sound) {
        super(type, entity);
        this.sound = sound;

    }
    public void onUse() {
        if (!entity.getWorld().isClient && entity instanceof PlayerEntity p && sound != null) {
            p.getWorld().playSound(null, p.getX(), p.getY(), p.getZ(), sound, SoundCategory.NEUTRAL, 0.5F, 0.4F / (p.getRandom().nextFloat() * 0.4F + 0.8F));
        }
    }


    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier("potentia","nullify_damage_dealt"),
                new SerializableData().add("chance", SerializableDataTypes.FLOAT, 1.0f)
                        .add("sound", SerializableDataTypes.SOUND_EVENT, null),
                data -> {
                    SoundEvent soundEvent = data.get("sound");
                    return (type, player) -> {
                        NullifyDamageDealtPower power = new NullifyDamageDealtPower(type, player, soundEvent);
                        power.addCondition( e -> new Random().nextFloat() < data.getFloat("chance")
                                );
                        return power;
                    };
                }).allowCondition();
    }
}
