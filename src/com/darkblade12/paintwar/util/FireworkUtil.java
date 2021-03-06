package com.darkblade12.paintwar.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUtil {
	public static Random rn = new Random();
	public static Color[] colors = new Color[] { Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE,
			Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW };

	public static void createRandomFirework(Location loc) {
		World world = loc.getWorld();
		Firework firework = (Firework) world.spawn(loc, Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.clearEffects();
		meta.addEffects(getRandomEffects());
		firework.setFireworkMeta(meta);
		try {
			Object worldServer = ReflectionUtil.getMethod("getHandle", world.getClass(), 0).invoke(world);
			Object entityFireworks = ReflectionUtil.getMethod("getHandle", firework.getClass(), 0).invoke(firework);
			ReflectionUtil.getMethod("broadcastEntityEffect", worldServer.getClass(), 2).invoke(worldServer, entityFireworks, (byte) 17);
		} catch (Exception e) {
			return;
		}
		firework.remove();
	}

	public static List<FireworkEffect> getRandomEffects() {
		List<FireworkEffect> effects = new ArrayList<FireworkEffect>();
		for (int f = 1; f <= rn.nextInt(2) + 1; f++)
			effects.add(FireworkEffect.builder().flicker(rn.nextBoolean()).with(Type.values()[rn.nextInt(Type.values().length)]).trail(rn.nextBoolean()).withColor(getRandomColors()).withFade(getRandomColors())
					.build());
		return effects;
	}

	public static List<Color> getRandomColors() {
		List<Color> colors = new ArrayList<Color>();
		for (int a = 0; a <= (rn.nextInt(5) + 1); a++) {
			Color c = FireworkUtil.colors[rn.nextInt(FireworkUtil.colors.length)];
			if (!colors.contains(c)) {
				colors.add(c);
			}
		}
		return colors;
	}
}
