package com.radimous.moddeditemborders.mixin.the_vault;

import com.anthonyhilyard.itemborders.ItemBordersConfig;
import com.radimous.moddeditemborders.Config;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.gear.VaultGearState;
import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.GearDataCache;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.tool.JewelItem;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(
    require = {
        @Condition(type = Condition.Type.MOD, value = "itemborders"),
        @Condition(type = Condition.Type.MOD, value = "the_vault")
    }
)
@Mixin(value = ItemBordersConfig.class, remap = false)
public class MixinItemBordersConfig {

    // Speed up rarity color calculation by using cache instead of vgd needed for tooltips
    // Add support for trinket and charm colors
    @Inject(method = "getBorderColorForItem", at = @At("HEAD"), cancellable = true)
    private void getBorderColorForItem(ItemStack itemStack, CallbackInfoReturnable<TextColor> cir) {
        Item item = itemStack.getItem();
        if (item instanceof VaultGearItem) {
            if (Config.jewelColorInsteadOfRarity.get() && item instanceof JewelItem){
                cir.setReturnValue(TextColor.fromRgb(JewelItem.getColor(itemStack)));
                return;
            }
            GearDataCache cache = GearDataCache.of(itemStack);
            if (cache.getState() == VaultGearState.UNIDENTIFIED){
                ModConfigs.VAULT_GEAR_TYPE_CONFIG.getRollPool(cache.getGearRollType()).ifPresent(pool -> {
                    cir.setReturnValue(TextColor.fromRgb(pool.getColor()));
                });
                return;
            }
            VaultGearRarity rarity = cache.getRarity();
            if (rarity == null){
                return;
            }
            cir.setReturnValue(rarity.getColor());
            return;
        }
        if (item == ModItems.TRINKET) {
            // TrinketItem has utility methods for this, but that would require 2 data reads instead of 1
            AttributeGearData data = AttributeGearData.read(itemStack);
            VaultGearState state = data.getFirstValue(ModGearAttributes.STATE).orElse(VaultGearState.UNIDENTIFIED);

            if (state == VaultGearState.UNIDENTIFIED) {
                cir.setReturnValue(TextColor.fromRgb(16004495));
                return;
            }
            data.getFirstValue(ModGearAttributes.TRINKET_EFFECT).ifPresent(effect ->
                cir.setReturnValue(effect.getTrinketConfig().getComponentColor())
            );
        }
        if (item instanceof CharmItem charmItem) {
            cir.setReturnValue(TextColor.fromRgb(charmItem.getColor()));
        }
    }
}
