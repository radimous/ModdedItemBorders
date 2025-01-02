package com.radimous.moddeditemborders.mixin.sophisticatedcore;

import com.mojang.blaze3d.vertex.PoseStack;
import com.radimous.moddeditemborders.ModdedItemBorders;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.InventoryScrollPanel;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Restriction(
    require = {
        @Condition(type = Condition.Type.MOD, value = "itemborders"),
        @Condition(type = Condition.Type.MOD, value = "sophisticatedcore")
    }
)
@Mixin(value = StorageScreenBase.class, remap = false)
public abstract class MixinStorageScreenBase<S extends StorageContainerMenuBase<?>> extends AbstractContainerScreen<S> implements InventoryScrollPanel.IInventoryScreen  {

    public MixinStorageScreenBase(S pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Inject(method = "renderSlot(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/inventory/Slot;)V", at = @At("HEAD"), remap = true)
    protected void renderSlot(PoseStack matrixStack, Slot slot, CallbackInfo ci) {
        ModdedItemBorders.renderBorder(matrixStack, slot);
    }
    // Backport https://github.com/P3pp3rF1y/SophisticatedCore/commit/353b357ef9d01f44c6268223ccbcbad587fa02b4
    @Redirect(method = "renderStorageInventorySlots(Lcom/mojang/blaze3d/vertex/PoseStack;IIZ)V", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    protected int renderStorageInventorySlots(List<Slot> instance) {
        return this.menu.getNumberOfStorageInventorySlots();
    }
}
