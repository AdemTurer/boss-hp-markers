package com.bosshpmarkers;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BossHPMarkersTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BossHPMarkersPlugin.class);
		RuneLite.main(args);
	}
}