package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    val video: String? = null,
    val liked: Boolean,

    val likeNum: Long,
    val shareNum: Long,
    val viewNum: Long
) {
    fun toDto() =
        Post(
            id, author, authorAvatar, published, content, video, liked,
            likeNum, shareNum, viewNum
        )

    companion object {
        fun fromDto(dto: Post) = with(dto) {
            PostEntity(
                id, author, authorAvatar, published, content, video, liked,
                likeNum, shareNum, viewNum
            )
        }
    }
}
