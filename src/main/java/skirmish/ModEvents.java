package skirmish;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skirmish.ai.EntityAIAvoidSwellingCreeper;
import skirmish.ai.EntityAISwellOnTargetBlocked;
import skirmish.ai.EntityAITargetTall;

public class ModEvents {
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof  EntityCreature)
            EntityPatcher.apply((EntityCreature) event.getEntity());
    }

    @SubscribeEvent
    public void onAttacked(LivingAttackEvent event) {
        Entity entity = event.getEntity();

        if(entity.isDead || entity.worldObj.isRemote)
            return;

        DamageSource source = event.getSource();
        if(entity.getClass().equals(EntityCreeper.class) && (source.isExplosion() || source.isFireDamage()))
            Common.explodeCreeper((EntityCreeper) entity);

        if(entity.getClass().equals(EntitySlime.class) && source.damageType == "arrow"){
            EntitySlime slime = (EntitySlime) entity;
            entity.worldObj.createExplosion(null, entity.posX, entity.posY, entity.posZ, slime.getSlimeSize(), false);
            entity.setDead();
        }
    }
}
