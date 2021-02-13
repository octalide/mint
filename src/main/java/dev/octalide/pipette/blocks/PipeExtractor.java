package dev.octalide.pipette.blocks;

import dev.octalide.pipette.Pipette;
import dev.octalide.pipette.blockentities.PipeExtractorEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class PipeExtractor extends PipeBase {
    public static final String NAME = "pipe_extractor";
    public static final Identifier ID = new Identifier(Pipette.MOD_ID, NAME);

    @Override
    protected boolean canExtend(BlockState state, BlockState other, Direction direction) {
        boolean can = super.canExtend(state, other, direction);

        if (direction == state.get(Props.input))
            can = true;

        return can;
    }

    @Override
    protected Direction getNextDirection(BlockState state, Direction current) {
        Direction next = incrimentDirection(current);

        if (next == state.get(Props.input))
            next = incrimentDirection(next);

        return next;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = super.getPlacementState(context);

        Direction targetDirection = context.getSide().getOpposite();
        BlockState target = context.getWorld().getBlockState(context.getBlockPos().offset(targetDirection));

        Direction output = targetDirection.getOpposite();
        if (target.getBlock() instanceof PipeBase) {
            if (target.get(Props.output) == targetDirection.getOpposite()) {
                output = target.get(Props.output);
            }
        }

        state = state.with(Props.output, output);
        state = state.with(Props.input, output.getOpposite());
        state = updateExtensions(state, context.getWorld(), context.getBlockPos());

        return state;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new PipeExtractorEntity();
    }
}