package com.example.hotelbnmproject.repository;

import com.example.hotelbnmproject.entity.Hotel;
import com.example.hotelbnmproject.entity.Inventory;
import com.example.hotelbnmproject.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    public void deleteByRoom(Room room);

    @Query("""
            SELECT DISTINCT i.hotel
            from Inventory i WHERE
                i.city = :city AND
                i.date BETWEEN :startDt AND :endDt AND
                i.closed = false AND
                (i.totalCount-i.bookedCount-i.reservedCount) >= :roomCount
            GROUP BY i.hotel,i.room
            HAVING COUNT(i.date) = :dateCount
            """)
    public Page<Hotel> getAllValidInventoriesForDtRange(
            @Param("city") String city,
            @Param("startDt") LocalDate startDt,
            @Param("endDt") LocalDate endDt,
            @Param("roomCount") Integer roomCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    @Query("""
            SELECT i
            FROM Inventory  i
            WHERE i.room.id = :roomId AND
                  i.date BETWEEN :checkInDt AND :checkOutDt AND
                  i.closed = false AND
                  (i.totalCount-i.bookedCount-i.reservedCount) >= :roomCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<Inventory> findAndLockAvailableInventory(
            @Param("roomId") Long roomId,
            @Param("checkInDt") LocalDate checkInDt,
            @Param("checkOutDt") LocalDate checkOutDt,
            @Param("roomCount") Integer roomCount
    );

    @Query("""
            SELECT i
            FROM Inventory  i
            WHERE i.room.id = :roomId AND
                  i.date BETWEEN :startDate AND :endDate AND
                  i.closed = false AND
                  (i.totalCount-i.bookedCount) >= :roomCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<Inventory> findAndLockReservedInventory(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCount") Integer roomCount
    );


    //need to apply a lock before modifying the reserved count because it might get inconsistent
    @Modifying
    @Query("""
            UPDATE Inventory i
            SET  i.reservedCount = i.reservedCount - :roomCount,
                 i.bookedCount = i.bookedCount + :roomCount
            WHERE i.room.id = :roomId AND
                  i.date BETWEEN :startDate AND :endDate AND
                  (i.totalCount - i.bookedCount) >= :roomCount AND 
                  i.reservedCount >= :roomCount AND
                  i.closed = false
            """)

    void confirmBooking(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCount") Integer roomCount
    );


    @Modifying
    @Query("""
            UPDATE Inventory i
            SET  i.bookedCount = i.bookedCount - :roomCount
            WHERE i.room.id = :roomId AND
                  i.date BETWEEN :startDate AND :endDate AND
                  (i.totalCount - i.bookedCount) >= :roomCount AND
                  i.closed = false
            """)

    void cancelBooking(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCount") Integer roomCount
    );


    List<Inventory> findByHotelAndDateBetween(Hotel hotel, LocalDate startDate, LocalDate endDate);
}
