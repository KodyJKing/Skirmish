package skirmish.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import skirmish.Common;

public class EntityAISwellOnTargetBlocked extends EntityAIBase{
    EntityCreeper creeper;

    public EntityAISwellOnTargetBlocked(EntityCreeper self) {
        super();
        this.creeper = self;
    }

    @Override
    public boolean continueExecuting() {
        return !canReach();
    }

    @Override
    public void updateTask() {
        if(!tryGetCloser())
            creeper.setCreeperState(1);
        else
            creeper.setCreeperState(-1);
    }

    @Override
    public void resetTask() {
        if(creeper != null)
            creeper.setCreeperState(-1);
    }

    @Override
    public boolean shouldExecute() {
        PathNavigate nav = creeper.getNavigator();
        EntityLivingBase target = creeper.getAttackTarget();

       if(target != null && nav.getPath() == null && !tryGetCloser())
           return true;

        return false;
    }

    public boolean tryGetCloser(){
        PathNavigate nav = creeper.getNavigator();
        EntityLivingBase target = creeper.getAttackTarget();

        if(target == null)
            return false;

        Path path = nav.getPathToEntityLiving(target);
        if(path == null || nav.getNodeProcessor() == null)
            return false;

        PathPoint end = path.getFinalPathPoint();
        PathPoint goal = Common.entityPathPoint(target);
        PathPoint here = Common.entityPathPoint(creeper);

        double currentDist = here.distanceManhattan(goal);
        double newDist = end.distanceManhattan(goal);

        if(newDist < currentDist){
            nav.setPath(path, 1);
            return true;
        }

        return false;
    }

    public boolean canReach(){
        PathNavigate nav = creeper.getNavigator();
        EntityLivingBase target = creeper.getAttackTarget();
        if(target == null)
            return true;
        PathPoint goal = Common.entityPathPoint(target);
        return nav.getPath() != null && goal.distanceManhattan(nav.getPath().getFinalPathPoint()) <= 1;//goal.equals(nav.getPath().getFinalPathPoint());
    }
}
