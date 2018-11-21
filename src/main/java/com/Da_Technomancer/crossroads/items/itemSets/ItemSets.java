package com.Da_Technomancer.crossroads.items.itemSets;

public class ItemSets{

	public static void init(){
		HeatCableFactory.init();
		GearFactory.init();
		OreSetup.init();
	}

	public static void clientInit(){
		GearFactory.clientInit();
	}

	public static void modelInit(){
		HeatCableFactory.clientInit();
	}

	public static void craftingInit(){
		OreSetup.initCrafting();
		GearFactory.craftingInit();
	}
}
