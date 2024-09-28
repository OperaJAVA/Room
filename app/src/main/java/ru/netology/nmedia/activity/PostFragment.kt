package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FeedFragment.Companion.idArg
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.util.CountLikeShareView
import ru.netology.nmedia.viewmodel.PostViewModel

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val id = arguments?.idArg

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            binding.postContent.apply {
                posts.map { post ->
                    if (post.id.toInt() == id) {

                        author.text = post.author
                        published.text = post.published
                        content.text = post.content
                        like.text = CountLikeShareView.counterDecimal(post.likeNum)
                        share.text = CountLikeShareView.counterDecimal(post.shareNum)
                        view.text = CountLikeShareView.counterDecimal(post.viewNum)

                        like.isChecked = post.liked

                        if (post.video == null) {
                            binding.postContent.playVideoGroup.visibility = View.GONE
                        } else {
                            binding.postContent.playVideoGroup.visibility = View.VISIBLE
                        }

                        like.setOnClickListener { viewModel.likeById(post.id) }
                        share.setOnClickListener { viewModel.shareById(post.id) }
                        play.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                            startActivity(intent)
                        }
                        backgroundVideo.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                            startActivity(intent)
                        }

                        menu.setOnClickListener {
                            PopupMenu(it.context, it).apply {
                                inflate(R.menu.post_menu)

                                setOnMenuItemClickListener { menuItem ->
                                    when (menuItem.itemId) {
                                        R.id.remove -> {
                                            viewModel.removeById(post.id)
//                                            findNavController().navigate(
//                                                R.id.action_postFragment_to_feedFragment
//                                            )
                                            findNavController().navigateUp()
                                            true
                                        }
                                        R.id.edit -> {
                                            viewModel.editContent(post)
                                            findNavController().navigate(
                                                R.id.action_postFragment_to_newPostFragment,
                                                Bundle().apply {
                                                    textArg = post.content
                                                }
                                            )
                                            true
                                        }
                                        else -> false
                                    }
                                }
                            }.show()
                        }
                    }
                }
            }
        }
        return binding.root
    }
}