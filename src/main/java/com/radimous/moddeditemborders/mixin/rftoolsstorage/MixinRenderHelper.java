package com.radimous.moddeditemborders.mixin.rftoolsstorage;

import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.moddeditemborders.ModdedItemBorders;
import mcjty.lib.client.RenderHelper;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(
    require = {
        @Condition(type = Condition.Type.MOD, value = "itemborders"),
        @Condition(type = Condition.Type.MOD, value = "rftoolsstorage")
    }
)

@Mixin(value = RenderHelper.class, remap = false)
public class MixinRenderHelper {
    @Inject(method = "renderItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderAndDecorateItem(Lnet/minecraft/world/item/ItemStack;II)V", remap = true))
    private static void renderBorders(PoseStack matrixStack, ItemRenderer itemRender, ItemStack itm, int x, int y,String txt, boolean highlight, CallbackInfoReturnable<Boolean> cir) {
        ModdedItemBorders.render(matrixStack, itm, x, y);
    }
}
