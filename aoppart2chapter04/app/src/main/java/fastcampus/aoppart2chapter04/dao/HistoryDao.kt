package fastcampus.aoppart2chapter04.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fastcampus.aoppart2chapter04.model.History

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history") // db 가져오는 query 작성
    fun getAll(): List<History>

    @Insert
    fun insterHistory(history: History) // insert 문

    @Query("DELETE FROM history")
    fun deleteAll()

    @Delete
    fun delete(history: History)    // 하나만 삭제

    @Query("SELECT * FROM history WHERE result LIKE :result")
    fun findByResult(result: String): List<History> // data 반환 함수

}