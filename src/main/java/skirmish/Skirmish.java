package skirmish;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import skirmish.proxy.CommonProxy;

@Mod(modid = Skirmish.modId, name = Skirmish.name, version = Skirmish.name, acceptedMinecraftVersions = "[1.10, 1.10.2]")
public class Skirmish {
    public static final String modId = "skirmish";
    public static final String name = "Smirkmish";
    public static final String version = "1.0.0";

    @SidedProxy(clientSide = "skirmish.proxy.ClientProxy", serverSide = "skirmish.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(modId)
    public static Skirmish instance;

    @Mod.EventHandler
    public void preInit(FMLInitializationEvent event)
    {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
    }

    @Mod.EventHandler
    public void postInit(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        proxy.registerEntityRendering();
    }
}
