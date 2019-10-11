package com.Da_Technomancer.crossroads.API;

import com.Da_Technomancer.crossroads.tileentities.technomancy.MathAxisTileEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;

public class CrossroadsProperties{

	public static final UnlistedPropertyBooleanSixArray CONNECT = new UnlistedPropertyBooleanSixArray("connect");
	public static final UnlistedPropertyIntegerSixArray CONNECT_MODE = new UnlistedPropertyIntegerSixArray("connect_mode");
	public static final UnlistedPropertyIntegerSixArray PORT_TYPE = new UnlistedPropertyIntegerSixArray("port_type");
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
	public static final IntegerProperty FULLNESS = IntegerProperty.create("fullness", 0, 3);
	public static final EnumProperty<Direction.Axis> HORIZ_AXIS = EnumProperty.create("horiz_axis", Direction.Axis.class, (Direction.Axis axis) -> axis != null && axis.isHorizontal());

	/**
	 * 0: copper 1: iron 2: quartz 3: diamond
	 */
//	public static final IntegerProperty TEXTURE_4 = IntegerProperty.create("text", 0, 3);
	public static final BooleanProperty CRYSTAL = BooleanProperty.create("crystal");
	public static final DirectionProperty HORIZ_FACING = DirectionProperty.create("horiz_facing", (Direction side) -> side != null && side.getAxis() != Direction.Axis.Y);
	public static final BooleanProperty CONTAINER_TYPE = BooleanProperty.create("container_type");
	public static final EnumProperty<MathAxisTileEntity.Arrangement> ARRANGEMENT = EnumProperty.create("arrangement", MathAxisTileEntity.Arrangement.class);
}