package fastcampus.aoppart2chapter04.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
// DB Table 생성하는 과정
// Entity를 import 할 수 있게 build.gradle (Module:~) 파일에서 코드 추가함
// room 의 데이터 클래스로 변환
@Entity
data class History (
    @PrimaryKey val uid: Int?,
    @ColumnInfo(name = "expression") val expression: String?,
    @ColumnInfo(name = "result") val result: String?
)
