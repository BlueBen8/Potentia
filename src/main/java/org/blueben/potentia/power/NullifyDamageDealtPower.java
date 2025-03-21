package org.blueben.potentia.power;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

// This power type causes attacking to deal no damage, with configurable chance and sound effect options.

public class NullifyDamageDealtPower extends ActiveCooldownPower {
    public NullifyDamageDealtPower(PowerType<?> type, LivingEntity entity, SoundEvent sound) {
        super(type, entity, 0, HudRender.DONT_RENDER, e -> {
            if (!e.getWorld().isClient && e instanceof PlayerEntity p) {
                p.getWorld().playSound(null, p.getX(), p.getY(), p.getZ(), sound, SoundCategory.NEUTRAL, 0.5F, 0.4F / (p.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }
        );

    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier("potentia","launch"),
                new SerializableData()
                        .add("sound", SerializableDataTypes.SOUND_EVENT, null),
                data -> {
                    SoundEvent soundEvent = data.get("sound");
                    return (type, player) -> {
                        NullifyDamageDealtPower power = new NullifyDamageDealtPower(type, player, soundEvent);
                        return power;
                    };
                }).allowCondition();
    }
}
