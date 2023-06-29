package com.example.assignment3.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Musical::class],
    version = 2
)
abstract class MusicalDatabase:RoomDatabase(){
    abstract fun musicalDao(): MusicalDAO

    companion object {
        private  var INSTANCE: MusicalDatabase? = null

        fun getDatabase(context: Context): MusicalDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            val instance = Room.databaseBuilder(
                context,
                MusicalDatabase::class.java,
                "musicaldb"
            ).addMigrations(migration_1_2).build()

            INSTANCE = instance
            return instance
        }
    }
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Sound Add COLUMN isChecked INTEGER NOT NULL DEFAULT 0"
        )
    }

}
