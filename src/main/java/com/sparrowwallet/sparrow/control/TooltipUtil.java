package com.sparrowwallet.sparrow.control;

import com.sparrowwallet.sparrow.io.Config;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

public class TooltipUtil {

    /**
     * Sets a tooltip on a control only if tooltips are enabled in the configuration
     * @param control The control to add the tooltip to
     * @param tooltip The tooltip to add
     */
    public static void setTooltip(Control control, Tooltip tooltip) {
        if (!Config.get().isDisableTooltips()) {
            control.setTooltip(tooltip);
        }
    }

    /**
     * Sets a tooltip on a control only if tooltips are enabled in the configuration
     * @param control The control to add the tooltip to
     * @param text The tooltip text
     */
    public static void setTooltip(Control control, String text) {
        if (!Config.get().isDisableTooltips()) {
            control.setTooltip(new Tooltip(text));
        }
    }
}
