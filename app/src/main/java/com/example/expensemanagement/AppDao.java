package com.example.expensemanagement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {

    // ── Users ──────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity user);

    @Update
    void updateUser(UserEntity user);

    @Query("SELECT * FROM users WHERE firebase_uid = :firebaseUid LIMIT 1")
    UserEntity getUserByFirebaseUid(String firebaseUid);

    @Query("UPDATE users SET last_login_at = :time, updated_at = :time WHERE firebase_uid = :firebaseUid")
    void updateLastLogin(String firebaseUid, String time);

    // ── User Settings ──────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSettings(UserSettingsEntity settings);

    @Query("SELECT * FROM user_settings WHERE user_id = :userId LIMIT 1")
    LiveData<UserSettingsEntity> getSettingsByUserId(String userId);

    // ── Categories ─────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllCategories(List<CategoryEntity> categories);

    @Query("SELECT COUNT(*) FROM categories WHERE is_system = 1")
    int countSystemCategories();

    @Query("SELECT * FROM categories WHERE type = :type AND parent_id IS NULL ORDER BY sort_order")
    List<CategoryEntity> getParentCategories(String type);

    @Query("SELECT * FROM categories WHERE parent_id = :parentId ORDER BY sort_order")
    List<CategoryEntity> getChildCategories(String parentId);
}