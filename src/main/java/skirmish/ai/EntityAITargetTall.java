package skirmish.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAITargetTall extends EntityAINearestAttackableTarget {
    public EntityAITargetTall(EntityCreature creature, Class classTarget, boolean checkSight) {
        super(creature, classTarget, checkSight);
    }

    @Override
    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return taskOwner.getEntityBoundingBox().expand(targetDistance, targetDistance, targetDistance);
    }
}
