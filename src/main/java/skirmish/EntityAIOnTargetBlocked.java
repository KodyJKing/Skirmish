package skirmish;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.Sys;

public class EntityAIOnTargetBlocked extends EntityAIBase{
    EntityCreeper self;

    public EntityAIOnTargetBlocked(EntityCreeper self) {
        super();
        this.self = self;
    }

    @Override
    public void updateTask() {
        if(shouldExecute())
            self.setCreeperState(1);
        else
            self.setCreeperState(-1);
    }


    @Override
    public boolean shouldExecute() {
        PathNavigate nav = self.getNavigator();
        Path currentPath = nav.getPath();
        EntityLivingBase target = self.getAttackTarget();

       if(currentPath == null && target != null)
           return true;

        return false;
    }
}
