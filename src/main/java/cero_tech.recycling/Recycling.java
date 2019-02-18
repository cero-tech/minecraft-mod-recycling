package cero_tech.recycling;

import cero_tech.recycling.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Name: Recycling
 * Description: Contains mod information, instance, proxy, and initializes the mod.
 * Author: cero_tech
 *
 * Last Update: 2/13/2019
 **/

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
     * Proxy instance
     *
     **/
    @SidedProxy(clientSide = PROXY_PREFIX + "ClientProxy", serverSide = PROXY_PREFIX + "CommonProxy")
    public static CommonProxy proxy;

    /**
     *
     * Initialization methods
     *
     **/
    @SubscribeEvent
    public static void preInit(FMLPreInitializationEvent event) {}

    @SubscribeEvent
    public static void init(FMLInitializationEvent event) {}

    @SubscribeEvent
    public static void postnit(FMLPostInitializationEvent event) {}
}
