package com.radimous.moddeditemborders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.moddeditemborders.mixin.ItemBordersInvoker;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("moddeditemborders")
public class ModdedItemBorders {
    
    public ModdedItemBorders() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
    }
    public static void render(PoseStack poseStack, ItemStack itemStack, int x, int y) {
        if (Config.itemBordersEverywhere.get() || !Config.moddedItemBorders.get() || itemStack == null || itemStack.isEmpty()) {
            return;
        }
        if (poseStack == null){
            poseStack = new PoseStack();
        }
        ItemBordersInvoker.invokeRender(poseStack, itemStack, x, y);
    }

    public static void renderBorder(PoseStack poseStack, Slot slot) {
       if (slot != null) {
           render(poseStack, slot.getItem(), slot.x, slot.y);
       }
    }
}
