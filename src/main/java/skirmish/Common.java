package skirmish;

import net.minecraft.entity.monster.EntityCreeper;

public class Common {
    public static void explodeCreeper(EntityCreeper creeper){
        creeper.setDead();
        boolean griefing = creeper.worldObj.getGameRules().getBoolean("mobGriefing");
        creeper.worldObj.createExplosion(creeper, creeper.posX, creeper.posY, creeper.posZ, 3, griefing);
    }
}
