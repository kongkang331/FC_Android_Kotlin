package fastcampus.aop_part2_chapter03

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }
    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }
    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {
            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val pwPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val pwFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (pwPreferences.getString("password", "000").equals(pwFromUser)) {
                // 패스워드 일치
                // TODO 다이어리 페이지 작성 후에 넘겨주어야 함
                 startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                // 실패
                showErrorAlertDialog()
            }
        }

        changePasswordButton.setOnClickListener {
            val pwFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            val pwPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            if (changePasswordMode) {
                // 번호를 저장하는 기능
                pwPreferences.edit(true) {
                    putString("password", pwFromUser)
                }
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)
            } else {
                // changePasswordMode 활성화 :: 비밀번호 맞는지 체크
                if (pwPreferences.getString("password", "000").equals(pwFromUser)) {
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    changePasswordButton.setBackgroundColor(Color.RED)
                } else {
                    // 실패
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}