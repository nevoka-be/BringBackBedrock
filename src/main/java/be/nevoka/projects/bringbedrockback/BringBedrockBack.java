package be.nevoka.projects.bringbedrockback;

import be.nevoka.projects.bringbedrockback.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

@Mod(
        modid = BringBedrockBack.MOD_ID,
        name = BringBedrockBack.MOD_NAME,
        version = BringBedrockBack.VERSION
)
public class BringBedrockBack {

    public static final String MOD_ID = "bringbedrockback";
    public static final String MOD_NAME = "BringBedrockBack";
    public static final String VERSION = "0.0.4";
    public static Logger logger;

    @SidedProxy(serverSide = "be.nevoka.projects.bringbedrockback.proxy.ServerProxy", clientSide = "be.nevoka.projects.bringbedrockback.proxy.ClientProxy")
    public static CommonProxy proxy;

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static BringBedrockBack INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }
}
