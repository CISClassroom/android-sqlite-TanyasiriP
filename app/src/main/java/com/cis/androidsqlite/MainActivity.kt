package com.cis.androidsqlite

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val DB_NAME = "todo.db"
        button_add.setOnClickListener{
            val dbHelper = DBHelper(this,DB_NAME,null,1)
            val newTask:Task = Task(editText.text.toString())
            dbHelper.addTask(newTask)

            Toast.makeText(this,
                editText.text.toString()+ "Add to database",
                Toast.LENGTH_SHORT).show()
        }
        button_read.setOnClickListener{
            val dbHelper = DBHelper(this,DB_NAME,null,1)
            val data:Cursor? = dbHelper.getAllTask()

            data!!.moveToFirst()

            textView.text = ""
            //textView.append(data.getString((data.getColumnIndex("taskname"))))
            do {
                val taskname = data.getString(data.getColumnIndex("taskname"))
                val id = data.getString(data.getColumnIndex("id"))
                textView.append(id + ") " + taskname)
                textView.append("\n")
            }while (data.moveToNext())
            data.close()
        }
        buttonDelete.setOnClickListener{
            val input = editText2.text.toString()
            val dbHelper = DBHelper(this,DB_NAME,null,1)
            val result = dbHelper.deleteTask(input.toInt())
        }
        buttonEdit.setOnClickListener{
            //1,New task (id and data)
            val input = editText2.text.toString()
            val datas = input.split(",")
            val task = Task(datas[0].toInt(),datas[1])
            val dbHelper = DBHelper(this,DB_NAME,
                null,1)
            val result = dbHelper.updateTask(task)
        }
    }
}
