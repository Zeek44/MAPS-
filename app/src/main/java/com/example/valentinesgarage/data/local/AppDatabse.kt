package com.example.valentinesgarage.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.valentinesgarage.data.local.dao.RepairTaskDao
import com.example.valentinesgarage.data.local.dao.TruckDao
import com.example.valentinesgarage.data.local.dao.UserDao
import com.example.valentinesgarage.data.local.entity.RepairTask
import com.example.valentinesgarage.data.local.entity.Truck
import com.example.valentinesgarage.data.local.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest

@Database(
    entities = [Truck::class, RepairTask::class, User::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun truckDao(): TruckDao
    abstract fun repairTaskDao(): RepairTaskDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun String.md5(): String {
            return MessageDigest.getInstance("MD5")
                .digest(toByteArray())
                .joinToString("") { "%02x".format(it) }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "valentines_garage_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = INSTANCE?.userDao()
                                dao?.insertUser(User(
                                    username = "valentine",
                                    passwordHash = "admin123".md5(),
                                    role = "admin",
                                    isApproved = true,
                                    fullName = "Valentine"
                                ))
                                dao?.insertUser(User(
                                    username = "mechanic1",
                                    passwordHash = "mech123".md5(),
                                    role = "mechanic",
                                    isApproved = true,
                                    fullName = "Mechanic One"
                                ))
                                dao?.insertUser(User(
                                    username = "receptionist1",
                                    passwordHash = "recep123".md5(),
                                    role = "receptionist",
                                    isApproved = true,
                                    fullName = "Receptionist One"
                                ))
                            }
                        }
                    }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}