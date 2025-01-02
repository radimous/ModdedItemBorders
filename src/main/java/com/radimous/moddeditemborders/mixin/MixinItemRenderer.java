package com.radimous.moddeditemborders.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.moddeditemborders.Config;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Restriction(
    require = @Condition(type = Condition.Type.MOD, value = "itemborders")
)
@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Inject(method = "renderGuiItem(Lnet/minecraft/world/item/ItemStack;IILnet/minecraft/client/resources/model/BakedModel;)V", at = @At("HEAD"))
    private void renderGuiItem(ItemStack itemStack, int x, int y, BakedModel bakedModel, CallbackInfo ci) {
        if (Config.itemBordersEverywhere.get()){
            ItemBordersInvoker.invokeRender(new PoseStack(), itemStack, x, y);
        }
    }
}
