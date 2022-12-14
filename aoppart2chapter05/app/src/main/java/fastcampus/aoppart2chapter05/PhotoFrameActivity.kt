package fastcampus.aoppart2chapter05

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()

    private var currentPosition = 0

    private val photoImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.photoImageView)
    }

    private val backgroundPhotoImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.backgroundPhotoImageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoframe)

        getPhotoUriFromIntent()
        startTimer()
    }

    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))    // null 일 경우 실행 안되는 코드
            }
        }
    }

    private fun startTimer() {
        timer(period = 5 * 1000) {
           runOnUiThread {
               val current = currentPosition
               val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1

               backgroundPhotoImageView.setImageURI(photoList[current])

               photoImageView.alpha = 0f    //opacity
               photoImageView.setImageURI(photoList[next])
               photoImageView.animate()     // animation function action
                   .alpha(1.0f)
                   .setDuration(1000)
                   .start()
               currentPosition = next
           }
        }
    }

}