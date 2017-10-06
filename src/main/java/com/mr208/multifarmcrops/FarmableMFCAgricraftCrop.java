package com.mr208.multifarmcrops;

import javax.annotation.Nullable;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmable;
import forestry.farming.logic.CropBasicAgriCraft;

import com.infinityraider.agricraft.tiles.TileEntityCrop;

public class FarmableMFCAgricraftCrop implements IFarmable {
	@GameRegistry.ObjectHolder("agricraft:crop")
	public static final Block cropBlock = null;

	public FarmableMFCAgricraftCrop() {}

	@Override
	public boolean isSaplingAt(World world, BlockPos blockPos) {

		return cropBlock.equals(world.getBlockState(blockPos).getBlock());
	}

	@Nullable
	@Override
	public ICrop getCropAt(World world, BlockPos blockPos, IBlockState iBlockState) {

		TileEntity crop = world.getTileEntity(blockPos);
		if(crop == null) {
			return null;
		} else if(!CropMFCAgricraftCrop.isAgricraftCrop(crop)) {
			return null;
		} else if(!CropMFCAgricraftCrop.canHarvestCrop(crop)) {
			return null;
		} else
			return new CropMFCAgricraftCrop(world,blockPos);
	}

	@Override
	public boolean isGermling(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean isWindfall(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean plantSaplingAt(EntityPlayer entityPlayer, ItemStack itemStack, World world, BlockPos blockPos) {
		return false;
	}
}
