package com.Da_Technomancer.crossroads.gui.container;

import com.Da_Technomancer.crossroads.API.templates.MachineContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class PrototypingTableContainer extends MachineContainer{

	public PrototypingTableContainer(IInventory playerInv, PrototypingTableTileEntity te){
		super(playerInv, te);
	}


	@Override
	protected void addSlots(){
		//Copshowium ID 0
		addSlot(new StrictSlot(te, 0, 134, 78));

		//Template ID 1
		addSlot(new StrictSlot(te, 1, 152, 78));

		//Output ID 2
		addSlot(new OutputSlot(te, 2, 152, 108));

		//Trash ID 3
		addSlot(new StrictSlot(te, 3, 134, 108));
	}

	@Override
	protected int[] getInvStart(){
		return new int[] {8, 132};
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player){
		ItemStack out = super.slotClick(slotId, dragType, clickTypeIn, player);
		detectAndSendChanges();
		return out;
	}
}
