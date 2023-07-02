package com.dsfhdshdjtsb.ArmorAbilities.Enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AnvilStompEnchantment extends Enchantment {
    public AnvilStompEnchantment() {
        super(Rarity.COMMON, EnchantmentCategory.ARMOR_FEET, EquipmentSlot.values());
    }
}
