package com.mr208.multifarmcrops;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import forestry.core.proxy.Proxies;
import forestry.farming.logic.Crop;
import com.infinityraider.agricraft.tiles.TileEntityCrop;

public class CropMFCAgricraftCrop extends Crop {

	private TileEntity tileEntityCrop;
	private static Random random;


	protected CropMFCAgricraftCrop(@Nonnull World world, @Nonnull BlockPos position) {
		super(world, position);
		tileEntityCrop = world.getTileEntity(position);
		random = new Random();
	}

	@Override
	protected boolean isCrop(World world, BlockPos blockPos) {
		return canHarvestCrop(this.tileEntityCrop);
	}

	@Override
	protected Collection<ItemStack> harvestBlock(World world, BlockPos blockPos) {


		return getCropDrops(this.tileEntityCrop);

	}

	public static boolean isAgricraftCrop(TileEntity tileEntity) {
		return tileEntity instanceof TileEntityCrop;
	}

	public static boolean canHarvestCrop(TileEntity tileEntity) {
		if(isAgricraftCrop(tileEntity)) {
			TileEntityCrop crop = (TileEntityCrop) tileEntity;

			if(crop.isMature() && crop.isFertile()) {
				return true;
			}
		}
		return false;
	}

	private static List<ItemStack> getCropDrops(TileEntity tileEntity) {
		if(isAgricraftCrop(tileEntity)) {
			TileEntityCrop crop = (TileEntityCrop) tileEntity;
			List<ItemStack> harvestResults;
			harvestResults = new ArrayList<>();
			crop.getFruits((harvestResults::add),random);
			List<ItemStack> filteredList = new ArrayList<>();
			for(ItemStack stack: harvestResults) {
				if(stack!=null) filteredList.add(stack);
			}
			Proxies.common.addBlockDestroyEffects(tileEntity.getWorld(),tileEntity.getPos(),((TileEntityCrop) tileEntity).getState());
			crop.setGrowthStage(0);
			return filteredList.isEmpty()? null: filteredList;
		}
		return null;
	}

}
