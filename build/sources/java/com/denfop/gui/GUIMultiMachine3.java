package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.api.inv.IInvSlotProcessableMulti;
import com.denfop.container.ContainerMultiMachine;
import com.denfop.tiles.base.TileEntityMultiMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GUIMultiMachine3 extends GUIIC2 {
    public final ContainerMultiMachine container;

    public GUIMultiMachine3(ContainerMultiMachine container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        TileEntityMultiMachine tile = (TileEntityMultiMachine) this.container.base;

        int chargeLevel = (int) (14.0F * tile.getChargeLevel2());
        int chargeLevel1 = (int) (14.0F * tile.getChargeLevel1());

        int i = 0;
        for (Slot slot : (List<Slot>) this.container.inventorySlots) {
            if (slot instanceof SlotInvSlot) {
                int xX = this.xoffset + slot.xDisplayPosition;
                int yY = this.yoffset + slot.yDisplayPosition;
                SlotInvSlot slotInv = (SlotInvSlot) slot;
                if (slotInv.invSlot instanceof IInvSlotProcessableMulti) {
                    int down = 0;
                    if (tile.blockMetadata < 4)
                        down = 0;
                    if (tile.blockMetadata >= 4 && tile.blockMetadata < 8)
                        down = 24;

                    drawTexturedModalRect(xX, yY + 19, 176, 14 + down, 16, 24);
                    int progress = (int) (24.0F * tile.getProgress(i));
                    if (progress >= 0)
                        if (down == 24)
                            drawTexturedModalRect(xX - 1, yY + 19, 192, 14 + down, 16, progress + 1);
                    if (down == 0)
                        drawTexturedModalRect(xX - 2, yY + 19, 192, 14 + down, 16, progress + 1);

                    i++;


                }
                drawTexturedModalRect(xX - 1, yY - 1, 238, 0, 18, 18);
            }
        }


        if (chargeLevel >= 0)
            drawTexturedModalRect(this.xoffset + 5, this.yoffset + 47 + 14 - chargeLevel, 176, 14 - chargeLevel, 14,
                    chargeLevel);
        if (chargeLevel1 >= 0)
            drawTexturedModalRect(this.xoffset + 14, this.yoffset + 47 + 14 - chargeLevel1, 176 + 14, 14 - chargeLevel1, 14,
                    chargeLevel1);
    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        TileEntityMultiMachine tile = (TileEntityMultiMachine) this.container.base;
        String type = "";
        if (tile.progress.length == 2)
            type = "_adv";
        if (tile.progress.length == 3)
            type = "_imp";
        if (tile.progress.length == 4)
            type = "_per";
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMachine3" + type + ".png");

    }
}
