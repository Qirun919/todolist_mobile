// TodoDao.kt = 你能对笔记本做什么动作
// - 看全部单词   → getByStatus()
// - 添加单词     → insert()
// - 修改单词     → update()
// - 删除单词     → delete()

package com.example.todolist.database

import androidx.room.*
import com.example.todolist.model.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table WHERE status = :status")
    suspend fun getByStatus(status: String): List<TodoEntity>

    @Insert
    suspend fun insert(todo: TodoEntity)

    @Update
    suspend fun update(todo: TodoEntity)

    @Delete
    suspend fun delete(todo: TodoEntity)
}