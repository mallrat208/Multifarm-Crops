package com.mr208.multifarmcrops;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = MultifarmCrops.MOD_ID, name = MultifarmCrops.MOD_NAME, version = MultifarmCrops.MOD_VERSION, dependencies = MultifarmCrops.MOD_DEPENDENCIES)
public class MultifarmCrops {

	public static final String MOD_ID = "multifarmcrops";
	public static final String MOD_NAME = "Multifarm Crops";
	public static final String MOD_VERSION = "1.1.2";
	public static final String MOD_DEPENDENCIES = "required-after:Forge@[12.18.3.2185,);required-after:forestry;required-after:agricraft@[0-0.10.0-a20,)";

	public static final Logger logger = LogManager.getLogger(MOD_ID);


}
