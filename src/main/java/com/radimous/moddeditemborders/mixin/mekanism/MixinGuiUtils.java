package com.radimous.moddeditemborders.mixin.mekanism;


import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.moddeditemborders.ModdedItemBorders;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import mekanism.client.gui.GuiUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(
    require = {
        @Condition(type = Condition.Type.MOD, value = "itemborders"),
        @Condition(type = Condition.Type.MOD, value = "mekanism")
    }
)
@Mixin(value = GuiUtils.class, remap = false)
public class MixinGuiUtils {
    @Inject(method = "renderItem", at = @At(value = "HEAD", remap = true))
    private static void renderItemBorder(PoseStack matrix, ItemRenderer renderer, ItemStack stack, int xAxis, int yAxis, float scale, Font font, String text, boolean overlay, CallbackInfo ci) {
        ModdedItemBorders.render(matrix, stack, xAxis, yAxis);
    }
}
