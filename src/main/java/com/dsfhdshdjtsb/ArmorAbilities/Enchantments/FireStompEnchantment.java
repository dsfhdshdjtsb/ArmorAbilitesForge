package com.dsfhdshdjtsb.ArmorAbilities.Enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class FireStompEnchantment extends Enchantment {
    public FireStompEnchantment() {
        super(Rarity.COMMON, EnchantmentCategory.ARMOR_FEET, EquipmentSlot.values());
    }
    @Override
    public int getMaxLevel() {
        return 5;
    }
    @Override
    public int getMinCost(int pLevel) {
        return 1 + (pLevel - 1) * 10;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return this.getMinCost(pLevel) + 15;
    }
    @Override
    protected boolean checkCompatibility(@NotNull Enchantment other) {
        return super.checkCompatibility(other) && !(other instanceof FrostStompEnchantment || other instanceof AnvilStompEnchantment);
    }
}
