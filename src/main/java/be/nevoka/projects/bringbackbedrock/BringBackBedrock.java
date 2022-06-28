package be.nevoka.projects.bringbackbedrock;

import be.nevoka.projects.bringbackbedrock.config.BBBConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Mod("bringbackbedrock")
public class BringBackBedrock {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "bringbackbedrock";

    public BringBackBedrock() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, BBBConfig.Common.common_config);
    }
}