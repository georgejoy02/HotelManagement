package com.hm.service;

import com.hm.dao.RoomDao;
import com.hm.model.Room;

public class RoomService {
    private RoomDao roomDao = new RoomDao();

    public Room checkRoomStatus(int roomId) {
        return roomDao.getRoomStatus(roomId);
    }
}
