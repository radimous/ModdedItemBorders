package com.radimous.moddeditemborders.mixin;

import com.anthonyhilyard.itemborders.ItemBorders;
import com.mojang.blaze3d.vertex.PoseStack;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Restriction(
    require = @Condition(type = Condition.Type.MOD, value = "itemborders")
)
@Mixin(value = ItemBorders.class, remap = false)
public interface ItemBordersInvoker {
    @Invoker("render")
    static void invokeRender(PoseStack poseStack, ItemStack item, int x, int y) {}
}
