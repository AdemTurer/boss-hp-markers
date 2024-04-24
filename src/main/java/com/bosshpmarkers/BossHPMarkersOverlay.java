package com.bosshpmarkers;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class BossHPMarkersOverlay extends Overlay {
    private final Client client;
    private final BossHPMarkersConfig config;
    private final BossHPMarkersPlugin plugin;

    @Inject
    public BossHPMarkersOverlay(Client client, BossHPMarkersConfig config, BossHPMarkersPlugin plugin) {
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(PRIORITY_MED);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Rectangle HPBar = BossHPMarkersPlugin.bossHPBarWidget.getBounds();
        int markerPer = 50;
        int markerPos = HPBar.x + markerPer*HPBar.width/100;
        int y1 = HPBar.y;
        int y2 = y1 + HPBar.height;

        graphics.setStroke(new BasicStroke(3));
        graphics.setColor(Color.MAGENTA);
        graphics.drawLine(markerPos, y1, markerPos, y2);

        return null;
    }
}

