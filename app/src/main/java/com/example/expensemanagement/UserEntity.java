package com.example.expensemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "users",
    indices = {
        @Index(value = "email", unique = true),
        @Index(value = "firebase_uid", unique = true)
    }
)
class UserEntity {

    @PrimaryKey @NonNull @ColumnInfo(name = "user_id")   public String userId;
    @NonNull @ColumnInfo(name = "email")                 public String email;
    @NonNull @ColumnInfo(name = "password_hash")         public String passwordHash;
    @NonNull @ColumnInfo(name = "full_name")             public String fullName;
    @Nullable @ColumnInfo(name = "avatar_url")           public String avatarUrl;
    @NonNull @ColumnInfo(name = "currency_code")         public String currencyCode = "VND";
    @NonNull @ColumnInfo(name = "language")              public String language = "vi";
    @NonNull @ColumnInfo(name = "auth_provider")         public String authProvider = "local";
    @Nullable @ColumnInfo(name = "firebase_uid")         public String firebaseUid;
    @ColumnInfo(name = "is_active")                      public int isActive = 1;
    @NonNull @ColumnInfo(name = "created_at")            public String createdAt;
    @NonNull @ColumnInfo(name = "updated_at")            public String updatedAt;
    @Nullable @ColumnInfo(name = "last_login_at")        public String lastLoginAt;

    public UserEntity(@NonNull String userId, @NonNull String email,
                      @NonNull String passwordHash, @NonNull String fullName,
                      @Nullable String firebaseUid,
                      @NonNull String createdAt, @NonNull String updatedAt) {
        this.userId       = userId;
        this.email        = email;
        this.passwordHash = passwordHash;
        this.fullName     = fullName;
        this.firebaseUid  = firebaseUid;
        this.createdAt    = createdAt;
        this.updatedAt    = updatedAt;
        this.lastLoginAt  = createdAt;
    }
}

// ─────────────────────────────────────────────────────────
// Bảng: user_settings
// ─────────────────────────────────────────────────────────
@Entity(
    tableName = "user_settings",
    indices = { @Index(value = "user_id", unique = true) },
    foreignKeys = {
        @ForeignKey(entity = UserEntity.class,
            parentColumns = "user_id", childColumns = "user_id",
            onDelete = ForeignKey.CASCADE)
    }
)
class UserSettingsEntity {

    @PrimaryKey @NonNull @ColumnInfo(name = "setting_id")          public String settingId;
    @NonNull @ColumnInfo(name = "user_id")                         public String userId;
    @NonNull @ColumnInfo(name = "theme")                           public String theme = "light";
    @ColumnInfo(name = "daily_reminder_enabled")                   public int dailyReminderEnabled = 1;
    @NonNull @ColumnInfo(name = "daily_reminder_time")             public String dailyReminderTime = "21:00:00";
    @ColumnInfo(name = "budget_alert_enabled")                     public int budgetAlertEnabled = 1;
    @ColumnInfo(name = "sms_reading_enabled")                      public int smsReadingEnabled = 0;
    @ColumnInfo(name = "biometric_enabled")                        public int biometricEnabled = 0;
    @ColumnInfo(name = "app_lock_enabled")                         public int appLockEnabled = 0;
    @ColumnInfo(name = "app_lock_timeout")                         public int appLockTimeout = 5;
    @ColumnInfo(name = "gps_suggestion_enabled")                   public int gpsSuggestionEnabled = 1;
    @ColumnInfo(name = "first_day_of_week")                        public int firstDayOfWeek = 1;
    @NonNull @ColumnInfo(name = "date_format")                     public String dateFormat = "dd/MM/yyyy";
    @NonNull @ColumnInfo(name = "updated_at")                      public String updatedAt;

    public UserSettingsEntity(@NonNull String settingId,
                               @NonNull String userId,
                               @NonNull String updatedAt) {
        this.settingId = settingId;
        this.userId    = userId;
        this.updatedAt = updatedAt;
    }
}

// ─────────────────────────────────────────────────────────
// Bảng: categories
// ─────────────────────────────────────────────────────────
@Entity(
    tableName = "categories",
    indices = {
        @Index(value = {"user_id", "type"}),
        @Index(value = "parent_id")
    },
    foreignKeys = {
        @ForeignKey(entity = UserEntity.class,
            parentColumns = "user_id", childColumns = "user_id",
            onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = CategoryEntity.class,
            parentColumns = "category_id", childColumns = "parent_id",
            onDelete = ForeignKey.SET_NULL)
    }
)
class CategoryEntity {

    @PrimaryKey @NonNull @ColumnInfo(name = "category_id")  public String categoryId;
    @Nullable @ColumnInfo(name = "user_id")                 public String userId;
    @Nullable @ColumnInfo(name = "parent_id")               public String parentId;
    @NonNull @ColumnInfo(name = "name")                     public String name;
    @NonNull @ColumnInfo(name = "type")                     public String type;
    @Nullable @ColumnInfo(name = "icon")                    public String icon;
    @Nullable @ColumnInfo(name = "color")                   public String color;
    @ColumnInfo(name = "is_system")                         public int isSystem = 0;
    @ColumnInfo(name = "sort_order")                        public int sortOrder = 0;
    @NonNull @ColumnInfo(name = "created_at")               public String createdAt;

    public CategoryEntity(@NonNull String categoryId, @Nullable String userId,
                          @Nullable String parentId, @NonNull String name,
                          @NonNull String type, @Nullable String icon,
                          @Nullable String color, int isSystem, int sortOrder,
                          @NonNull String createdAt) {
        this.categoryId = categoryId;
        this.userId     = userId;
        this.parentId   = parentId;
        this.name       = name;
        this.type       = type;
        this.icon       = icon;
        this.color      = color;
        this.isSystem   = isSystem;
        this.sortOrder  = sortOrder;
        this.createdAt  = createdAt;
    }
}