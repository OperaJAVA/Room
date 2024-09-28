package ru.netology.nmedia.dto

data class Post(
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
)