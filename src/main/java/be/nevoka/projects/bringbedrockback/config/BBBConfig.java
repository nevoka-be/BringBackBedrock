package be.nevoka.projects.bringbedrockback.config;

import be.nevoka.projects.bringbedrockback.BringBedrockBack;

import be.nevoka.projects.bringbedrockback.proxy.CommonProxy;
import net.minecraft.world.DimensionType;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Level;

@Config(modid = BringBedrockBack.MOD_ID)
@Mod.EventBusSubscriber(modid = BringBedrockBack.MOD_ID)
public class BBBConfig {
    private static final String CATEGORY_GENERAL = "general";
    @Config.Comment("Enable log output to console")
    public static boolean enableLogOutput= false;
    @Config.Comment("Enable bedrock generation")
    public static boolean enableRegeneration = true;
    @Config.Comment("Y-level roofed bedrock. -1 to disable")
    public static int roofedBedrockLevel = 127;
    @Config.Comment("Y-level floored bedrock. -1 to disable")
    public static int flooredBedrockLevel = 0;
    @Config.Comment("List of dimensions the roof needs to be generated/regenerated")
    public static int[] roofedDimensions = new int[] {
            DimensionType.NETHER.getId()
    };
    @Config.Comment("List of dimensions the floor needs to be generated/regenerated")
    public static int[] flooredDimensions = new int[] {
            DimensionType.OVERWORLD.getId(),
            DimensionType.NETHER.getId()
    };

    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            BringBedrockBack.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        enableLogOutput = cfg.getBoolean("enableLogOutput", CATEGORY_GENERAL, enableRegeneration, "Enable log output to console");
        enableRegeneration = cfg.getBoolean("enableRegeneration", CATEGORY_GENERAL, enableRegeneration, "Enable regeneration of bedrock layer");
        roofedBedrockLevel = cfg.getInt("roofedBedrockLevel", CATEGORY_GENERAL, roofedBedrockLevel, -1, 256, "Y-level of the roofed bedrock layer. -1 to disable");
        flooredBedrockLevel = cfg.getInt("flooredBedrockLevel", CATEGORY_GENERAL, flooredBedrockLevel, -1, 256, "Y-level of the floored bedrock layer. -1 to disabel");
        flooredDimensions = cfg.get("regenerateFlooredDimensions", CATEGORY_GENERAL, flooredDimensions, "List of dimensions the floor bedrock needs to be generated/regenerated").getIntList();
        roofedDimensions = cfg.get("regenerateRoofedDimensions", CATEGORY_GENERAL, roofedDimensions, "List of dimensions the roof bedrock needs to be generated/regenerated").getIntList();
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(BringBedrockBack.MOD_ID)) {
            ConfigManager.sync(BringBedrockBack.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
