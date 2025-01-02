package com.radimous.moddeditemborders.mixin.ssn;

import com.lothrazar.storagenetwork.gui.ItemSlotNetwork;
import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.moddeditemborders.ModdedItemBorders;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(
    require = {
        @Condition(type = Condition.Type.MOD, value = "itemborders"),
        @Condition(type = Condition.Type.MOD, value = "storagenetwork")
    }
)
@Mixin(value = ItemSlotNetwork.class, remap = false)
public abstract class MixinItemSlotNetwork {
    @Shadow public abstract ItemStack getStack();

    @Shadow @Final private int y;

    @Shadow @Final private int x;

    @Inject(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderAndDecorateItem(Lnet/minecraft/world/item/ItemStack;II)V", remap = true))
    public void drawItemBorder(PoseStack poseStack, Font font, int mx, int my, CallbackInfo ci) {
        ModdedItemBorders.render(poseStack, this.getStack(), this.x, this.y);
    }
}
