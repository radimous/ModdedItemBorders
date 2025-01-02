package com.radimous.moddeditemborders.mixin;
import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class ModdedItemBordersMixinCanceller implements MixinCanceller {
    @Override public boolean shouldCancel(List<String> list, String s) {
        // Don't render the border twice
        return s.contains("xyz.iwolfking.woldsvaults.mixins.itemborders");
    }
}
