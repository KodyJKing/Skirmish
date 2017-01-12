package skirmish;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.MathHelper;

public class Common {
    public static void explodeCreeper(EntityCreeper creeper){
        creeper.setDead();
        boolean griefing = creeper.worldObj.getGameRules().getBoolean("mobGriefing");
        creeper.worldObj.createExplosion(creeper, creeper.posX, creeper.posY, creeper.posZ, 3, griefing);
    }

    public static PathPoint entityPathPoint(Entity target){
        return new PathPoint(
                MathHelper.floor_double(target.posX - target.width / 2.0 + 0.5),
                MathHelper.floor_double(target.posY + 0.5),
                MathHelper.floor_double(target.posX - target.width / 2.0 + 0.5));
    }
}
