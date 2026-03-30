package com.example.expensemanagement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities = {
        UserEntity.class,
        UserSettingsEntity.class,
        CategoryEntity.class,
        // TODO: thêm entity của các thành viên khác vào đây
        // TransactionEntity.class,
        // WalletEntity.class,
        // BudgetEntity.class,
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "expense_manager.db";
    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public abstract AppDao appDao();   // Chỉ còn 1 DAO duy nhất

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DB_NAME
                        )
                        .addCallback(seedCallback)
                        .build();
                }
            }
        }
        return INSTANCE;
    }

    // Seed 32 danh mục hệ thống khi tạo DB lần đầu
    private static final RoomDatabase.Callback seedCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            executor.execute(() -> INSTANCE.appDao().insertAllCategories(buildSeedCategories()));
        }
    };

    private static List<CategoryEntity> buildSeedCategories() {
        String now = "2024-01-01T00:00:00";
        List<CategoryEntity> list = new ArrayList<>();

        // Thu nhập cấp 1
        list.add(new CategoryEntity("cat_income_01", null, null, "Lương & Thưởng",  "income", "ic_salary",   "#4CAF50", 1, 1, now));
        list.add(new CategoryEntity("cat_income_02", null, null, "Kinh doanh",       "income", "ic_business", "#2196F3", 1, 2, now));
        list.add(new CategoryEntity("cat_income_03", null, null, "Đầu tư",           "income", "ic_invest",   "#9C27B0", 1, 3, now));
        list.add(new CategoryEntity("cat_income_04", null, null, "Thu nhập khác",    "income", "ic_other",    "#607D8B", 1, 4, now));

        // Thu nhập cấp 2
        list.add(new CategoryEntity("cat_income_01_1", null, "cat_income_01", "Lương cơ bản",  "income", "ic_salary",   "#4CAF50", 1, 1, now));
        list.add(new CategoryEntity("cat_income_01_2", null, "cat_income_01", "Thưởng",        "income", "ic_bonus",    "#8BC34A", 1, 2, now));
        list.add(new CategoryEntity("cat_income_03_1", null, "cat_income_03", "Cổ tức",        "income", "ic_dividend", "#9C27B0", 1, 1, now));
        list.add(new CategoryEntity("cat_income_03_2", null, "cat_income_03", "Lãi tiết kiệm", "income", "ic_interest", "#AB47BC", 1, 2, now));

        // Chi tiêu cấp 1
        list.add(new CategoryEntity("cat_exp_01", null, null, "Ăn uống",       "expense", "ic_food",      "#F44336", 1, 1, now));
        list.add(new CategoryEntity("cat_exp_02", null, null, "Di chuyển",     "expense", "ic_transport", "#FF9800", 1, 2, now));
        list.add(new CategoryEntity("cat_exp_03", null, null, "Nhà ở",         "expense", "ic_house",     "#795548", 1, 3, now));
        list.add(new CategoryEntity("cat_exp_04", null, null, "Sức khỏe",      "expense", "ic_health",    "#E91E63", 1, 4, now));
        list.add(new CategoryEntity("cat_exp_05", null, null, "Mua sắm",       "expense", "ic_shopping",  "#3F51B5", 1, 5, now));
        list.add(new CategoryEntity("cat_exp_06", null, null, "Giải trí",      "expense", "ic_entertain", "#00BCD4", 1, 6, now));
        list.add(new CategoryEntity("cat_exp_07", null, null, "Giáo dục",      "expense", "ic_education", "#009688", 1, 7, now));
        list.add(new CategoryEntity("cat_exp_08", null, null, "Tài chính",     "expense", "ic_finance",   "#FF5722", 1, 8, now));
        list.add(new CategoryEntity("cat_exp_09", null, null, "Chi tiêu khác", "expense", "ic_other",     "#9E9E9E", 1, 9, now));

        // Chi tiêu cấp 2
        list.add(new CategoryEntity("cat_exp_01_1", null, "cat_exp_01", "Nhà hàng",         "expense", "ic_restaurant", "#F44336", 1, 1, now));
        list.add(new CategoryEntity("cat_exp_01_2", null, "cat_exp_01", "Cà phê",            "expense", "ic_coffee",     "#795548", 1, 2, now));
        list.add(new CategoryEntity("cat_exp_01_3", null, "cat_exp_01", "Siêu thị",          "expense", "ic_grocery",    "#4CAF50", 1, 3, now));
        list.add(new CategoryEntity("cat_exp_01_4", null, "cat_exp_01", "Đặt đồ ăn online", "expense", "ic_delivery",   "#FF9800", 1, 4, now));
        list.add(new CategoryEntity("cat_exp_02_1", null, "cat_exp_02", "Xăng xe",           "expense", "ic_gas",        "#FF9800", 1, 1, now));
        list.add(new CategoryEntity("cat_exp_02_2", null, "cat_exp_02", "Grab / Taxi",       "expense", "ic_taxi",       "#FFC107", 1, 2, now));
        list.add(new CategoryEntity("cat_exp_02_3", null, "cat_exp_02", "Vé tàu / máy bay", "expense", "ic_flight",     "#03A9F4", 1, 3, now));
        list.add(new CategoryEntity("cat_exp_03_1", null, "cat_exp_03", "Tiền thuê nhà",     "expense", "ic_rent",       "#795548", 1, 1, now));
        list.add(new CategoryEntity("cat_exp_03_2", null, "cat_exp_03", "Điện",              "expense", "ic_electric",   "#FFEB3B", 1, 2, now));
        list.add(new CategoryEntity("cat_exp_03_3", null, "cat_exp_03", "Nước",              "expense", "ic_water",      "#03A9F4", 1, 3, now));
        list.add(new CategoryEntity("cat_exp_03_4", null, "cat_exp_03", "Internet",          "expense", "ic_wifi",       "#3F51B5", 1, 4, now));
        list.add(new CategoryEntity("cat_exp_06_1", null, "cat_exp_06", "Du lịch",           "expense", "ic_travel",     "#00BCD4", 1, 1, now));
        list.add(new CategoryEntity("cat_exp_06_2", null, "cat_exp_06", "Phim ảnh",          "expense", "ic_movie",      "#9C27B0", 1, 2, now));
        list.add(new CategoryEntity("cat_exp_08_1", null, "cat_exp_08", "Trả nợ",            "expense", "ic_debt",       "#FF5722", 1, 1, now));
        list.add(new CategoryEntity("cat_exp_08_2", null, "cat_exp_08", "Bảo hiểm",          "expense", "ic_insurance",  "#F44336", 1, 2, now));

        return list;
    }
}