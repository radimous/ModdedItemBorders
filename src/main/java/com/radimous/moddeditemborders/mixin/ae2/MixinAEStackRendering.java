package com.radimous.moddeditemborders.mixin.ae2;

import appeng.api.client.AEStackRendering;
import appeng.api.stacks.AEKey;
import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.moddeditemborders.ModdedItemBorders;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(
    require = {
        @Condition(type = Condition.Type.MOD, value = "itemborders"),
        @Condition(type = Condition.Type.MOD, value = "ae2")
    }
)
@Mixin(value = AEStackRendering.class)
public abstract class MixinAEStackRendering {

    @Inject(method="drawInGui", at= @At(value = "TAIL"), remap = false)
    private static void renderSlot(Minecraft minecraft, PoseStack poseStack, int x, int y, int z, AEKey what, CallbackInfo ci) {
        ModdedItemBorders.render(poseStack, what.wrapForDisplayOrFilter(), x, y);
    }
}
