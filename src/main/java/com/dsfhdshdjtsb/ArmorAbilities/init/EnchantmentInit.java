package com.dsfhdshdjtsb.ArmorAbilities.init;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.Enchantments.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ArmorAbilities.MODID);

    public static final RegistryObject<Enchantment> FOCUS = ENCHANTMENTS.register("focus", FocusEnchantment::new);
    public static final RegistryObject<Enchantment> MIND_CONTROL = ENCHANTMENTS.register("mind_control", MindControlEnchantment::new);
    public static final RegistryObject<Enchantment> TELEKINESIS = ENCHANTMENTS.register("telekinesis", TelekinesisEnchantment::new);

    public static final RegistryObject<Enchantment> CLEANSE = ENCHANTMENTS.register("cleanse", CleanseEnchantment::new);
    public static final RegistryObject<Enchantment> EXPLODE = ENCHANTMENTS.register("explode", ExplodeEnchantment::new);
    public static final RegistryObject<Enchantment> SIPHON = ENCHANTMENTS.register("siphon", SiphonEnchantment::new);

    public static final RegistryObject<Enchantment> DASH = ENCHANTMENTS.register("dash", DashEnchantment::new);
    public static final RegistryObject<Enchantment> BLINK = ENCHANTMENTS.register("blink", BlinkEnchantment::new);
    public static final RegistryObject<Enchantment> RUSH = ENCHANTMENTS.register("rush", RushEnchantment::new);

    public static final RegistryObject<Enchantment> FIRE_STOMP = ENCHANTMENTS.register("fire_stomp", FireStompEnchantment::new);
    public static final RegistryObject<Enchantment> FROST_STOMP = ENCHANTMENTS.register("frost_stomp", FrostStompEnchantment::new);
    public static final RegistryObject<Enchantment> ANVIL_STOMP = ENCHANTMENTS.register("anvil_stomp", AnvilStompEnchantment::new);
}
