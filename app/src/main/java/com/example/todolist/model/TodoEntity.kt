//  TodoEntity.kt = 每一页的格式
// 单词页格式：
//- 编号 (id)
//- 标题 (title)
//- 意思 (meaning)
//- 同义词 (synonyms)
//- 详情 (details)
//- 状态 (status) → "NEW" 或 "DONE"
package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val meaning: String? = null,
    val synonyms: String? = null,
    val details: String,
    val status: String // "NEW" or "DONE"
)