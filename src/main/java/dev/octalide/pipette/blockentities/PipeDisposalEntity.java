package dev.octalide.pipette.blockentities;

import dev.octalide.pipette.PBlocks;
import dev.octalide.pipette.api.blockentities.PipeEntityBase;
import dev.octalide.pipette.api.blocks.properties.PipeDisposalProps;

public class PipeDisposalEntity extends PipeEntityBase {
    public PipeDisposalEntity() {
        super(PBlocks.PIPE_DISPOSAL_ENTITY);
    }

    @Override
    protected boolean attemptOutput() {
        if (getCachedState().get(PipeDisposalProps.powered)) return false;
        if (this.isEmpty()) return false;

        // NOTE: need document how this works
        // sorry for the mess of code
        this.clear();

        return true;
    }
}
