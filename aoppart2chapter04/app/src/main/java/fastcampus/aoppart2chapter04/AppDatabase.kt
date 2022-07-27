package fastcampus.aoppart2chapter04

import androidx.room.Database
import androidx.room.RoomDatabase
import fastcampus.aoppart2chapter04.dao.HistoryDao
import fastcampus.aoppart2chapter04.model.History

@Database(entities = [History::class], version = 1)  // 사용할 db 등록
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}