package com.Da_Technomancer.crossroads.items;

import com.Da_Technomancer.crossroads.API.heat.HeatInsulators;
import com.Da_Technomancer.crossroads.API.rotary.GearTypes;
import com.Da_Technomancer.crossroads.Main;
import com.Da_Technomancer.crossroads.items.alchemy.*;
import com.Da_Technomancer.crossroads.items.itemSets.GearFactory;
import com.Da_Technomancer.crossroads.items.itemSets.HeatCableFactory;
import com.Da_Technomancer.crossroads.items.technomancy.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public final class ModItems{

	public static final CreativeTabs TAB_CROSSROADS = new CreativeTabs(Main.MODID){
		@Override
		public ItemStack getTabIconItem(){
			return new ItemStack(omnimeter, 1);
		}
	};

	public static final CreativeTabs TAB_HEAT_CABLE = new CreativeTabs("heatCable"){
		@Override
		public ItemStack getTabIconItem(){
			return new ItemStack(HeatCableFactory.HEAT_CABLES.get(HeatInsulators.WOOL), 1);
		}
	};

	public static final CreativeTabs TAG_GEAR = new CreativeTabs("gear"){
		@Override
		public ItemStack getTabIconItem(){
			return new ItemStack(GearFactory.BASIC_GEARS.get(GearTypes.BRONZE));
		}
	};

	public static final ArmorMaterial BOBO = EnumHelper.addArmorMaterial("BOBO", Main.MODID + ":bobo", 100, new int[4], 0, SoundEvents.ENTITY_HORSE_DEATH, 0F).setRepairItem(new ItemStack(Items.POISONOUS_POTATO));
	public static final ArmorMaterial TECHNOMANCY = EnumHelper.addArmorMaterial("TECHNOMANCY", "chain", 0, new int[4], 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);

	public static DebugGearWriter debugGearWriter;
	public static DebugHeatWriter debugHeatWriter;
	public static BasicItem dustSalt;
	public static MashedPotato mashedPotato;
	public static HandCrank handCrank;
	public static BasicItem dustCopper;
	@Deprecated
	public static Thermometer thermometer;
	@Deprecated
	public static FluidGauge fluidGauge;
	@Deprecated
	public static Speedometer speedometer;
	public static OmniMeter omnimeter;
	public static Vacuum vacuum;
	public static MagentaBread magentaBread;
	public static EdibleBlob edibleBlob;
	public static RainIdol rainIdol;
	public static BasicItem pureQuartz;
	public static BasicItem luminescentQuartz;
	public static BasicItem lensArray;
	public static BasicItem invisItem;
	public static SquidHelmet squidHelmet;
	public static PigZombieChestsplate pigZombieChestplate;
	public static CowLeggings cowLeggings;
	public static ChickenBoots chickenBoots;
	public static ChaosRod chaosRod;
	public static BasicItem voidCrystal;
	public static ModuleGoggles moduleGoggles;
	public static StaffTechnomancy staffTechnomancy;
	public static BeamCage beamCage;
	public static PrototypePistol pistol;
	public static PrototypeWatch watch;
	public static BasicItem adamant;
	public static BasicItem sulfur;
	public static BasicItem vanadium;
	public static BasicItem vanadiumVOxide;
	public static PhilStone philosopherStone;
	public static BasicItem practitionerStone;
	public static BasicItem alchCrystal;
	public static BasicItem wasteSalt;
	public static Phial phial;
	public static LiechWrench liechWrench;
	public static LeydenJar leydenJar;
	public static FlorenceFlask florenceFlask;
	public static Shell shell;
	public static Nitroglycerin nitroglycerin;
	public static BasicItem solidQuicksilver;
	//public static CustomMaterial customMaterial;
	public static BasicItem solidFusas;
	public static BasicItem solidEldrine;
	public static BasicItem solidStasisol;
	public static BasicItem solidDensus;
	public static BasicItem solidAntiDensus;
	public static BasicItem solidFortis;
	public static BasicItem solidVitriol;
	public static BasicItem solidMuriatic;
	public static BasicItem solidRegia;
	public static FlyingMachine flyingMachine;

	/**
	 * Registers the model location for items. Item: item; Integer: the meta value to register for; ModelResourceLocation: The location to map to. 
	 */
	public static final HashMap<Pair<Item, Integer>, ModelResourceLocation> toClientRegister = new HashMap<Pair<Item, Integer>, ModelResourceLocation>();
	public static final ArrayList<Item> toRegister = new ArrayList<Item>();

	/**
	 * Convenience method to add an Item to the toClientRegister map. 
	 * @param item The item to register the model of
	 * @return The passed item
	 */
	public static <T extends Item> T itemAddQue(T item){
		toClientRegister.put(Pair.of(item, 0), new ModelResourceLocation(item.getRegistryName(), "inventory"));
		return item;
	}

	public static void init(){
		debugGearWriter = new DebugGearWriter();
		handCrank = new HandCrank();
		debugHeatWriter = new DebugHeatWriter();
		dustCopper = new BasicItem("dust_copper", "dustCopper");
		dustSalt = new BasicItem("dust_salt", "dustSalt");
		mashedPotato = new MashedPotato();
		thermometer = new Thermometer();
		fluidGauge = new FluidGauge();
		speedometer = new Speedometer();
		omnimeter = new OmniMeter();
		vacuum = new Vacuum();
		magentaBread = new MagentaBread();
		edibleBlob = new EdibleBlob();
		rainIdol = new RainIdol();
		pureQuartz = new BasicItem("pure_quartz", "gemQuartz");
		luminescentQuartz = new BasicItem("luminescent_quartz");
		lensArray = new BasicItem("lens_array");
		invisItem = new BasicItem("invis_item", null, false);
		squidHelmet = new SquidHelmet();
		pigZombieChestplate = new PigZombieChestsplate();
		cowLeggings = new CowLeggings();
		chickenBoots = new ChickenBoots();
		chaosRod = new ChaosRod();
		voidCrystal = new BasicItem("void_crystal");
		moduleGoggles = new ModuleGoggles();
		staffTechnomancy = new StaffTechnomancy();
		beamCage = new BeamCage();
		pistol = new PrototypePistol();
		watch = new PrototypeWatch();
		adamant = new BasicItem("adamant");
		sulfur = new BasicItem("sulfur", "dustSulfur");
		vanadium = new BasicItem("vanadium");
		vanadiumVOxide = new BasicItem("vanadium_5_oxide");
		philosopherStone = new PhilStone();
		practitionerStone = new BasicItem("prac_stone");
		alchCrystal = new BasicItem("alch_crystal", "gemAlcCryst");
		wasteSalt = new BasicItem("waste_salt", "dustSalt");
		phial = new Phial();
		liechWrench = new LiechWrench();
		leydenJar = new LeydenJar();
		florenceFlask = new FlorenceFlask();
		shell = new Shell();
		nitroglycerin = new Nitroglycerin();
		solidQuicksilver = new BasicItem("solid_quicksilver");
//		new CustomTool("pickaxe");
//		new CustomTool("axe");
//		new CustomTool("shovel");
//		new CustomTool("sword");
		//customMaterial = new CustomMaterial();
		solidFusas = new BasicItem("solid_fusas");
		solidEldrine = new BasicItem("solid_eldrine");
		solidStasisol = new BasicItem("solid_stasisol");
		solidDensus = new BasicItem("solid_densus", "gemDensus");
		solidAntiDensus = new BasicItem("solid_anti_densus", "gemAntiDensus");
		solidFortis = new BasicItem("solid_fortis");
		solidVitriol = new BasicItem("solid_vitriol");
		solidMuriatic = new BasicItem("solid_muriatic");
		solidRegia = new BasicItem("solid_regia");
		flyingMachine = new FlyingMachine();
	}

	@SideOnly(Side.CLIENT)
	public static void initModels(){
		for(Entry<Pair<Item, Integer>, ModelResourceLocation> modeling : toClientRegister.entrySet()){
			ModelLoader.setCustomModelResourceLocation(modeling.getKey().getLeft(), modeling.getKey().getRight(), modeling.getValue());
		}
		toClientRegister.clear();
	}

	@SideOnly(Side.CLIENT)
	public static void clientInit(){
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((ItemStack stack, int layer) -> layer == 0 ? AbstractGlassware.getColorRGB(stack) : -1, phial, florenceFlask, shell);
	}
}