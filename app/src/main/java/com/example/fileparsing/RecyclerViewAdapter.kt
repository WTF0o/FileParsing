import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fileparsing.FileParsing
import com.example.fileparsing.R

class MainAdapter(var items: List<FileParsing>, val callback: Callback) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false))
//        return if(viewType == R.layout.card_view) MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false))
//        else MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.load_more, parent, false))


    override fun getItemCount() = items.size//= items.size + 1

//    override fun getItemViewType(position: Int): Int {
//        //return if(position == items.size) R.layout.load_more else R.layout.card_view
//        return R.layout.card_view
//    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        //if(position != items.size) holder.bind(items[position])
        holder.bind(items[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fileName = itemView.findViewById<TextView>(R.id.fileName)
        private val countDownload = itemView.findViewById<TextView>(R.id.countDownload)
        private val fileTime = itemView.findViewById<TextView>(R.id.fileTime)
        private val fileLike = itemView.findViewById<TextView>(R.id.fileLike)
        private val fileDislike = itemView.findViewById<TextView>(R.id.fileDislike)
        private val fileMD5 = itemView.findViewById<TextView>(R.id.fileMD5)
        private val fileSHA1 = itemView.findViewById<TextView>(R.id.fileSHA1)
        private val fileDownload = itemView.findViewById<TextView>(R.id.fileDownload)


        fun bind(item: FileParsing) {

            fileName.text = item.fName
            countDownload.text = item.fDownload
            fileTime.text = item.fTime
            fileLike.text = item.fLike
            fileDislike.text = item.fDislike
            fileMD5.text = item.fMD5
            fileSHA1.text = item.fSHA1
            fileDownload.autoLinkMask = 0
            fileDownload.text = HtmlCompat.fromHtml("<a href=${item.fLink.toString()}>Загрузить</a>", HtmlCompat.FROM_HTML_MODE_LEGACY)
            fileDownload.movementMethod = LinkMovementMethod.getInstance()

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: FileParsing)
    }

}