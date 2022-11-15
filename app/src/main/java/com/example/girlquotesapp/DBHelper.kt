import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import com.example.girlquotesapp.Models.CatagoryNameModel
import com.example.girlquotesapp.QuotesModelClass
import java.io.*
import java.util.*

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {
    private val mDataBase: SQLiteDatabase? = null
    private val DATABASE_NAME = "QUOTES.db"
    private var DB_PATH = ""
    private var mNeedUpdate = false
    private val mContext: Context
    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile()
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DATABASE_NAME)
        return dbFile.exists()
    }

    //    TODO copy file
    @Throws(IOException::class)
    private fun copyDBFile() {
        val mInput: InputStream = mContext.getAssets().open(DATABASE_NAME)
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DATABASE_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) mNeedUpdate = true
    }

    //    TODO update database
    @Throws(IOException::class)
    fun updateDataBase() {
        if (mNeedUpdate) {
            val dbFile = File(DB_PATH + DATABASE_NAME)
            if (dbFile.exists()) dbFile.delete()
            copyDataBase()
            mNeedUpdate = false
        }
    }


    override fun close() {
        mDataBase?.close()
        super.close()
    }

    companion object {
        private const val TAG = "MyDatabase"
        private const val DATABASE_NAME = "QUOTES.db"
        private var DB_PATH = ""
        private const val DB_VERSION = 1
    }

    init {
        if (Build.VERSION.SDK_INT >= 17) (context.applicationInfo.dataDir.toString() + "/databases/").also {
            DB_PATH = it
        }
        else DB_PATH =
            "/data/data/" + context.packageName.toString() + "/databases/"
        mContext = context
        copyDataBase()
        this.readableDatabase
    }


    fun display(): ArrayList<CatagoryNameModel> {
        val list = ArrayList<CatagoryNameModel>()

        val db = readableDatabase
        val sql = "select * from Catagory_tb"
        val cursor: Cursor = db.rawQuery(sql, null)

        cursor.moveToFirst()

        do {
            val id = cursor.getInt(0)
            val catagoryName = cursor.getString(1)

            list.add(CatagoryNameModel(id, catagoryName))

            Log.e("TAG", "display:     " + id + "" + catagoryName + "")

        } while (cursor.moveToNext())

        return list
    }

    fun displayQuotes(idCatagory: Int?): ArrayList<QuotesModelClass> {
        var idQuotes = idCatagory
        var quotesList = ArrayList<QuotesModelClass>()
        var db = readableDatabase
        var sql = "select * from Quotes_Tb"
        val cursor: Cursor = db.rawQuery(sql, null)

        cursor.moveToFirst()

        do {
            val id = cursor.getInt(0)
            val quotes = cursor.getString(1)
            val catagoryId = cursor.getInt(2)
            val status = cursor.getInt(3)

            if (catagoryId.equals(idQuotes)) {
                quotesList.add(QuotesModelClass(id, quotes, catagoryId,status))
                Log.e("TAG", "display:     " + id + "" + quotes + "" + catagoryId)
            }

        } while (cursor.moveToNext())

        return quotesList
    }

    fun insertLike(id:Int,status:Int){

        var db=writableDatabase
        var cv=ContentValues()
        cv.put("like",status)
        db.insert("Quotes_Tb",null,cv)
    }
    fun likeUpdate(like:Int,id: Int){
        var db=writableDatabase
        var cv=ContentValues()
        cv.put("like",like)
        db.update("Quotes_Tb",cv,"id=?", arrayOf(id.toString()))
    }
}

