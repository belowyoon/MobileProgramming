package com.example.recyclerview

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding
import java.security.AccessController.getContext
import java.text.DateFormatSymbols


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val emotions  = arrayOf(
        R.drawable.ic_baseline_sentiment_very_satisfied_24,
        R.drawable.ic_baseline_sentiment_satisfied_alt_24,
        R.drawable.ic_baseline_sentiment_satisfied_24,
        R.drawable.ic_baseline_sentiment_dissatisfied_24,
        R.drawable.ic_baseline_sentiment_very_dissatisfied_24
    )
    val colors = arrayOf(
        R.color.lightpink, R.color.pink, R.color.purple, R.color.blue, R.color.lightblue,
        R.color.mint, R.color.green, R.color.lightgreen, R.color.yellow, R.color.orange, R.color.peach,
        R.color.gray, R.color.white
    )

    var diaryList: ArrayList<DiaryData> = ArrayList()
    var memoList: ArrayList<String> = ArrayList()

    lateinit var rAdapter: DiaryDataAdapter

    var colorSelect = 0
    var emotion = 0
    lateinit var year: String
    lateinit var month: String
    lateinit var day: String
    lateinit var activity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initSpinners()
        spinnerHandler()
        btnHandler()
        initRecyclerView()
    }
    private fun initData() {
        diaryList.add(DiaryData(emotions[1],"2023  March  29", "걷기", colors[10]))
        diaryList.add(DiaryData(emotions[0],"2023  March  30", "놀기", colors[9]))
        diaryList.add(DiaryData(emotions[1],"2023  March  31", "배드민턴", colors[8]))
        diaryList.add(DiaryData(emotions[4],"2023  April  1", "축구", colors[3]))
        diaryList.add(DiaryData(emotions[0],"2023  April  2", "힐링", colors[7]))
        diaryList.add(DiaryData(emotions[3],"2023  April  3", "공부", colors[6]))
        diaryList.add(DiaryData(emotions[2],"2023  April  4", "볼링", colors[5]))
        diaryList.add(DiaryData(emotions[3],"2023  April  5", "공연보기", colors[8]))
        diaryList.add(DiaryData(emotions[2],"2023  April  6", "맛집가기", colors[4]))

        memoList.add("석촌호수 산책")
        memoList.add("친한친구 만나기")
        memoList.add("아빠랑 오랜만에")
        memoList.add("축구에 졌다")
        memoList.add("한강에 갔다")
        memoList.add("과제를 했다")
        memoList.add("")
        memoList.add("공연이 재밌었다")
        memoList.add("그냥 그랬다")
    }
    private fun initSpinners() {
        val adapter1= EmotionCustomAdapter(this, emotions)
        binding.emotionSpinner.adapter = adapter1

        val years = resources.getStringArray(R.array.spinner_year)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, years)
        binding.yearSpinner.adapter = adapter2

        val months = DateFormatSymbols().months
        val adapter3 = ArrayAdapter(this, android.R.layout.simple_list_item_1, months)
        binding.monthSpinner.adapter = adapter3

        val days = resources.getStringArray(R.array.spinner_day)
        val adapter4 = ArrayAdapter(this, android.R.layout.simple_list_item_1, days)
        binding.daySpinner.adapter = adapter4


        val activities = resources.getStringArray(R.array.spinner_activity)
        val adapter5 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, activities)
        binding.activitySpinner.adapter = adapter5

        val colorName = resources.getStringArray(R.array.spinner_color)
        val adapter6 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, colorName)
        binding.colorSpinner.adapter = adapter6

    }
    private fun spinnerHandler() {
        binding.emotionSpinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                emotion = emotions[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                emotion = emotions[0]
            }
        }
        binding.yearSpinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                year = binding.yearSpinner.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                year = binding.yearSpinner.getItemAtPosition(0).toString()
            }
        }
        binding.monthSpinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                month = binding.monthSpinner.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                month = binding.monthSpinner.getItemAtPosition(0).toString()
            }
        }
        binding.daySpinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                day = binding.daySpinner.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                day = binding.daySpinner.getItemAtPosition(0).toString()
            }
        }
        binding.activitySpinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                activity = binding.activitySpinner.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                day = binding.daySpinner.getItemAtPosition(0).toString()
            }
        }
        binding.colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.main1.setBackgroundResource(colors[position])
                binding.main2.setBackgroundResource(colors[position])
                colorSelect = colors[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                colorSelect = colors[0]
            }
        }
    }
    private fun btnHandler () {
        binding.submitBtn.setOnClickListener {
            var date = "$year  $month  $day"
            diaryList.add(DiaryData(emotion,date,activity,colorSelect))
            rAdapter.notifyDataSetChanged()
        }
    }
    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager (this,
            LinearLayoutManager.VERTICAL, false)
        rAdapter = DiaryDataAdapter(diaryList, memoList)
        binding.recyclerView.adapter = rAdapter

        rAdapter.itemClickListener = object:DiaryDataAdapter.OnItemClickListener {
            override fun OnItemClick(data: DiaryData,memo: String)  {
                var message = "\t\t오늘 기분이 "
                when (data.emotion) {
                    R.drawable.ic_baseline_sentiment_very_satisfied_24 -> message += "매우 좋았다. "
                    R.drawable.ic_baseline_sentiment_satisfied_alt_24 -> message += "좋았다. "
                    R.drawable.ic_baseline_sentiment_satisfied_24 -> message += "괜찮았다. "
                    R.drawable.ic_baseline_sentiment_dissatisfied_24 -> message += "안 좋았다. "
                    R.drawable.ic_baseline_sentiment_very_dissatisfied_24 -> message += "매우 안좋았다. "
                    else -> print("default")
                }
                message += "\n\t\t오늘 나는 " + data.activity + "을(를) 했다.\n\t\t" + memo
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(data.date)
                    .setMessage(message)
                    .setPositiveButton("종료"
                    ) { dialog, which -> Log.d("MyTag", "positive") }
                    .create()
                    .show()
            }
        }
        val simpleCallback = object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                rAdapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                rAdapter.removeItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}