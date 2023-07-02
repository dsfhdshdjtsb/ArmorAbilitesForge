package com.dsfhdshdjtsb.ArmorAbilities.init;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.Enchantments.AnvilStompEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ArmorAbilities.MODID);

    public static final RegistryObject<Enchantment> BRIDGE = ENCHANTMENTS.register("bridge", AnvilStompEnchantment::new);
}
