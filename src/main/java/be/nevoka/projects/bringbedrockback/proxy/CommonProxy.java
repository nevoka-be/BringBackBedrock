package be.nevoka.projects.bringbedrockback.proxy;

import be.nevoka.projects.bringbedrockback.config.BBBConfig;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "BringBackBedrock.cfg"));
        BBBConfig.readConfig();
    }
}
