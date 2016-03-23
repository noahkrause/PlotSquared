package com.plotsquared.bukkit.titles;

import com.plotsquared.bukkit.chat.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * [ PlotSquared DefaultTitleManager by Maxim Van de Wynckel ]
 *
 * @author Maxim Van de Wynckel
 *
 */
public class DefaultTitleManager {

    private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<>();
    /* Title packet */
    private Class<?> packetTitle;
    /* Title packet actions ENUM */
    private Class<?> packetActions;
    /* Chat serializer */
    private Class<?> nmsChatSerializer;
    private Class<?> chatBaseComponent;
    /* Title text and color */
    private String title = "";
    private ChatColor titleColor = ChatColor.WHITE;
    /* Subtitle text and color */
    private String subtitle = "";
    private ChatColor subtitleColor = ChatColor.WHITE;
    /* Title timings */
    private int fadeInTime = -1;
    private int stayTime = -1;
    private int fadeOutTime = -1;
    private boolean ticks = false;

    /**
     * Create a new 1.8 title
     *
     * @param title
     *            Title
     * @throws ClassNotFoundException
     */
    public DefaultTitleManager(String title) throws ClassNotFoundException {
        this.title = title;
        loadClasses();
    }

    /**
     * Create a new 1.8 title
     *
     * @param title
     *            Title text
     * @param subtitle
     *            Subtitle text
     * @throws ClassNotFoundException
     */
    public DefaultTitleManager(String title, String subtitle) throws ClassNotFoundException {
        this.title = title;
        this.subtitle = subtitle;
        loadClasses();
    }

    /**
     * Copy 1.8 title.
     *
     * @param title Title
     * @throws ClassNotFoundException
     */
    public DefaultTitleManager(DefaultTitleManager title) throws ClassNotFoundException {
        // Copy title
        this.title = title.title;
        this.subtitle = title.subtitle;
        this.titleColor = title.titleColor;
        this.subtitleColor = title.subtitleColor;
        this.fadeInTime = title.fadeInTime;
        this.fadeOutTime = title.fadeOutTime;
        this.stayTime = title.stayTime;
        this.ticks = title.ticks;
        loadClasses();
    }

    /**
     * Create a new 1.8 title.
     *
     * @param title Title text
     * @param subtitle Subtitle text
     * @param fadeInTime Fade in time
     * @param stayTime Stay on screen time
     * @param fadeOutTime Fade out time
     * @throws ClassNotFoundException
     */
    public DefaultTitleManager(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime)
            throws ClassNotFoundException {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeInTime = fadeInTime;
        this.stayTime = stayTime;
        this.fadeOutTime = fadeOutTime;
        loadClasses();
    }

