package com.uitrs.game.majiang.common.room;

import java.util.Map.Entry;

public interface RoomUserCallable<V> {
	V call(Room room, Entry<Integer, Long> user) throws Exception;
}
