package cero_tech.recycling;

import cero_tech.recycling.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * Name: Recycling
 * Description: Contains mod information, instance, proxy, and initializes the mod.
 * Author: cero_tech
 *
 * Created: 2/13/2019
 **/

@SuppressWarnings("WeakerAccess")
@Mod(
        modid = Recycling.MOD_ID,
        name = Recycling.MOD_NAME,
        version = Recycling.MOD_VERSION,
        acceptedMinecraftVersions = Recycling.MC_VERSION,
        dependencies = Recycling.DEPENDENCIES
)
public class Recycling {

    /**
     *
     * Mod Information
     *
     **/
    public static final String MOD_ID = "recycling";
    public static final String MOD_NAME = "Recycling";
    public static final String MOD_VERSION = "1.0.1";
    public static final String MC_VERSION = "1.12.2";
    public static final String FORGE_VERSION = "14.23.5.2768";
    public static final String DEPENDENCIES = "required-after:forge@[" + FORGE_VERSION + ",);";
    public static final String PROXY_PREFIX = "cero_tech.recycling.proxy.";

    /**
     *
     * Mod Instance
     *
     **/
    @Mod.Instance(MOD_ID)
    public static Recycling instance;

    /**
     *
     * Proxy Instance
     *
     **/
    @SidedProxy(clientSide = PROXY_PREFIX + "ClientProxy", serverSide = PROXY_PREFIX + "CommonProxy")
    private static CommonProxy proxy;

    /**
     *
     * Mod Logger
     *
     **/
    private static Logger logger;

    /**
     * Initialization Methods
     **/
    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    public void writeInfo(String msg) {
        logger.info(msg);
    }

    public void writeError(String msg) {
        logger.error(msg);
    }
}