    private static boolean equalsTypeArray(Class<?>[] a, Class<?>[] o) {
        if (a.length != o.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(o[i]) && !a[i].isAssignableFrom(o[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Load spigot and NMS classes
     * @throws ClassNotFoundException
     */
    private void loadClasses() throws ClassNotFoundException {
        this.packetTitle = Reflection.getNMSClass("PacketPlayOutTitle");
        this.packetActions = Reflection.getNMSClass("EnumTitleAction");
        this.chatBaseComponent = Reflection.getNMSClass("IChatBaseComponent");
        this.nmsChatSerializer = Reflection.getNMSClass("ChatSerializer");
    }

    /**
     * Get title text
     *
     * @return Title text
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set title text
     *
     * @param title
     *            Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get subtitle text
     *
     * @return Subtitle text
     */
    public String getSubtitle() {
        return this.subtitle;
    }

    /**
     * Set subtitle text.
     *
     * @param subtitle Subtitle text
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * Set the title color.
     *
     * @param color Chat color
     */
    public void setTitleColor(ChatColor color) {
        this.titleColor = color;
    }

    /**
     * Set the subtitle color.
     *
     * @param color Chat color
     */
    public void setSubtitleColor(ChatColor color) {
        this.subtitleColor = color;
    }

    /**
     * Set title fade in time.
     *
     * @param time Time
     */
    public void setFadeInTime(int time) {
        this.fadeInTime = time;
    }

    /**
     * Set title fade out time.
     *
     * @param time Time
     */
    public void setFadeOutTime(int time) {
        this.fadeOutTime = time;
    }

    /**
     * Set title stay time.
     *
     * @param time Time
     */
    public void setStayTime(int time) {
        this.stayTime = time;
    }

    /**
     * Set timings to ticks.
     */
    public void setTimingsToTicks() {
        this.ticks = true;
    }

    /**
     * Set timings to seconds.
     */
    public void setTimingsToSeconds() {
        this.ticks = false;
    }

    /**
     * Send the title to a player.
     *
     * @param player Player
     * @throws Exception
     */
    public void send(Player player) throws Exception {
        if (this.packetTitle != null) {
            // First reset previous settings
            resetTitle(player);
            // Send timings first
            Object handle = getHandle(player);
            Object connection = getField(handle.getClass(), "playerConnection").get(handle);
            Object[] actions = this.packetActions.getEnumConstants();
            Method sendPacket = getMethod(connection.getClass(), "sendPacket");
            Object packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent, Integer.TYPE, Integer.TYPE, Integer.TYPE)
                    .newInstance(actions[2], null, this.fadeInTime * (this.ticks ? 1 : 20),
                            this.stayTime * (this.ticks ? 1 : 20), this.fadeOutTime * (this.ticks ? 1 : 20));
            // Send if set
            if (this.fadeInTime != -1 && this.fadeOutTime != -1 && this.stayTime != -1) {
                sendPacket.invoke(connection, packet);
            }
            // Send title
            Object serialized = getMethod(this.nmsChatSerializer, "a", String.class).invoke(null,
                    "{text:\"" + ChatColor.translateAlternateColorCodes('&', this.title) + "\",color:" + this.titleColor.name().toLowerCase() + "}");
            packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent).newInstance(actions[0], serialized);
            sendPacket.invoke(connection, packet);
            if (!this.subtitle.isEmpty()) {
                // Send subtitle if present
                serialized = getMethod(this.nmsChatSerializer, "a", String.class).invoke(null,
                        "{text:\"" + ChatColor.translateAlternateColorCodes('&', this.subtitle) + "\",color:" + this.subtitleColor.name()
                                .toLowerCase() + "}");
                packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent).newInstance(actions[1], serialized);
                sendPacket.invoke(connection, packet);
            }
        }
    }

    /**
     * Broadcast the title to all players.
     *
     * @throws Exception
     */
    public void broadcast() throws Exception {
        for (Player p : Bukkit.getOnlinePlayers()) {
            send(p);
        }
    }

    /**
     * Clear the title.
     *
     * @param player Player
     * @throws Exception
     */
    public void clearTitle(Player player) throws Exception {
        // Send timings first
        Object handle = getHandle(player);
        Object connection = getField(handle.getClass(), "playerConnection").get(handle);
        Object[] actions = this.packetActions.getEnumConstants();
        Method sendPacket = getMethod(connection.getClass(), "sendPacket");
        Object packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent).newInstance(actions[3], null);
        sendPacket.invoke(connection, packet);
    }

    /**
     * Reset the title settings.
     *
     * @param player Player
     * @throws Exception
     */
    public void resetTitle(Player player) throws Exception {
        // Send timings first
        Object handle = getHandle(player);
        Object connection = getField(handle.getClass(), "playerConnection").get(handle);
        Object[] actions = this.packetActions.getEnumConstants();
        Method sendPacket = getMethod(connection.getClass(), "sendPacket");
        Object packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent).newInstance(actions[4], null);
        sendPacket.invoke(connection, packet);
    }

    private Class<?> getPrimitiveType(Class<?> clazz) {
        return CORRESPONDING_TYPES.containsKey(clazz) ? CORRESPONDING_TYPES.get(clazz) : clazz;
    }

    private Class<?>[] toPrimitiveTypeArray(Class<?>[] classes) {
        int a = classes != null ? classes.length : 0;
        Class<?>[] types = new Class<?>[a];
        for (int i = 0; i < a; i++) {
            types[i] = getPrimitiveType(classes[i]);
        }
        return types;
    }

    private Object getHandle(Object obj) {
        try {
            return getMethod("getHandle", obj.getClass()).invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Method getMethod(String name, Class<?> clazz, Class<?>... paramTypes) {
        Class<?>[] t = toPrimitiveTypeArray(paramTypes);
        for (Method m : clazz.getMethods()) {
            Class<?>[] types = toPrimitiveTypeArray(m.getParameterTypes());
            if (m.getName().equals(name) && equalsTypeArray(types, t)) {
                return m;
            }
        }
        return null;
    }

    private Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Method getMethod(Class<?> clazz, String name, Class<?>... args) {
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(name) && (args.length == 0 || ClassListEqual(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    private boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        if (l1.length != l2.length) {
            return false;
        }
        boolean equal = true;
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }
}
