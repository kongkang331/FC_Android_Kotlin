package fastcampus.aop_part2_chapter03

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity: AppCompatActivity() {
    // main thread에 연결된 handler
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)
        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))


        // 자주 저장되는 문제 해결을 위해 만든 thread 구간
        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", diaryEditText.text.toString())
            }
            Log.d("DiaryActivity", "SAVE!! ${diaryEditText.text.toString()}")
        }

        diaryEditText.addTextChangedListener {
            // 다이어리의 변경사항이 있을 시에 저장하는 기능 - 한 자,한 자 다 반응하여 자주 저장된다는 문제 있음
            // 위의 thread 코드 사용으로 문제 해결
//            detailPreferences.edit {
//                putString("detail", diaryEditText.text.toString())
//            }
            Log.d("DiaryActivity", "TextChanted::$it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}