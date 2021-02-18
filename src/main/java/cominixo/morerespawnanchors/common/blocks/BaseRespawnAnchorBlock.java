package cominixo.morerespawnanchors.common.blocks;

import java.util.Optional;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BaseRespawnAnchorBlock extends Block implements IRespawnAnchorBlock {
	
	protected int maxCharges;
	
	public BaseRespawnAnchorBlock(Properties properties, int maxCharges) {
		super(properties);
		this.maxCharges = 4;
		this.setDefaultState(this.getDefaultState().with(BlockStateProperties.CHARGES, 0));
	}

	public IntegerProperty getCharges() {
		return BlockStateProperties.CHARGES;
	}

	public int getMaxCharges() {
		return this.maxCharges;
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (handIn == Hand.MAIN_HAND && !this.isValidFuel(itemstack)
				&& this.isValidFuel(player.getHeldItem(Hand.OFF_HAND))) {
			return ActionResultType.PASS;
		} else if (isValidFuel(itemstack) && notFullyCharged(state)) {
			this.chargeAnchor(worldIn, pos, state);
			if (!player.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}

			return ActionResultType.func_233537_a_(worldIn.isRemote);
		} else if (state.get(this.getCharges()) == 0) {
			return ActionResultType.PASS;
		} else if (!this.doesRespawnAnchorWork(worldIn)) {
			if (!worldIn.isRemote) {
				this.triggerExplosion(state, worldIn, pos);
			}

			return ActionResultType.func_233537_a_(worldIn.isRemote);
		} else {
			if (!worldIn.isRemote) {
				ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
				if (serverplayerentity.func_241141_L_() != worldIn.getDimensionKey()
						|| !serverplayerentity.func_241140_K_().equals(pos)) {
					serverplayerentity.func_242111_a(worldIn.getDimensionKey(), pos, 0.0F, false, true);
					worldIn.playSound((PlayerEntity) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
							(double) pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN,
							SoundCategory.BLOCKS, 1.0F, 1.0F);
					return ActionResultType.SUCCESS;
				}
			}

			return ActionResultType.CONSUME;
		}
	}

	public boolean isValidFuel(ItemStack itemStack) {
		return itemStack.getItem() == Items.GLOWSTONE;
	}

	public boolean notFullyCharged(BlockState state) {
		return state.get(this.getCharges()) < this.maxCharges;
	}

	public boolean doesRespawnAnchorWork(World world) {
		return RespawnAnchorBlock.doesRespawnAnchorWork(world);
	}

	public void chargeAnchor(World world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state.with(this.getCharges(), Integer.valueOf(state.get(this.getCharges()) + 1)), 3);
		world.playSound((PlayerEntity) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
				(double) pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(BlockState state, World worldIn, BlockPos pos) {
		return RespawnAnchorBlock.getChargeScale(state, 15 * this.maxCharges / 4);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@SuppressWarnings("deprecation")
	private void triggerExplosion(BlockState state, World world, final BlockPos pos2) {
		world.removeBlock(pos2, false);
		boolean flag = Direction.Plane.HORIZONTAL.getDirectionValues().map(pos2::offset).anyMatch((posIn) -> {
			return RespawnAnchorBlock.isNearWater(posIn, world);
		});
		final boolean flag1 = flag || world.getFluidState(pos2.up()).isTagged(FluidTags.WATER);
		ExplosionContext explosioncontext = new ExplosionContext() {
			public Optional<Float> getExplosionResistance(Explosion explosion, IBlockReader reader, BlockPos pos,
					BlockState state, FluidState fluid) {
				return pos.equals(pos2) && flag1 ? Optional.of(Blocks.WATER.getExplosionResistance())
						: super.getExplosionResistance(explosion, reader, pos, state, fluid);
			}
		};
		world.createExplosion((Entity) null, DamageSource.func_233546_a_(), explosioncontext,
				(double) pos2.getX() + 0.5D, (double) pos2.getY() + 0.5D, (double) pos2.getZ() + 0.5D, 5.0F, true,
				Explosion.Mode.DESTROY);
	}

	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.get(this.getCharges()) != 0) {
			if (rand.nextInt(100) == 0) {
				worldIn.playSound((PlayerEntity) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
						(double) pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS,
						1.0F, 1.0F);
			}

			double d0 = (double) pos.getX() + 0.5D + (0.5D - rand.nextDouble());
			double d1 = (double) pos.getY() + 1.0D;
			double d2 = (double) pos.getZ() + 0.5D + (0.5D - rand.nextDouble());
			double d3 = (double) rand.nextFloat() * 0.04D;
			worldIn.addParticle(ParticleTypes.REVERSE_PORTAL, d0, d1, d2, 0.0D, d3, 0.0D);
		}
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(this.getCharges());
	}

}
