package com.mr208.multifarmcrops;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod(modid = MultifarmCrops.MOD_ID, name = MultifarmCrops.MOD_NAME, version = MultifarmCrops.MOD_VERSION, dependencies = MultifarmCrops.MOD_DEPENDENCIES)
public class MultifarmCrops {

	public static final String MOD_ID = "multifarmcrops";
	public static final String MOD_NAME = "Multifarm Crops";
	public static final String MOD_VERSION = "1.0.1";
	public static final String MOD_DEPENDENCIES = "required-after:forestry;required-after:agricraft";

	public static String[] traversableBlocks;

	public static List<Block> traversableBlock;

	public static final Logger logger = LogManager.getLogger(MOD_ID);
	public static Configuration config;

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		if(config == null) {
			config = new Configuration(event.getSuggestedConfigurationFile());
			traversableBlocks = config.getStringList("Traversable Blocks",
					"Agricraft",
					new String[] {
							"farmland",
							"dirt",
							"gravel",
							"sand",
							"gravel",
							"mycelium"},
					"Add in Soil blocks that require resources below them. Metadata is currently not parsed");

			if(config.hasChanged()) config.save();
		}
	}

	protected static void parseTraversableBlocks() {
		traversableBlock = new ArrayList<>();

		for(String block: traversableBlocks) {
			String[] blockname = block.split(":");

			if(blockname.length == 1) {
				Block tempBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft",blockname[0]));
				if(tempBlock != null) {
					logger.info("Adding Traversable Block: <" + blockname[0] +">");
					traversableBlock.add(tempBlock);
				}
			} else if (blockname.length==2) {
				Block tempBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockname[0],blockname[1]));
				if(tempBlock != null) {
					logger.info("Adding Traversable Block: <" + blockname[0] + ":" + blockname[1] + ">");
					traversableBlock.add(tempBlock);
				}
			} else if (blockname.length==3) {
				Block tempBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockname[0],blockname[1]));
				if(tempBlock != null) {
					logger.info("Adding Traversable Block: <" + blockname[0] + ":" + blockname[1] + "> Skipping metadata of :" + blockname[2]);
					traversableBlock.add(tempBlock);
				}
			}
		}

	}

}
