package com.dsfhdshdjtsb.ArmorAbilities.init;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.Enchantments.FocusEnchantment;
import com.dsfhdshdjtsb.ArmorAbilities.effects.MindControlEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectsInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ArmorAbilities.MODID);

    public static final RegistryObject<MobEffect> MIND_CONTROL_COOLDOWN = MOB_EFFECTS.register("mind_control_cooldown", MindControlEffect::new);
}
