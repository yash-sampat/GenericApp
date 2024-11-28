package com.generic.login.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.generic.login.R
import kotlinx.android.synthetic.main.fragment_product_detail.view.comments
import kotlinx.android.synthetic.main.fragment_product_detail.view.downloads
import kotlinx.android.synthetic.main.fragment_product_detail.view.favorites
import kotlinx.android.synthetic.main.fragment_product_detail.view.imageSize
import kotlinx.android.synthetic.main.fragment_product_detail.view.imageTags
import kotlinx.android.synthetic.main.fragment_product_detail.view.imageType
import kotlinx.android.synthetic.main.fragment_product_detail.view.likes
import kotlinx.android.synthetic.main.fragment_product_detail.view.productDetailImage
import kotlinx.android.synthetic.main.fragment_product_detail.view.user
import kotlinx.android.synthetic.main.fragment_product_detail.view.views

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "largeImageURL"
private const val ARG_PARAM2 = "size"
private const val ARG_PARAM3 = "type"
private const val ARG_PARAM4 = "tags"
private const val ARG_PARAM5 = "user"
private const val ARG_PARAM6 = "views"
private const val ARG_PARAM7 = "likes"
private const val ARG_PARAM8 = "comments"
private const val ARG_PARAM9 = "favorites"
private const val ARG_PARAM10 = "downloads"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: Int? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null
    private var param6: Int? = null
    private var param7: Int? = null
    private var param8: Int? = null
    private var param9: Int? = null
    private var param10: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
            param5 = it.getString(ARG_PARAM5)
            param6 = it.getInt(ARG_PARAM6)
            param7 = it.getInt(ARG_PARAM7)
            param8 = it.getInt(ARG_PARAM8)
            param9 = it.getInt(ARG_PARAM9)
            param10 = it.getInt(ARG_PARAM10)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(view.context).load(param1).centerCrop().into(view.productDetailImage)
        view.imageSize.text = String.format("Size: %s", param2)
        view.imageType.text = String.format("Type: %s", param3)
        view.imageTags.text = String.format("Tags: %s", param4)
        view.user.text = String.format("User: %s", param5)
        view.views.text = String.format("Views: %s", param6)
        view.likes.text = String.format("Likes: %s", param7)
        view.comments.text = String.format("Comments: %s", param8)
        view.favorites.text = String.format("Favorites: %s", param9)
        view.downloads.text = String.format("Downloads: %s", param10)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}