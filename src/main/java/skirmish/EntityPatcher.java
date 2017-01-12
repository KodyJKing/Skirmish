package skirmish;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;
import skirmish.ai.EntityAIAvoidSwellingCreeper;
import skirmish.ai.EntityAISwellOnTargetBlocked;
import skirmish.ai.EntityAITargetTall;

import java.util.ArrayList;

public class EntityPatcher {

    public static void clearTasks(EntityAITasks tasks){
        EntityAITasks.EntityAITaskEntry[] entries = new EntityAITasks.EntityAITaskEntry[tasks.taskEntries.size()];
        int i = 0;
        for(EntityAITasks.EntityAITaskEntry entry : tasks.taskEntries)
            entries[i++] = entry;
        for(EntityAITasks.EntityAITaskEntry entry : entries)
            tasks.removeTask(entry.action);
    }

    public static void clearAI(EntityLiving living){
        clearTasks(living.tasks);
        clearTasks(living.targetTasks);
    }

    public static void insertTask(EntityAITasks tasks, int priority, EntityAIBase task){

    }

    //-------------------------------------------------------------

    public static void apply(EntityCreature entity){
        EntityAITasks tasks = entity.tasks;
        EntityAITasks targets = entity.targetTasks;
        World world = entity.worldObj;
        if(world.isRemote)
            return;

        //-------------------------------------------------------------

        if(entity instanceof EntityMob && !(entity instanceof EntityEnderman)){
            entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100);

            tasks.addTask(0, new EntityAIAvoidSwellingCreeper(entity, EntityCreeper.class, 32.0F, 1.5D, 1.5D));
            if(!(entity instanceof EntityCreeper)){
                tasks.addTask(0, new EntityAIAvoidEntity(entity, EntityCreeper.class, 16.0F, 1D, 1D));
            }

            targets.addTask(1, new EntityAITargetTall(entity, EntityVillager.class, false));
            targets.addTask(1, new EntityAITargetTall(entity, EntityPlayer.class, false));
            targets.addTask(2, new EntityAINearestAttackableTarget(entity, EntityIronGolem.class, true));

            if(!(entity instanceof EntityZombie))
                tasks.addTask(6, new EntityAIMoveThroughVillage(entity, 1.0D, false));
        }

        //-------------------------------------------------------------

        if(entity instanceof EntitySkeleton || entity instanceof EntityWitch)
        {
            ((PathNavigateGround)entity.getNavigator()).setBreakDoors(true);
            tasks.addTask(4, new EntityAIOpenDoor(entity, false));
        }

        //-------------------------------------------------------------

        if(entity instanceof EntityCreeper)
        {
            EntityCreeper creeper = (EntityCreeper) entity;
            tasks.addTask(2, new EntityAISwellOnTargetBlocked(creeper));
        }

        //-------------------------------------------------------------

        if(entity instanceof EntityVillager){
            tasks.addTask(1, new EntityAIAvoidEntity(entity, EntityCreeper.class, 8.0F, 0.8D, 1D));
            tasks.addTask(1, new EntityAIAvoidEntity(entity, EntitySkeleton.class, 16.0F, 0.7D, 0.7D));
            tasks.addTask(1, new EntityAIAvoidEntity(entity, EntitySlime.class, 8.0F, 0.3D, 0.3D));
        }

        //-------------------------------------------------------------

        if(entity instanceof EntityIronGolem)
            targets.addTask(3, new EntityAINearestAttackableTarget(entity, EntityLiving.class, false, true));

        //-------------------------------------------------------------

        if(entity instanceof EntityCreeper || entity instanceof EntityZombie || entity instanceof EntitySkeleton || entity instanceof EntityWitch)
            entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
    }
}
