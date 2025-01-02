package com.radimous.moddeditemborders.mixin;

import com.anthonyhilyard.itemborders.ItemBorders;
import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.moddeditemborders.Config;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(
    require = @Condition(type = Condition.Type.MOD, value = "itemborders")
)
@Mixin(value = ItemBorders.class, remap = false)
public class MixinItemBorders {
    @Inject(method = "renderBorder(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/inventory/Slot;)V", at = @At("HEAD"), cancellable = true)
    private static void cancelBorder(PoseStack poseStack, Slot slot, CallbackInfo ci){
        if (Config.itemBordersEverywhere.get()) { // do not render it twice (I'll invoke the underlying method in MixinItemRenderer)
            ci.cancel();
        }
    }

    @Inject(method = "renderBorder(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemStack;II)V", at = @At("HEAD"), cancellable = true)
    private static void cancelBorder(PoseStack poseStack, net.minecraft.world.item.ItemStack itemStack, int x, int y, CallbackInfo ci){
        if (Config.itemBordersEverywhere.get()) { // do not render it twice (I'll invoke the underlying method in MixinItemRenderer)
            ci.cancel();
        }
    }

}
