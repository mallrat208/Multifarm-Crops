package com.mr208.multifarmcrops;

import javax.annotation.Nonnull;

import java.util.Collection;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import forestry.core.proxy.Proxies;
import forestry.farming.logic.Crop;
import com.infinityraider.agricraft.tiles.TileEntityCrop;


public class CropMFCAgricraftCrop extends Crop {

	private TileEntity tileEntityCrop;


	protected CropMFCAgricraftCrop(@Nonnull World world, @Nonnull BlockPos position) {
		super(world, position);
		tileEntityCrop = world.getTileEntity(position);
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
		if(tileEntity instanceof TileEntityCrop) {
			return true;
		}
		return false;
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
			Block block = crop.getBlockType();

			List<ItemStack> harvestResults = block.getDrops(tileEntity.getWorld(),tileEntity.getPos(),((TileEntityCrop) tileEntity).getState(), 0);

			if(harvestResults.size() > 1) {
				harvestResults.remove(1);
			}
			harvestResults.remove(0);


			Proxies.common.addBlockDestroyEffects(tileEntity.getWorld(),tileEntity.getPos(),((TileEntityCrop) tileEntity).getState());

			crop.setGrowthStage(0);

			//Check for Lists with null itemstacks. I hates them.
			if(harvestResults.get(0) == null) {
				return null;
			}

			return harvestResults;
		}
		return null;
	}

}
