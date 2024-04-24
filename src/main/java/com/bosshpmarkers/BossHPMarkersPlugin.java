package com.bosshpmarkers;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Boss HP Markers"
)
public class BossHPMarkersPlugin extends Plugin
{
	private static final String RUNELITE_GROUP_KEY = "runelite";
	private static final String POSITION_KEY = "_preferredPosition";
	private static final String LOCATION_KEY = "_preferredLocation";
	private static final String HP_BAR_NAME = "HEALTH_OVERLAY_BAR";
	public static int bossMaxHP=0;
	public static int bossCurHP=0;
	public static int[] HPBarLocation = new int[2];
	private static final int HP_BAR_TEXT_UPDATE_SCRIPT_ID = 2102;

	static Widget bossNameWidget;
	static Widget bossHPBarWidget;


	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ConfigManager configManager;
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private BossHPMarkersOverlay bossHPMarkersOverlay;

	@Inject
	private BossHPMarkersConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Boss HP Markers started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Boss HP Markers stopped!");
		overlayManager.remove(bossHPMarkersOverlay);
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged varbitChanged){
		//S 303.9 is the widget for the text of the boss.
		//S 303.13 is the widget for the exact HP bar GREEN + RED in the boss.
		//S 303.14 is the widget for the exact HP bar WHILE TRANSITIONING FROM GREEN TO RED in the boss.
		//S 303.15 is the widget for the exact HP bar ONLY GREEN in the boss.

		if(varbitChanged.getVarbitId() == Varbits.BOSS_HEALTH_MAXIMUM){
			bossMaxHP = varbitChanged.getValue();
			bossNameWidget = client.getWidget(303,9);
			bossHPBarWidget = client.getWidget(303,13);
			log.info("bossNameWidget loaded. Boss is " + bossNameWidget.getText());
			log.info("bossHPBarWidget loaded. Located at " + bossNameWidget.getBounds().toString());

			if(bossMaxHP==0){
				//Stop rendering, boss is dead or gone
				overlayManager.remove(bossHPMarkersOverlay);
				return;
			}
			overlayManager.add(bossHPMarkersOverlay);
		} else if (varbitChanged.getVarbitId() == Varbits.BOSS_HEALTH_CURRENT){
			bossCurHP = varbitChanged.getValue();
		}
	}

	@Provides
	BossHPMarkersConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BossHPMarkersConfig.class);
	}


}
