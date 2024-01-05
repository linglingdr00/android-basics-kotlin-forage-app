/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.forage.data

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forage.model.Forageable

/**
 * Room database to persist data for the Forage app.
 * This database stores a [Forageable] entity
 */
// TODO: create the database with all necessary annotations, methods, variables, etc.
@Database(entities = [Forageable::class], version = 1, exportSchema = false)
abstract class ForageDatabase : RoomDatabase() {
    abstract fun forageableDao(): ForageableDao

    // 定義 companion object (可使用 class name 做為限定詞，建立或取得 database)
    companion object {

        private var INSTANCE: ForageDatabase? = null

        // 使用 database builder 所需的 Context 參數定義 getDatabase() method
        fun getDatabase(context: Context): ForageDatabase {
            return INSTANCE ?: synchronized(this) { // synchronized區塊中，一次只能執行一個 thread
                // 使用 database builder 取得 database instance
                // 將 application context、database class 以及 database name item_database 傳遞給 database builder
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ForageDatabase::class.java,
                    "forageable_database")
                    .fallbackToDestructiveMigration() // 將遷移策略新增至 builder
                    .build()
                INSTANCE = instance // 將 INSTANCE 設為剛才建立好的 instance
                return instance
            }
        }
    }
}
