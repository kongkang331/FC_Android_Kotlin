package fastcampus.aop_part2_chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }
    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }
    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }
    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }
    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.randomFirst),
            findViewById<TextView>(R.id.randomSecond),
            findViewById<TextView>(R.id.randomThird),
            findViewById<TextView>(R.id.randomFourth),
            findViewById<TextView>(R.id.randomFifth),
            findViewById<TextView>(R.id.randomSixth)
        )
    }
    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    // runButton 클릭시 실행될 함수
    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
                setNumberBackground(number, textView)
            }
        }
    }
    // addButton 클릭시 실행될 함수
    private fun initAddButton() {
        addButton.setOnClickListener {
            // if들은 각 상황에 대한 예외처리들
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요. ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다. ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다. ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()
            setNumberBackground(numberPicker.value, textView)
            pickNumberSet.add(numberPicker.value)
        }
    }

    // initRunButton()과 initAddButton()에 공통으로 들어가는 when문의 중복 문제 해결을 위한 함수
    private fun setNumberBackground(number:Int, textView: TextView) {
        when(number) {
            in 1..10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }

    // clearButton 클릭시 실행될 함수
    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
            didRun = false
        }
    }

    // 랜덤하게 숫자를 뽑아줄 함수
    private fun getRandomNumber(): List<Int> {
        // 1~45의 후보숫자들을 리스트에 모두 저장
        val numberList = mutableListOf<Int>()
            .apply {
                // for문을 i만큼 돌려서 해당되는 타이밍에 i들을 리스트에 순서대로 저장
                for (i in 1..45) {
                    if (pickNumberSet.contains(i)) {
                        continue
                    }
                    this.add(i)
                }
            }
        // 리스트의 내용물을 랜덤하게 셔플
        numberList.shuffle()
        // 위 45까지 있는 리스트 중에서 원하는 만큼만 잘라서 새로 저장되는 리스트 생성(서브 리스트)
        // 수동으로 추가한 번호는 중복되지 않게 제외 후 이미 추가된 숫자와 랜덤 숫자를 함께 새 리스트에 저장
        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)
        // 리스트의 6개의 랜덤되게 뽑은 숫자들이 오름차순으로 정렬됨(=sorted)
        return newList.sorted()
    }
}