package com.Da_Technomancer.crossroads.gui.container;

import com.Da_Technomancer.crossroads.API.templates.MachineContainer;
import com.Da_Technomancer.crossroads.Crossroads;
import com.Da_Technomancer.crossroads.tileentities.fluid.FluidTankTileEntity;
import com.Da_Technomancer.essentials.gui.container.FluidSlotManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.commons.lang3.tuple.Pair;

@ObjectHolder(Crossroads.MODID)
public class FluidTankContainer extends MachineContainer<FluidTankTileEntity>{

	@ObjectHolder("fluid_tank")
	private static ContainerType<FluidTankContainer> type = null;

	public FluidTankContainer(int id, PlayerInventory playerInv, PacketBuffer buf){
		super(type, id, playerInv, buf);
	}

	@Override
	protected void addSlots(){
		Pair<Slot, Slot> flSlots = FluidSlotManager.createFluidSlots(new FluidSlotManager.FakeInventory(this), 0, 100,19, 100, 54, te, new int[] {0});
		addSlot(flSlots.getLeft());
		addSlot(flSlots.getRight());
	}

	@Override
	protected int slotCount(){
		return 2;
	}
}
